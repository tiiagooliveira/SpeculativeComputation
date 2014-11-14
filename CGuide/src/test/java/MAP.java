/** Import statements for il1 classes. */
/** Import statements for standard Java classes. */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucla.belief.BeliefNetwork;
import edu.ucla.belief.FiniteVariable;
import edu.ucla.belief.Prune;
import edu.ucla.belief.StateNotFoundException;
import edu.ucla.belief.VariableImpl;
import edu.ucla.belief.inference.JEngineGenerator;
import edu.ucla.belief.inference.JoinTreeInferenceEngineImpl;
import edu.ucla.belief.inference.JoinTreeSettings;
import edu.ucla.belief.inference.map.InitializationMethod;
import edu.ucla.belief.inference.map.MapRunner;
import edu.ucla.belief.inference.map.SearchMethod;
import edu.ucla.belief.io.NetworkIO;
import edu.ucla.util.AbstractStringifier;

/**
 * This class demonstrates code for a MAP query
 * 
 * To compile this class, make sure inflib.jar occurs in the command line
 * classpath, e.g. javac -classpath inflib.jar MAP.java
 * 
 * To run it, do the same, but also include the path to the compiled class, e.g.
 * java -classpath .;inflib.jar MAP
 * 
 * @author Filipe Gonçalves
 */
public class MAP {

	public static map_res MAP_default_start(String path, String[] variables) {
		MAP T = new MAP();
		map_res res = T.doMAP_default(T.readNetworkFile(path), variables);
		return res;
	}

	public static map_res MAP_value_start(String path,
			ArrayList<String[]> variables) {
		MAP T = new MAP();
		map_res res = T.doMAP_value(T.readNetworkFile(path), variables);
		return res;
	}

	/**
	 * Demonstrates a MAP query. Determine Default Values (Unknown Value of all
	 * Variables).
	 */
	public map_res doMAP_default(BeliefNetwork bn, String[] variables) {
		/* Define evidence, by id. */
		Map evidence = Collections.EMPTY_MAP;

		/* Define the set of MAP variables, by id. */
		Set setMAPVariables = new HashSet();
		// String[] arrayMAPVariableIDs = new String[] { "t", "n", "m" };
		String[] arrayMAPVariableIDs = variables;
		for (int i = 0; i < arrayMAPVariableIDs.length; i++) {
			setMAPVariables.add(bn.forID(arrayMAPVariableIDs[i]));
		}

		/* Approximate MAP. */

		/* Prune first. */
		BeliefNetwork networkUnpruned = bn;
		Set varsUnpruned = setMAPVariables;
		Map evidenceUnpruned = evidence;

		Map oldToNew = new HashMap(networkUnpruned.size());
		Map newToOld = new HashMap(networkUnpruned.size());
		Set queryVarsPruned = new HashSet(varsUnpruned.size());
		Map evidencePruned = new HashMap(evidenceUnpruned.size());
		BeliefNetwork networkPruned = Prune.prune(networkUnpruned,
				varsUnpruned, evidenceUnpruned, oldToNew, newToOld,
				queryVarsPruned, evidencePruned);

		bn = networkPruned;
		setMAPVariables = queryVarsPruned;
		evidence = evidencePruned;

		/* Construct the right kind of inference engine. */
		JEngineGenerator generator = new JEngineGenerator();
		JoinTreeInferenceEngineImpl engine = generator
				.makeJoinTreeInferenceEngineImpl(bn, new JoinTreeSettings());

		/* Set evidence. */
		try {
			bn.getEvidenceController().setObservations(evidencePruned);
		} catch (StateNotFoundException e) {
			System.err.println("Error, failed to set evidence: " + e);
			return null;
		}
		;

		/*
		 * Define the search method, one of: HILL (Hill Climbing), TABOO (Taboo
		 * Search)
		 */
		SearchMethod searchmethod = SearchMethod.HILL;

		/*
		 * Define the initialization method, one of: RANDOM (Random), MPE (MPE),
		 * ML (Maximum Likelihoods), SEQ (Sequential)
		 */
		InitializationMethod initializationmethod = InitializationMethod.ML;

		/* Define a limit on the number of search steps (default 25). */
		int steps = 25;

		/* Construct a MapRunner and run the query. */
		MapRunner maprunner = new MapRunner();
		MapRunner.MapResult mapresult = maprunner.approximateMap(bn, engine,
				setMAPVariables, evidence, searchmethod, initializationmethod,
				steps);
		Map instantiation = mapresult.instantiation;
		double score = mapresult.score;

		/* Print the results. */
		System.out.println("Approximate MAP, P(MAP,e)= " + score);
		System.out.println("\t P(MAP|e)= " + (score / engine.probability()));
		VariableImpl.setStringifier(AbstractStringifier.VARIABLE_ID);
		System.out.println("\t instantiation: " + instantiation);

		/* Print timings. */
		System.out.println();
		System.out.println("Initialization time, cpu: "
				+ mapresult.initDurationMillisProfiled + ", elapsed: "
				+ mapresult.initDurationMillisElapsed);
		System.out.println("Search time, cpu: "
				+ mapresult.searchDurationMillisProfiled + ", elapsed: "
				+ mapresult.searchDurationMillisElapsed);
		System.out.println();

		/*
		 * Split instantiation into String[2] for each result, being String[0]
		 * name of variable, String[1] most probable value
		 */
		List<String> list = Arrays.asList(instantiation.toString().trim()
				.split(","));
		List<String[]> conj_res = new ArrayList<String[]>();
		for (String a : list) {
			String[] res = new String[2];
			res = a.split("=");
			/* Retirar espaços */
			res[0] = res[0].trim().replaceAll("\\{", "");
			res[1] = res[1].trim().replaceAll("\\}", "");
			conj_res.add(res);
		}

		map_res res = new map_res(score, score / engine.probability(), conj_res);

		/* Clean up to avoid memory leaks. */
		engine.die();

		return res;
	}

