import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.rosuda.JRI.Rengine;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

import uk.ac.manchester.cs.owlapi.dlsyntax.DLSyntaxObjectRenderer;
import cguide.execution.entities.Action;
import cguide.execution.entities.ClinicalAction;
import cguide.execution.entities.ClinicalTask;
import cguide.execution.entities.Condition;
import cguide.execution.entities.ConditionSet;
import cguide.execution.entities.Decision;
import cguide.execution.entities.End;
import cguide.execution.entities.Guideline;
import cguide.execution.entities.Option;
import cguide.execution.entities.Parameter;
import cguide.execution.entities.Plan;
import cguide.execution.entities.Question;
import cguide.execution.entities.Scope;
import cguide.execution.entities.TriggerCondition;
import cguide.parser.GuidelineHandler;

/**
 * Created with IntelliJ IDEA. User: tiago Date: 19-08-2013 Time: 20:34 To
 * change this template use File | Settings | File Templates.
 */
public class CGuideOwl {
	private static final String BASE_URL = "http://www.semanticweb.org/ontologies/2012/3/CompGuide.owl";
	private static OWLObjectRenderer renderer = new DLSyntaxObjectRenderer();

	public static void main(String[] args) throws OWLOntologyCreationException {
		Boolean load;
		GuidelineHandler parser = new GuidelineHandler();
		System.out.println("Loading action...");
		load = parser.loadGuideline();
		ArrayList<String> tasks = new ArrayList<String>();
		if (load) {
			ClinicalTask task = parser.getClinicalTask("Q03");
			Question question = (Question) task;
			// System.out.println(question.toJson());

			// Obter conjunto guidelines
			ArrayList<String> guidelines_str = parser.getGuidelines();
			ArrayList<Guideline> guidelines = new ArrayList<Guideline>();

			// Obtem guideline[0]
			// String guide_str = guidelines_str.get(0);
			// Guideline guide = parser.getClinicalPracticeGuideline(guide_str);

			// ClinicalTask teste = parser.getClinicalTask("Action011");
			// System.out.println(teste.toJson());

			// Gerar ficheiros .dot para visualizar através da aplicação
			// GraphViz representações gráficas das guidelines
			/*
			 * for(String guide : guidelines_str){
			 * gerar(parser.getClinicalPracticeGuideline(guide),parser); }
			 */

			// System.out.println(System.getProperty("java.library.path"));
			ArrayList<String> questoes = new ArrayList<String>();
			for (Parameter a : question.getParameters()) {
				// System.out.println("ID:"+a.getId());
				// System.out.println("ParameterIdentifier:"+a.getParameterIdentifier());
				// System.out.println("QuestionParameter:"+a.getQuestionParameter().replaceAll(".*\\(|\\).*",
				// "").toLowerCase());
				questoes.add(a.getQuestionParameter()
						.replaceAll(".*\\(|\\).*", "").toLowerCase()
						.replaceAll("\n", "")); // È ESTE QUE NÓS PRECISAMOS
				// System.out.println("QuestionParameter:"+a.getQuestionParameter());
				for (String b : a.getVariableName()) {
					// System.out.println("VariableName:"+b);
				}
				for (String b : a.getPossibleValue()) {
					// System.out.println("PossibleValue:"+b.toLowerCase());
				}
			}

			// Comando que gera bayesian networks usando linguagem R
			String path = GerarBN(question.getId(), questoes);

			// Arraylist<String> -> String[]
			String[] array = questoes.toArray(new String[questoes.size()]);

			// Generate default values (MAP). PS: Needs to find bayesian network file!
			if (!path.isEmpty()) {
				map_res map_resultado = MAP.MAP_default_start(path, array);
				System.out.println(map_resultado.toString());
				
				Prolog_query.Prolog_init(map_resultado);
			}
			
			/*
			
			//Gerar pergunta para MAP_value_start
			ArrayList<String[]> pergunta = new ArrayList<String[]>();
			for(String a : questoes) {
				if(a.equals("t")) {
					String[] insert = new String[2];
					insert[0] = a;
					insert[1] = "t2";
					pergunta.add(insert);
				}
				else if(a.equals("m")) {
					String[] insert = new String[2];
					insert[0] = a;
					insert[1] = "m1";
					pergunta.add(insert);
				}
				else {
					String[] insert = new String[2];
					insert[0] = a;
					insert[1] = "unknown";
					pergunta.add(insert);
				}
			}
			
			// Generate values to unknown variables(MAP). PS: Needs to find bayesian network file!
			if(!path.isEmpty()) {
				map_res map_resultado = MAP.MAP_value_start(path, pergunta);
				System.out.println(map_resultado.toString());
			}
			*/
			
			/*
			 * Plan plano = guide.getPlan(); Scope scope = guide.getScope();
			 * 
			 * String first_task_str = plano.getFirstClinicalTask().get(0);
			 * ClinicalTask first_task = parser.getClinicalTask(first_task_str);
			 * 
			 * System.out.println("FirstTaskFormat:"+first_task.getTaskFormat());
			 */
		} else {
			System.out.println("failed to load Check the filename");

		}

		/*
		 * //prepare ontology and reasoner OWLOntologyManager manager =
		 * OWLManager.createOWLOntologyManager(); //OWLOntology ontology =
		 * manager.loadOntologyFromOntologyDocument(IRI.create(BASE_URL));
		 * OWLOntology ontology =
		 * manager.loadOntologyFromOntologyDocument(IRI.create
		 * ("file:///c://Cguide.owl")); OWLReasonerFactory reasonerFactory =
		 * PelletReasonerFactory.getInstance(); OWLReasoner reasoner =
		 * reasonerFactory.createReasoner(ontology, new SimpleConfiguration());
		 * OWLDataFactory factory = manager.getOWLDataFactory();
		 * PrefixOWLOntologyFormat pm = (PrefixOWLOntologyFormat)
		 * manager.getOntologyFormat(ontology); pm.setDefaultPrefix(BASE_URL +
		 * "#");
		 * 
		 * //get class and its individuals OWLClass personClass =
		 * factory.getOWLClass(":Entities.action", pm); OWLNamedIndividual
		 * individual = factory.getOWLNamedIndividual(":CPG", pm);
		 * Map<OWLDataPropertyExpression, Set<OWLLiteral>> dataPropertyValues =
		 * individual
		 * .getDataPropertyValues(manager.getOntology(IRI.create(BASE_URL)));
		 * Map<OWLObjectPropertyExpression, Set<OWLIndividual>>
		 * objectPropertyValues =
		 * individual.getObjectPropertyValues(manager.getOntology
		 * (IRI.create(BASE_URL))); Entities.action action = new
		 * Entities.action(); for (Map.Entry<OWLDataPropertyExpression,
		 * Set<OWLLiteral>> entry : dataPropertyValues.entrySet()) {
		 * 
		 * String field = parseIRI(entry.getKey().toString()); Set<OWLLiteral> s
		 * = entry.getValue(); for(OWLLiteral literal : s){
		 * if(field.equals("dateOfLastUpdate"
		 * )){action.setDateofupdate(parseDateTime(literal.getLiteral()));}
		 * if(field
		 * .equals("dateOfCreation")){action.setDateofcreation(parseDateTime
		 * (literal.getLiteral()));}
		 * if(field.equals("versionNumber")){action.setVersionnumber
		 * (literal.getLiteral());}
		 * if(field.equals("actionName")){action.setactionname
		 * (literal.getLiteral());}
		 * if(field.equals("authorship")){action.setAuthorship
		 * (literal.getLiteral());}
		 * if(field.equals("actionDescription")){action.
		 * setactiondescription(literal.getLiteral());} }
		 * 
		 * } for (Map.Entry<OWLObjectPropertyExpression, Set<OWLIndividual>>
		 * entry : objectPropertyValues.entrySet()) {
		 * 
		 * System.out.println(entry); String field =
		 * parseIRI(entry.getKey().toString()); Set<OWLIndividual> s =
		 * entry.getValue(); for(OWLIndividual literal : s){
		 * //System.out.println(literal); }
		 * 
		 * }
		 * 
		 * 
		 * System.out.println(action.getAuthorship());
		 * System.out.println(action.getDateofcreation());
		 * System.out.println(action.getDateofupdate());
		 * System.out.println(action.getactiondescription());
		 * System.out.println(action.getactionname());
		 * System.out.println(action.getVersionnumber());
		 */
		// reasoner.getDataPropertyValues(individual,dataProperty);

		/*
		 * //get a given individual OWLNamedIndividual martin =
		 * factory.getOWLNamedIndividual(":CPG", pmMartin //get values of
		 * selected properties on the individual OWLDataProperty
		 * hasEmailProperty = factory.getOWLDataProperty(":hasnet",hasEmail
		 * OWLObjectProperty isEmployedAtProperty =
		 * factory.getOWLObjectProperty(":isEmployedAt", pm); ", pm); s email: +
		 * email.getLiteral()); }
		 */
		/*
		 * for (OWLNamedIndividual ind :
		 * reasoner.getObjectPropertyValues(martin,
		 * isEmployedAtProperty).getFlattened()) {
		 * y/*em.out.println("Martin is employed at: " + renderer.render(ind));
		 * }
		 * 
		 * //get labels LocalizedAnnotationSelector as = new
		 * LocalizedAnnotationSelector(ontology, factory, "en", "cs"); for
		 * (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(martin,
		 * isEmployedAtProperty).getFlattened()) {
		 * System.out.println("Martin is employed at: '" + as.getLabel(ind) +
		 * "'"); }
		 * 
		 * //get inverse of a property, i.e. which individuals are in relation
		 * with a given individual OWLNamedIndividual university =
		 * factory.getOWLNamedIndividual(":MU", pm); OWLObjectPropertyExpression
		 * inverse = factory.getOWLObjectInverseOf(isEmployedAtProperty); for
		 * (OWLNamedIndividual ind :
		 * reasoner.getObjectPropertyValues(university, inverse).getFlattened())
		 * { System.out.println("MU inverseOf(isEmployedAt) -> " +
		 * renderer.render(ind)); }
		 * 
		 * //find to which classes the individual belongs
		 * Set<OWLClassExpression> assertedClasses = martin.getTypes(ontology);
		 * for (OWLClass c : reasoner.getTypes(martin, false).getFlattened()) {
		 * boolean asserted = assertedClasses.contains(c);
		 * System.out.println((asserted ? "asserted" : "inferred") +
		 * " class for Martin: " + renderer.render(c)); }
		 * 
		 * //list all object property values for the individual
		 * Map<OWLObjectPropertyExpression, Set<OWLIndividual>> assertedValues =
		 * martin.getObjectPropertyValues(ontology); for (OWLObjectProperty
		 * objProp : ontology.getObjectPropertiesInSignature(true)) { for
		 * (OWLNamedIndividual ind : reasoner.getObjectPropertyValues(martin,
		 * objProp).getFlattened()) { boolean asserted =
		 * assertedValues.get(objProp).contains(ind);
		 * System.out.println((asserted ? "asserted" : "inferred") +
		 * " object property for Martin: " + renderer.render(objProp) + " -> " +
		 * renderer.render(ind)); } }
		 * 
		 * //list all same individuals for (OWLNamedIndividual ind :
		 * reasoner.getSameIndividuals(martin)) {
		 * System.out.println("same as Martin: " + renderer.render(ind)); }
		 * 
		 * //ask reasoner whether Martin is employed at MU boolean result =
		 * reasoner.isEntailed(factory.getOWLObjectPropertyAssertionAxiom(
		 * isEmployedAtProperty, martin, university));
		 * System.out.println("Is Martin employed at MU ? : " + result);
		 * 
		 * 
		 * //check whether the SWRL rule is used OWLNamedIndividual ivan =
		 * factory.getOWLNamedIndividual(":Ivan", pm); OWLClass chOMPClass =
		 * factory.getOWLClass(":ChildOfMarriedParents", pm);
		 * OWLClassAssertionAxiom axiomToExplain =
		 * factory.getOWLClassAssertionAxiom(chOMPClass, ivan);
		 * System.out.println("Is Ivan child of married parents ? : " +
		 * reasoner.isEntailed(axiomToExplain));
		 * 
		 * 
		 * //explain why Ivan is child of married parents
		 * DefaultExactionationGenerator exactionationGenerator = new
		 * DefaultExactionationGenerator( manager, reasonerFactory, ontology,
		 * reasoner, new SilentExactionationProgressMonitor()); Set<OWLAxiom>
		 * exactionation =
		 * exactionationGenerator.getExactionation(axiomToExplain);
		 * ExactionationOrderer deo = new ExactionationOrdererImpl(manager);
		 * ExactionationTree exactionationTree =
		 * deo.getOrderedExactionation(axiomToExplain, exactionation);
		 * System.out.println(); System.out.println(
		 * "-- exactionation why Ivan is in class ChildOfMarriedParents --");
		 * printIndented(exactionationTree, "");
		 */
	}

