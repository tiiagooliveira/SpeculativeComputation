package agents;


import java.io.IOException;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Cp extends Agent {

	private Agent cp;
	private boolean sent=false;

	protected void setup() {

		cp = this;
		System.out.println("cp: "+cp.getLocalName()+" is active!");
		addBehaviour(new answer());
	}

	public class answer extends CyclicBehaviour {

		@Override
		public void action (){

			ACLMessage answer;
			if((answer= cp.receive())!= null){

				System.out.println("cp:Received a question from "+answer.getSender().getLocalName()+".");
				System.out.println("cp:The question is what is the value of "+answer.getContent()+".");
				System.out.println("cp:The answer for the value of "+answer.getContent()+" is not currently available.");
				
				cp.doWait(120000);
				
				System.out.println("cp:There is an answer for the value of "+answer.getContent()+" available.");
				
				String question=answer.getContent();

				String[] message=new String[3];

				switch(question){

				case "ex(a01)":
					message[0]="ex(a01)";
					message[1]="equal";
					message[2]="1";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "ex(a02)":
					message[0]="ex(a02)";
					message[1]="equal";
					message[2]="1";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "ex(a03)":
					message[0]="ex(a03)";
					message[1]="equal";
					message[2]="0";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "ex(a04)":
					message[0]="ex(a04)";
					message[1]="equal";
					message[2]="0";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "ex(a05)":
					message[0]="ex(a05)";
					message[1]="equal";
					message[2]="1";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "ex(a06)":
					message[0]="ex(a06)";
					message[1]="equal";
					message[2]="1";
					try {
						sendmsg(answer.getSender().getLocalName(),message);
						System.out.println("cp:Sent answer for the value of "+answer.getContent()+" to "+answer.getSender().getLocalName()+".");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					System.out.println("cp:The parameter "+question+" is not valid.");
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
		cp.send(msg);
	}
}