	/**
	 * Demonstrates a MAP query. Determine Unknown Values, given some Variables.
	 */
	public map_res doMAP_value(BeliefNetwork bn, ArrayList<String[]> variables) {
		/* Define evidence, by id. */
		Map evidence = new HashMap();
		FiniteVariable var = null;

		ArrayList<String> mapVariables = new ArrayList<String>();

		for (String[] a : variables) {
			if (!a[1].equals("unknown")) {
				var = (FiniteVariable) bn.forID(a[0]);
				evidence.put(var, var.instance(a[1]));
			} else {
				mapVariables.add(a[0]);
			}
		}

		/* Define the set of MAP variables, by id. */
		Set setMAPVariables = new HashSet();
		String[] arrayMAPVariableIDs = mapVariables
				.toArray(new String[mapVariables.size()]);
		for (int i = 0; i < arrayMAPVariableIDs.length; i++) {
			setMAPVariables.add(bn.forID(arrayMAPVariableIDs[i]));
		}

		/* Approximate MAP. */

		/* Prune first. */
		BeliefNetwork networkUnpruned = bn;
		Set varsUnpruned = setMAPVariables;
		Map evidenceUnpruned = evidence;

		Map oldToNew = new HashMap(networkUnpruned.size());
		Map newToOld = new HashMap(networkUnpruned.size());
		Set queryVarsPruned = new HashSet(varsUnpruned.size());
		Map evidencePruned = new HashMap(evidenceUnpruned.size());
		BeliefNetwork networkPruned = Prune.prune(networkUnpruned,
				varsUnpruned, evidenceUnpruned, oldToNew, newToOld,
				queryVarsPruned, evidencePruned);

		bn = networkPruned;
		setMAPVariables = queryVarsPruned;
		evidence = evidencePruned;

		/* Construct the right kind of inference engine. */
		JEngineGenerator generator = new JEngineGenerator();
		JoinTreeInferenceEngineImpl engine = generator
				.makeJoinTreeInferenceEngineImpl(bn, new JoinTreeSettings());

		/* Set evidence. */
		try {
			bn.getEvidenceController().setObservations(evidencePruned);
		} catch (StateNotFoundException e) {
			System.err.println("Error, failed to set evidence: " + e);
			return null;
		}
		;

		/*
		 * Define the search method, one of: HILL (Hill Climbing), TABOO (Taboo
		 * Search)
		 */
		SearchMethod searchmethod = SearchMethod.HILL;

		/*
		 * Define the initialization method, one of: RANDOM (Random), MPE (MPE),
		 * ML (Maximum Likelihoods), SEQ (Sequential)
		 */
		InitializationMethod initializationmethod = InitializationMethod.ML;

		/* Define a limit on the number of search steps (default 25). */
		int steps = 25;

		/* Construct a MapRunner and run the query. */
		MapRunner maprunner = new MapRunner();
		MapRunner.MapResult mapresult = maprunner.approximateMap(bn, engine,
				setMAPVariables, evidence, searchmethod, initializationmethod,
				steps);
		Map instantiation = mapresult.instantiation;
		double score = mapresult.score;

		/* Print the results. */
		System.out.println("Approximate MAP, P(MAP,e)= " + score);
		System.out.println("\t P(MAP|e)= " + (score / engine.probability()));
		VariableImpl.setStringifier(AbstractStringifier.VARIABLE_ID);
		System.out.println("\t instantiation: " + instantiation);

		/* Print timings. */
		System.out.println();
		System.out.println("Initialization time, cpu: "
				+ mapresult.initDurationMillisProfiled + ", elapsed: "
				+ mapresult.initDurationMillisElapsed);
		System.out.println("Search time, cpu: "
				+ mapresult.searchDurationMillisProfiled + ", elapsed: "
				+ mapresult.searchDurationMillisElapsed);
		System.out.println();

		/*
		 * Split instantiation into String[2] for each result, being String[0]
		 * name of variable, String[1] most probable value
		 */
		List<String> list = Arrays.asList(instantiation.toString().trim()
				.split(","));
		List<String[]> conj_res = new ArrayList<String[]>(variables);
		for (String a : list) {
			String[] res = new String[2];
			res = a.split("=");
			/* Retirar espaços */
			res[0] = res[0].trim().replaceAll("\\{", "");
			res[1] = res[1].trim().replaceAll("\\}", "");
			conj_res.add(res);
		}

		map_res res = new map_res(score, score / engine.probability(), conj_res);

		/* Clean up to avoid memory leaks. */
		engine.die();

		return res;
	}

	/**
	 * Open the network file used to create this tutorial.
	 */
	public BeliefNetwork readNetworkFile(String path) {
		BeliefNetwork ret = null;
		try {
			/* Use NetworkIO static method to read network file. */
			ret = NetworkIO.read(path);
		} catch (Exception e) {
			System.err.println("Error, failed to read \"" + path + "\": " + e);
			return null;
		}
		return ret;
	}
}