	// Função com capacidade de criar Bayesian Network para cada.
	// id -> question.getID();
	// questao -> ArrayList<String> com todos os nomes dos parametros
	public static String GerarBN(String id, ArrayList<String> questao) {
		String res = new String();
		try {
			String[] Rargs = { "--vanilla" };
			Rengine c = new Rengine(Rargs, false, new CallbackListener());
			if (!c.waitForR()) {
				System.out.println("Cannot load R!");
				return res;
			}

			else if (questao.size() > 0) {
				String path = "C:/1/teste/";
				StringBuilder aux = new StringBuilder();
				// Lê libraries necessárias
				c.eval("library(rJava)");
				c.eval("library(bnlearn)");

				// Lê tabela e guarda na var data
				c.eval("data <- read.table(\"" + path
						+ "colorec2.csv\", header=T, sep = \",\")");

				// Filtra as colunas que a QuestionTask apenas deseja estudar
				if (questao.size() == 1) {
					aux.append("data <- data$" + questao.get(0));
				} else {
					aux.append("data <- data[,c(");
					for (int i = 0; i < questao.size() - 1; i++) {
						aux.append(" \"" + questao.get(i) + "\" ,");
					}
					aux.append(" \"" + questao.get(questao.size() - 1)
							+ "\" )]");
				}
				// comando replaceAll serve apenas para retirar os \n existentes
				// nas strings
				c.eval(aux.toString());
				// c.eval(aux.toString().replaceAll("\n", ""));

				// Gera as três Bayesian Network HC, GS e LIU
				c.eval("bn.hc <- hc(data, score = \"aic\")");
				c.eval("bn.gs <- cextend(gs(data, optimized = TRUE, undirected = FALSE))");
				c.eval("bn.liu <- cextend(chow.liu(data))");

				// Analisa os scores e determina o máximo dos três valores
				c.eval("scores <- c(score(bn.hc,data),score(bn.gs,data),score(bn.liu,data))");
				c.eval("index <- seq(along=scores)[scores == max(scores)]");

				// O valor máximo será o que possibilitará construir o ficheiro
				// .net onde será guardada a melhor Bayesian Network
				c.eval("if(index == 1){fitted <- bn.fit(bn.hc,data,\"mle\")} else if(index == 2){fitted <- bn.fit(bn.gs,data,\"mle\")} else{fitted <- bn.fit(bn.liu,data,\"mle\")}");

				// Remove Bayesian Network com QuestionID já existente
				c.eval("if(file.exists(\"" + path + "" + id
						+ ".net\") {file.remove(\"" + path + "" + id
						+ ".net\")}");

				// Cria Bayesian Network selecionado
				c.eval("write.net(\"" + path + "" + id + ".net\", fitted)");

				// Finaliza a comunicação com R
				c.end();

				res = path + "" + id + ".net";

				return res;

			}
		} catch (Exception e) {
			// Exception bn_constructer.R not finding fullpath()
			e.printStackTrace();
		}
		return res;
	}

