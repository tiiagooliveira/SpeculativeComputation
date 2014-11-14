import java.util.Hashtable;

import jpl.Atom;
import jpl.Compound;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import jpl.Variable;

public class Prolog_query {
	/*
	 * Load Prolog file with the speculative computation module. The file is
	 * located at
	 * C:\Users\Tiago\Documents\workspace\SpeculativeComputationSystem .
	 */

	public static void Prolog_init(map_res MAP) {

		JPL.init();

		System.out.println("cdss:Initializing Speculative Computation Module.");

		// System.out.println("Initializing the Speculative Computation Module.");
		Query consult_query1 = new Query("consult", new Term[] { new Atom(
				"C:/Users/Filipe/Documents/Prolog/spc_componentv2.pl") });
		boolean query1 = consult_query1.query();

		// System.out.println("Initializing CaseStudy Module.");
		Query consult_query2 = new Query("consult", new Term[] { new Atom(
				"C:/Users/Filipe/Documents/Prolog/casestudy1v2.pl") });
		boolean query2 = consult_query2.query();

		if (query1 && query2) {
			Prolog_add_facts(MAP);
		} else {
			System.err.println("cdss:Consult failed");
			System.exit(1);
		}
	}

	public static void Prolog_add_facts(map_res MAP) {

		Query assert_query;
		System.out.println("cdss:Defining Default Values.");
		for (String[] a : MAP.getList_variables()) {
			// Example ->
			// assert(delta(m(M)@pis,M,expression(in_set([m0]),list)))
			// assert_query = new Query("delta_conversion", new Term[] { new
			// Atom(a[0]) , new Atom("pis") , new Atom("in_set") , new
			// Atom(a[1]) });
			assert_query = new Query("delta_conversion", new Term[] {
					new Atom(a[0]), new Atom(a[1]) });
			assert_query.oneSolution();
		}
		/*
		Query listing_delta = new Query("listing", new Term[] { new Atom(
				"delta") });
		listing_delta.open();
		System.out.println("Delta: The answer set is:");
		System.out.println(listing_delta.getSolution());
		listing_delta.close();
		*/

		/*
		 * Initialization of the current belief set based on the defaults.
		 */
		
		System.out.println("cdss:Initializing the belief set.");
		Query q2 = new Query("init");
		q2.open();
		System.out.println(q2.getSolution());
		q2.close();

		/*
		 * Query to show the currsent belief set.
		 */
		Query q3 = new Query("listing", new Term[] { new Atom("cbs") });
		q3.open();
		System.out.println("cdss:The current belief set is:");
		System.out.println(q3.getSolution());
		q3.close();

		/*
		 * Main query to get the next task.
		 */
		Term arg[] = {new Atom("q02")};
		
		Query q4 = new Query("query", new Compound("next", arg));
		Hashtable solutions1 = q4.oneSolution();

		if (!solutions1.isEmpty()) {

			System.out
					.println("cdss:Calculating the next task in the clinical process...");
			System.out.println("cdss:The alternative tasks are:");
			System.out.println(solutions1.get("List"));

		}

		/*
		 * Listing the suspended processes after the query.
		 */

		Query q15 = new Query("listing", new Term[] { new Atom("process") });
		q15.open();
		System.out.println("cdss: The set of processes is:");
		System.out.println(q15.getSolution());
		q15.close();

		/*
		 * Listing the active processes after the query.
		 */

		Query q5 = new Query("listing", new Term[] { new Atom("active") });
		q5.open();
		System.out.println("cdss: The set of active processes is:");
		System.out.println(q5.getSolution());
		q5.close();

		/*
		 * Listing the suspended processes after the query.
		 */

		Query q6 = new Query("listing", new Term[] { new Atom("suspended") });
		q6.open();
		System.out.println("cdss: The set of suspended processes is:");
		System.out.println(q6.getSolution());
		q6.close();

		/*
		 * Listing the terminated processes after the query.
		 */

		Query q7 = new Query("listing", new Term[] { new Atom("terminated") });
		q7.open();
		System.out.println("cdss: The set of terminated processes is:");
		System.out.println(q7.getSolution());
		q7.close();
	}
}
