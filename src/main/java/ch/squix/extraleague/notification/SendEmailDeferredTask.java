package ch.squix.extraleague.notification;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class SendEmailDeferredTask implements DeferredTask {
	
    private static final Logger log = Logger.getLogger(SendEmailDeferredTask.class.getName());
    private static final Joiner JOINER = Joiner.on(", ");
	private String subject;
	private String msgBody;
	private List<String> recipients;


    public SendEmailDeferredTask(String subject, String msgBody, List<String> recipients) {
		this.subject = subject;
		this.msgBody = msgBody;
		this.recipients = recipients;
    }
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void run() {
        if (recipients.size() == 0) {
            log.info("No notifications enabled. Not sending email");
            return;
        }
        log.info("Sending email to " + JOINER.join(recipients));
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("squix78@gmail.com", "NCA League Admin"));
            for (String recipient : recipients) {
            	if (!Strings.isNullOrEmpty(recipient)) {
            		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            	}
            }
            msg.setSubject(subject);
            msg.setText(msgBody, "utf-8", "html");
            Transport.send(msg);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not send email", e);
        }
	}

}