	// Inclui ligações de sincronização no graph. Deve ser corrido apenas após
	// comando addGerar.
	public static void addGerarSync1(ArrayList<String> visto, String plano,
			StringBuilder texto, GuidelineHandler parser) {
		// public static void addGerarSync(ArrayList<String> visto, String
		// plano, GraphViz gv, GuidelineHandler parser){
		ClinicalTask type = parser.getClinicalTask(plano);
		// System.out.println("JSON:"+type.toJson());

		if (type != null && !visto.contains(type.getId())) {

			if (type.getTaskType().equals("Question")) {
				Question clinicalTask = (Question) parser
						.getClinicalTask(plano);
				// System.out.println("QUESTION "+clinicalTask.getId());

				if (clinicalTask.getSyncTask() != null) {
					// gv.addln("\""+plano+"\"->\""+clinicalTask.getId()+"\";");
					texto.append("\"" + plano + "\"->\"" + clinicalTask.getId()
							+ "\";\n");
				}

				visto.add(clinicalTask.getId());

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerarSync(visto,c,gv,parser);
					addGerarSync1(visto, c, texto, parser);
				}

			} else if (type.getTaskType().equals("Plan")) {
				Plan clinicalTask = (Plan) parser.getClinicalTask(plano);

				if (clinicalTask.getSyncTask() != null) {
					// gv.addln("\""+plano+"\"->\""+clinicalTask.getId()+"\";");
					texto.append("\"" + plano + "\"->\"" + clinicalTask.getId()
							+ "\";\n");
				}

				visto.add(clinicalTask.getId());

				for (String c : clinicalTask.getFirstClinicalTask()) {
					// addGerarSync(visto,c,gv,parser);
					addGerarSync1(visto, c, texto, parser);
				}
			} else if (type.getTaskType().equals("Decision")) {
				Decision clinicalTask = (Decision) parser
						.getClinicalTask(plano);

				if (clinicalTask.getSyncTask() != null) {
					// gv.addln("\""+plano+"\"->\""+clinicalTask.getId()+"\";");
					texto.append("\"" + plano + "\"->\"" + clinicalTask.getId()
							+ "\";\n");
				}

				visto.add(clinicalTask.getId());

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerarSync(visto,c,gv,parser);
					addGerarSync1(visto, c, texto, parser);
				}
			} else if (type.getTaskType().equals("Action")) {
				Action clinicalTask = (Action) parser.getClinicalTask(plano);

				if (clinicalTask.getSyncTask() != null) {
					// gv.addln("\""+plano+"\"->\""+clinicalTask.getId()+"\";");
					texto.append("\"" + plano + "\"->\"" + clinicalTask.getId()
							+ "\";\n");
				}

				visto.add(clinicalTask.getId());

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerarSync(visto,c,gv,parser);
					addGerarSync1(visto, c, texto, parser);
				}
			}
		}
	}

	// Trata de implementar os nodos das ClinicalTasks e suas informações
	// adjacentes. Este é uma função recursiva, que só para ao encontrar uma
	// ClinicalTask do tipo END
	// public static void addGerar(int clusterid,ArrayList<String> visto, String
	// anterior, String plano, GraphViz gv, GuidelineHandler parser){
	public static void addGerar1(int clusterid, ArrayList<String> visto,
			String anterior, String plano, StringBuilder texto,
			GuidelineHandler parser) {
		System.out.println("Anterior: " + anterior + "|Plano: " + plano);
		ClinicalTask type = parser.getClinicalTask(plano);
		// System.out.println("JSON:"+type.toJson());
		if (type != null && visto.contains(type.getId())) {
			// gv.addln("\""+anterior+"\"->\""+plano+"\";");
			texto.append("\"" + anterior + "\"->\"" + plano + "\";\n");
		}
		if (type != null && !visto.contains(type.getId())) {

			if (type.getTaskType().equals("Question")) {
				Question clinicalTask = (Question) parser
						.getClinicalTask(plano);
				System.out.println("QUESTION " + clinicalTask.getId());
				// gv.addln("subgraph cluster"+clusterid+"{");
				// texto.append("subgraph cluster"+clusterid+"{\n");
				// clusterid++;
				// gv.addln("\""+clinicalTask.getId()+"\" [shape=box];");
				texto.append("\"" + clinicalTask.getId() + "\" [shape=box];\n");
				// gv.addln("label = \""+clinicalTask.getTaskType()+"\";");

				// gv.addln("\""+anterior+"\"->\""+clinicalTask.getId()+"\";");
				texto.append("\"" + anterior + "\"->\"" + clinicalTask.getId()
						+ "\";\n");

				for (Parameter a : clinicalTask.getParameters()) {
					// System.out.println("QUESTION2 "+clinicalTask.getId());
					// gv.addln("\""+a.getId()+"\" [shape=box];");
					texto.append("\"" + a.getId() + "\" [shape=box];\n");
					// gv.addln("\""+clinicalTask.getId()+"\"->\""+a.getId()+"\";");
					texto.append("\"" + clinicalTask.getId() + "\"->\""
							+ a.getId() + "\";\n");
					// System.out.println("QUESTION3 "+clinicalTask.getId());
					// gv.addln("\""+a.getId()+"\"->\"Question Parameter:"+a.getQuestionParameter()+"\";");
					texto.append("\"" + a.getId() + "\"->\"Question Parameter:"
							+ a.getQuestionParameter() + "\";\n");
					// System.out.println("QUESTION4 "+clinicalTask.getId());
					for (String b : a.getPossibleValue()) {
						// System.out.println("QUESTION5 "+clinicalTask.getId());
						// gv.addln("\"Question Parameter:"+a.getQuestionParameter()+"\"->\"Possible Value:"+b+"\";");
						texto.append("\"Question Parameter:"
								+ a.getQuestionParameter()
								+ "\"->\"Possible Value:" + b + "\";\n");
					}
				}
				// System.out.println("QUESTION6 "+clinicalTask.getId());
				visto.add(clinicalTask.getId());
				// System.out.println("QUESTION7 "+clinicalTask.getId());

				// gv.addln("}");
				// texto.append("}\n");

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerar(clusterid,visto,clinicalTask.getId(),c,gv,parser);
					addGerar1(clusterid, visto, clinicalTask.getId(), c, texto,
							parser);
				}

			} else if (type.getTaskType().equals("Plan")) {
				Plan clinicalTask = (Plan) parser.getClinicalTask(plano);
				System.out.println("PLAN " + clinicalTask.getId());
				// gv.addln("subgraph cluster"+clusterid+"{");
				// texto.append("subgraph cluster"+clusterid+"{\n");
				// clusterid++;
				// gv.addln("\""+clinicalTask.getId()+"\" [shape=box];");
				texto.append("\"" + clinicalTask.getId() + "\" [shape=box];\n");
				// gv.addln("label = \""+clinicalTask.getTaskType()+"\";");
				// gv.addln("\""+anterior+"\"->\""+clinicalTask.getId()+"\";");
				texto.append("\"" + anterior + "\"->\"" + clinicalTask.getId()
						+ "\";\n");
				// gv.addln("\""+clinicalTask.getId()+"\""+"->\"Description:"+clinicalTask.getGeneralDescription()+"\";");

				for (TriggerCondition a : clinicalTask.getTriggerCondition()) {
					for (ConditionSet z : a.getTriggerConditionSet()) {
						for (Condition x : z.getCondition()) {
							// gv.addln("\""+clinicalTask.getId()+"\"->\"Condition:"+x.getConditionParameter()+" "+x.getComparisonOperator()+" "+x.getValue()+"\";");
							texto.append("\"" + clinicalTask.getId()
									+ "\"->\"Condition:"
									+ x.getConditionParameter() + " "
									+ x.getComparisonOperator() + " "
									+ x.getValue() + "\";\n");
						}
					}
				}
				visto.add(clinicalTask.getId());

				// gv.addln("}");
				// texto.append("}\n");

				for (String c : clinicalTask.getFirstClinicalTask()) {
					// addGerar(clusterid,visto,clinicalTask.getId(),c,gv,parser);
					addGerar1(clusterid, visto, clinicalTask.getId(), c, texto,
							parser);
				}
			} else if (type.getTaskType().equals("Decision")) {
				Decision clinicalTask = (Decision) parser
						.getClinicalTask(plano);
				System.out.println("DECISION " + clinicalTask.getId());
				// texto.append("subgraph cluster"+clusterid+"{\n");
				// clusterid++;
				// gv.addln("\""+clinicalTask.getId()+"\" [shape=diamond];");
				texto.append("\"" + clinicalTask.getId()
						+ "\" [shape=diamond];\n");
				// gv.addln("label = \""+clinicalTask.getTaskType()+"\";");
				// gv.addln("\""+anterior+"\"->\""+clinicalTask.getId()+"\";");
				texto.append("\"" + anterior + "\"->\"" + clinicalTask.getId()
						+ "\";\n");
				// gv.addln("\""+clinicalTask.getId()+"\""+"->\"Description:"+clinicalTask.getGeneralDescription()+"\";");

				for (Option a : clinicalTask.getOptions()) {
					// gv.addln("\""+a.getId()+"\" [shape=box];");
					texto.append("\"" + a.getId() + "\" [shape=box];\n");

					// gv.addln("\""+clinicalTask.getId()+"\"->\""+a.getId()+"\";");
					texto.append("\"" + clinicalTask.getId() + "\"->\""
							+ a.getId() + "\";\n");
					for (ConditionSet b : a.getOptionConditionSet()) {
						for (Condition x : b.getCondition()) {
							// gv.addln("\""+a.getId()+"\""+"->\"Condition:"+x.getConditionParameter()+" "+x.getComparisonOperator()+" "+x.getValue()+"\";");
							texto.append("\"" + a.getId() + "\""
									+ "->\"Condition:"
									+ x.getConditionParameter() + " "
									+ x.getComparisonOperator() + " "
									+ x.getValue() + "\";\n");
						}
					}
				}
				visto.add(clinicalTask.getId());

				// gv.addln("}");
				// texto.append("}\n");

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerar(clusterid,visto,clinicalTask.getId(),c,gv,parser);
					addGerar1(clusterid, visto, clinicalTask.getId(), c, texto,
							parser);
				}
			} else if (type.getTaskType().equals("Action")) {
				// System.out.println("ACTION1");
				Action clinicalTask = (Action) parser.getClinicalTask(plano);
				System.out.println("ACTION " + clinicalTask.getId());
				// gv.addln("subgraph cluster"+clusterid+"{");
				// texto.append("subgraph cluster"+clusterid+"{\n");
				// clusterid++;
				// gv.addln("\""+clinicalTask.getId()+"\" [shape=box];");
				texto.append("\"" + clinicalTask.getId() + "\" [shape=box];\n");
				// gv.addln("label = \""+clinicalTask.getTaskType()+"\";");
				// gv.addln("\""+anterior+"\"->\""+clinicalTask.getId()+"\";");
				texto.append("\"" + anterior + "\"->\"" + clinicalTask.getId()
						+ "\";\n");
				// gv.addln("\""+clinicalTask.getId()+"\""+"->\"Description:"+clinicalTask.getGeneralDescription()+"\";");
				for (ClinicalAction a : clinicalTask.getClinicalActions()) {
					// gv.addln("\""+clinicalTask.getId()+"\"->\"ClinicalAction:"+a.getName()+"\";");
					texto.append("\"" + clinicalTask.getId()
							+ "\"->\"ClinicalAction:" + a.getName() + "\";\n");
				}
				visto.add(clinicalTask.getId());

				// gv.addln("}");
				// texto.append("}\n");

				for (String c : clinicalTask.getClinicalTasks()) {
					// addGerar(clusterid,visto,clinicalTask.getId(),c,gv,parser);
					addGerar1(clusterid, visto, clinicalTask.getId(), c, texto,
							parser);
				}
			} else if (type.getTaskType().equals("End")) {
				End clinicalTask = (End) parser.getClinicalTask(plano);
				System.out.println("END " + clinicalTask.getId());
				// gv.addln("\""+clinicalTask.getId()+"\" [shape=Msquare];");
				texto.append("\"" + clinicalTask.getId()
						+ "\" [shape=Msquare];\n");
				// gv.addln("\""+anterior+"\"->\""+clinicalTask.getId()+"\";");
				texto.append("\"" + anterior + "\"->\"" + clinicalTask.getId()
						+ "\";\n");
			}
		}

	}

	// Inicializa os comandos gerar para formular um grafo da clinical guideline
	public static void gerar(Guideline guide, GuidelineHandler parser) {

		Plan plano = guide.getPlan();
		Scope scope = guide.getScope();

		StringBuilder texto = new StringBuilder();
		texto.append("digraph G {\n");

		// GraphViz gv = new GraphViz();
		// gv.addln(gv.start_graph());

		int clusterid = 0;

		ArrayList<String> visto = new ArrayList<String>();

		visto.add(guide.getId());
		// gv.addln("subgraph cluster"+clusterid+"{");
		// texto.append("subgraph cluster"+clusterid+"{\n");
		// clusterid++;
		// gv.addln("nodes[ranksep=3,nodesep=3];");
		// texto.append("nodes[ranksep=3,nodesep=3];\n");
		// gv.addln("\""+guide.getId()+"\""+" [shape=Mdiamond];");
		texto.append("\"" + guide.getId() + "\"" + " [shape=Mdiamond];\n");
		// gv.addln("label = \""+guide.getGuidelinename()+"\";");
		// gv.addln("\""+guide.getId()+"\""+"->\"Description:"+guide.getGuidelinedescription()+"\";");
		for (String a : scope.getHasClinicalSpeciality()) {
			texto.append("\"" + a + " Speciatly\" [shape=box3d];\n");
			texto.append("\"" + guide.getId() + "\"" + "->\"" + a
					+ " Speciatly\";\n");
			// gv.addln("\""+a+" Speciatly\" [shape=box3d];");
			// gv.addln("\""+guide.getId()+"\""+"->\""+a+" Speciatly\";");
		}
		for (ConditionSet z : scope.getConditionSet()) {
			for (Condition c : z.getCondition()) {
				texto.append("\"" + guide.getId() + "\"" + "->\"Condition:"
						+ c.getConditionParameter() + " "
						+ c.getComparisonOperator() + " " + c.getValue()
						+ "\";\n");
				// gv.addln("\""+guide.getId()+"\""+"->\"Condition:"+c.getConditionParameter()+" "+c.getComparisonOperator()+" "+c.getValue()+"\";");
			}
		}

		// gv.addln("}");
		// texto.append("}\n");

		addGerar1(clusterid, visto, guide.getId(), plano.getId(), texto, parser);

		visto = new ArrayList<String>();

		addGerarSync1(visto, plano.getId(), texto, parser);

		/*
		 * 
		 * 
		 * gv.addln("subgraph sync{"); gv.addln("edge[style=dotted]");
		 * 
		 * gv.addln("}");
		 */
		// gv.addln(gv.end_graph());
		texto.append("}\n");
		System.out.println("TESTEFIM");

		// String type = "txt";
		// String type = "gif";
		// String type = "fig"; // open with xfig
		// String type = "pdf";
		// String type = "ps";
		// String type = "svg"; // open with inkscape
		// String type = "png";
		// String type = "plain";
		String type = "dot";

		File out = new File("c:\\1\\" + guide.getId() + "." + type); // Windows
		// File out = new File("c:\\1\\teste." + type); // Windows

		try {
			byte[] img = String.valueOf(texto).getBytes();
			FileOutputStream fos = new FileOutputStream(out);
			fos.write(img);
			fos.close();
		} catch (java.io.IOException ioe) {
			System.out.println("ERRO NA CRIAÇÂO FICHEIRO");
		}

		// gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), type ), out);

	}

	public static String parseIRI(String iri) {
		Pattern pattern = Pattern.compile("#(.*?)>");
		Matcher matcher = pattern.matcher(iri);
		if (matcher.find()) {
			return (matcher.group(1));
		}
		return null;
	}

	public static String parseDateTime(String datetime) {
		return datetime.replaceAll("T", " ");
	}
}
