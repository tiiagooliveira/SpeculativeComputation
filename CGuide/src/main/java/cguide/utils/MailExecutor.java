package cguide.utils;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

public class MailExecutor {
	
	private ExecutorService executor;

	private static MailExecutor instance = new MailExecutor();

	private  MailExecutor() {
		executor = Executors.newCachedThreadPool();
	}

    public static MailExecutor getInstance(){
        return instance;
    }
	
	public void addMailJob(Message mail, Session mailSession){
		executor.submit(new SenderMail(mail, mailSession));
	}
	
	public class SenderMail implements Runnable {
		
			Message mail;
        Session mailSession;

		public SenderMail(Message mail, Session mailSession) {
			this.mail = mail;
			this.mailSession = mailSession;
		}

		@Override
		public void run() {

            Transport transport = null;
            try {
                transport = mailSession.getTransport("smtp");
                transport.connect();
                mail.setHeader("Content-Type", "text/plain; charset=\"us-ascii\";");
                transport.sendMessage(mail, mail.getAllRecipients());
            }catch (Exception e){
            } finally {
                if (transport != null){
                    try{
                    transport.close();
                    }catch (Exception e){
                     }
                }
            }
		}

	}
}
