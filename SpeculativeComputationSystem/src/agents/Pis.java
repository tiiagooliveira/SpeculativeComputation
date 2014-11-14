package agents;

import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class Pis extends Agent {

	private Agent pis;

	protected void setup() {

		pis = this;
		System.out.println("pis: "+pis.getLocalName()+" is active!");
		
		addBehaviour(new answer());
	}

	public class answer extends CyclicBehaviour {

		@Override
		public void action (){

			ACLMessage answer;
			if((answer= pis.receive())!= null){

				System.out.println("pis:Received a question from "+answer.getSender().getLocalName()+".");
				System.out.println("pis:The question is what is the value of "+answer.getContent()+".");
				System.out.println("pis:The answer for the value of "+answer.getContent()+" is not currently available.");
				
				pis.doWait(10000);
				
				System.out.println("pis:There is an answer for the value of "+answer.getContent()+" available.");
				
				String question=answer.getContent();

				String[] message = new String[3];

				switch(question){

				case "t":
					message[0]="t";
					message[1]="in_set";
					message[2]="t1";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "n":
					message[0]="n";
					message[1]="in_set";
					message[2]="n0";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "m":
					message[0]="m";
					message[1]="in_set";
					message[2]="m0";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "gdr":
					message[0]="gdr";
					message[1]="in_set";
					message[2]="male";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "aob":
					message[0]="aob";
					message[1]="equal";
					message[2]="108";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "trig":
					message[0]="trig";
					message[1]="equal";
					message[2]="152";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "hdl":
					message[0]="hdl";
					message[1]="equal";
					message[2]="34";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "sbp":
					message[0]="sbp";
					message[1]="equal";
					message[2]="140";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "dbp":
					message[0]="dbp";
					message[1]="equal";
					message[2]="90";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "fg":
					message[0]="fg";
					message[1]="equal";
					message[2]="116";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("pis:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					System.out.println("pis:The parameter "+question+" is not valid.");
				}
			}
			else{
				block ();
			}
		}

	}
	private void sendmsg(String agentName, String[] Content) throws IOException {
		AID receiver = new AID();
		receiver.setLocalName(agentName);
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(receiver);
		msg.setContentObject(Content);
		msg.setReplyWith(System.currentTimeMillis() + "");
		pis.send(msg);
	}
}