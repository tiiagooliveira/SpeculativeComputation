package agents;

import java.util.Hashtable;

import speculative.module.LoadProlog;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jpl.Atom;
import jpl.Compound;
import jpl.Query;
import jpl.Term;
import jpl.Variable;

public class Cdss extends Agent {

	private Agent cdss;

	protected void setup() {

		cdss = this;
		
		System.out.println("cdss: "+cdss.getLocalName()+" is active!");

		/*
		 * Variable definition for the main query.
		 * Structure of the terms for the main queries.
		 */
		
		Hashtable solutions1;
		//Variable list=new Variable("List");
		Term arg[]={new Atom("q02")};

		/*
		 * Load Prolog file with the speculative computation module.
		 * The file is located at C:\Users\Tiago\Documents\workspace\SpeculativeComputationSystem .
		 */
		System.out.println("cdss:Initializing the Speculative Computation Module.");
		Query q1 = new Query("consult", new Term[] {new Atom("C:/Users/Tiago/Documents/workspace/SpeculativeComputationSystem/spc_componentv2.pl")});
		q1.query();

		/*
		 * Initialization of the current belief set based on the defaults.
		 */
		System.out.println("cdss:Initializing the belief set.");
		Query q2 = new Query("init");
		q2.open();
		System.out.println(q2.getSolution());
		q2.close();
		
		/*
		 * Query to show the current belief set.
		 */
		Query q3 = new Query("listing",new Term[] {new Atom("cbs")});
		q3.open();
		System.out.println("cdss:The current belief set is:");
		System.out.println(q3.getSolution());
		q3.close();

		/*
		 * Main query to get the next task.
		 */
		Query q4 = new Query("query",new Compound("next",arg));		
		solutions1=q4.oneSolution();
		
		if(!solutions1.isEmpty()){
			
			System.out.println("cdss:Calculating the next task in the clinical process...");
			System.out.println("cdss:The alternative tasks are:");
			System.out.println(solutions1.get("List"));
			
		}
		
		/*
		 * Listing the suspended processes after the query.
		 */
		
		Query q15 = new Query("listing",new Term[] {new Atom("process")});
		q15.open();
		System.out.println("cdss: The set of processes is:");
		System.out.println(q15.getSolution());
		q15.close();
		
		/*
		 * Listing the active processes after the query.
		 */
		
		Query q5 = new Query("listing",new Term[] {new Atom("active")});
		q5.open();
		System.out.println("cdss: The set of active processes is:");
		System.out.println(q5.getSolution());
		q5.close();
		
		/*
		 * Listing the suspended processes after the query.
		 */
		
		Query q6 = new Query("listing",new Term[] {new Atom("suspended")});
		q6.open();
		System.out.println("cdss: The set of suspended processes is:");
		System.out.println(q6.getSolution());
		q6.close();
		
		/*
		 * Listing the terminated processes after the query.
		 */
		
		Query q7 = new Query("listing",new Term[] {new Atom("terminated")});
		q7.open();
		System.out.println("cdss: The set of terminated processes is:");
		System.out.println(q7.getSolution());
		q7.close();

		/*
		 * Cyclic behaviour to handle answers from the other agents. 
		 * Tiker behaviour to  pose questions to the other agents.
		 * Message handling and Speculative Computation 
		 */
		addBehaviour(new AskAgents(cdss,5000));
		addBehaviour(new HandleAnswer());
	}


	public class AskAgents extends TickerBehaviour{

		public AskAgents(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}

		public void onTick(){

			/*
			 * Definition of variables to store the questions to be asked to the information sources.
			 */
			Variable Receiver=new Variable("Receiver");
			Variable Value=new Variable("Value");

			/*
			 * Hashtable to store the queries.
			 */
			Hashtable[] solution;

			/*
			 * Query to retrieve the questions.
			 */
			Query q8 = new Query("check_ask",new Term[] {Value,Receiver});		
			solution=q8.allSolutions();

			/*
			 * If the hashtable containing the questions is not empty, then the cdss processes the
			 * questions and sends them to the information sources.
			 */
			if(solution.length!=0){
				for(int i=0;i<solution.length;i++){
					System.out.println("cdss:Asked the value of "+solution[i].get("Value").toString()+" from "+solution[i].get("Receiver").toString()+".");
					sendmsg(solution[i].get("Receiver").toString(),solution[i].get("Value").toString());

				}

			} 	

		}
	}

	public class HandleAnswer extends CyclicBehaviour {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     

		@Override
		public void action (){

			/*
			 * This behaviour processes the answers from the information sources.
			 */
			ACLMessage answer;

			if((answer=cdss.receive())!= null){
				
				try {
					String[] response=(String[])answer.getContentObject();
					
					System.out.println("cdss:Received message from "+answer.getSender().getLocalName()+" with the answer ("+response[0]+","+response[1]+","+response[2]+").");
					
					
					System.out.println("cdss:Asserting answer ("+response[0]+","+response[1]+","+response[2]+") to the Speculative Computation Module.");
					Query q9 = new Query("answer_arrival", new Term[] {new Atom(response[0]),new Atom(answer.getSender().getLocalName()),new Atom (response[1]),new Atom(response[2])});
					System.out.println(q9.hasSolution());
					
					Query q10 = new Query("listing",new Term[] {new Atom("answer")});
					q10.open();
					System.out.println("cdss: The answer set is:");
					System.out.println(q10.getSolution());
					q10.close();
					
					Query q11 = new Query("listing",new Term[] {new Atom("cbs")});
					q11.open();
					System.out.println("cdss: The belief set is:");
					System.out.println(q11.getSolution());
					q11.close();
					
					Query q16 = new Query("listing",new Term[] {new Atom("process")});
					q16.open();
					System.out.println("cdss: The set of processes is:");
					System.out.println(q16.getSolution());
					q16.close();
					
					Query q12 = new Query("listing",new Term[] {new Atom("active")});
					q12.open();
					System.out.println("cdss: The set of active processes is:");
					System.out.println(q12.getSolution());
					q12.close();
					
					Query q13 = new Query("listing",new Term[] {new Atom("suspended")});
					q13.open();
					System.out.println("cdss: The set of suspended processes is:");
					System.out.println(q13.getSolution());
					q13.close();
					
					Query q14 = new Query("listing",new Term[] {new Atom("terminated")});
					q14.open();
					System.out.println("cdss: The set of terminated processes is:");
					System.out.println(q14.getSolution());
					q14.close();
					
					
				} catch (UnreadableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else{
				block();
			}
		} 
	}

	private void sendmsg(String agentName, String Content) {
		AID receiver = new AID();
		receiver.setLocalName(agentName);
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(receiver);
		msg.setContent(Content);
		msg.setReplyWith(System.currentTimeMillis() + "");
		cdss.send(msg);
	}

}