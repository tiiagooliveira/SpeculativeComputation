package speculative.module;

import java.util.Hashtable;

import jpl.Atom;
import jpl.Compound;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import jpl.Variable;

public class LoadProlog {
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Variable list=new Variable("List");
		//Variable apl=new Variable("APL");
		//Variable spl=new Variable("SPL");
		Term arg[]={new Atom("q01"),list};
	
		
			
		Query q1 = new Query("consult", new Term[] {new Atom("C:/Users/Tiago/Documents/workspace/SpeculativeComputationSystem/spc_component.pl")});
		//Query q1 = new Query("pwd");
		q1.query();
		//System.out.println(q1.getSolution());
		//q1.close();
		

		
		System.out.println("cdss: Initializing the current belief set.");
		Query q2 = new Query("init");
		q2.open();
		System.out.println(q2.getSolution());
		q2.close();
		
		Query q4 = new Query("query",new Compound("next",arg));		
		Hashtable solutions1 = q4.oneSolution();
		System.out.println(solutions1.get("List"));
		
		Query q3 = new Query("answer_conversion", new Term[] {new Atom("ex(a01)"),new Atom("cp"),new Atom ("equal"),new Atom("1")});
		//Query q1 = new Query("pwd");
		//q3.open();
		System.out.println(q3.hasSolution());
		//q3.close();
		
		//Query q2 = new Query("rain",new Term[] {new Atom ("no")});
		//Query q2 = new Query("rain",arg);
		
		//Query q4 = new Query("check",new Term[] {new Atom("pis"),new Atom("t")});		
		//q4.open();
		//System.out.println("ola"+q4.getSolution());
		//q4.close();
		
		
		//Query q2 = new Query("query",new Compound("nt",arg));

		//Hashtable solution;
		//solution=q2.oneSolution();
		//q2.open();
		
		/*Obtain the truth value of the query
		 * */
		
		//System.out.println(q2.oneSolution());
		
		//solution=q2.oneSolution();
		//System.out.println(q2.oneSolution());
		//System.out.println(solution.isEmpty());
		//System.out.println(solution.length);
		//q2.close();
		
		Query q10 = new Query("listing",new Term[] {new Atom("answer")});
		q10.open();
		System.out.println("cdss: The answer set is:");
		System.out.println(q10.getSolution());
		q10.close();

		//Query q5 = new Query("listing",new Term[] {new Atom("here")});
		//q5.open();
		//System.out.println("cdss: The current belief set is:");
		//System.out.println(q5.getSolution());
		//q5.close();
		

		//Variable Receiver=new Variable("Receiver");
		//Variable Value=new Variable("Value");
		
		//Hashtable[] solution=null;
		//Query q4 = new Query("check",new Term[] {Receiver,Value});		
		//solution=q4.allSolutions();
		//System.out.println(solution.length);
		//System.out.println(solution[0].get("Value"));
		
		/*while ( q3.hasMoreSolutions() ){ 
		    solution = q3.nextSolution(); 
		    System.out.println( q3.getSolution()); 
		}*/
		//System.out.println("The first solution for the address of 1 is "+solution.get(X));
	}

}
