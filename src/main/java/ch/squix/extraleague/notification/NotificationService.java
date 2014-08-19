package ch.squix.extraleague.notification;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.codehaus.jackson.map.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.rest.notification.NotificationTokenResource;
import ch.squix.extraleague.rest.result.PlayerScoreDto;
import ch.squix.extraleague.rest.result.SummaryDto;
import ch.squix.extraleague.rest.result.SummaryService;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;

public class NotificationService {

    private static final Logger log = Logger.getLogger(NotificationTokenResource.class.getName());
    private static final Joiner JOINER = Joiner.on(", ");

    public static void sendMessage(NotificationMessage message) {
        try {
            List<BrowserClient> clients = ofy().load().type(BrowserClient.class).list();
            ChannelService channelService = ChannelServiceFactory.getChannelService();
            ObjectMapper mapper = new ObjectMapper();
            String payload = mapper.writeValueAsString(message);
            List<BrowserClient> oldClients = new ArrayList<>();
            long maxClientAge = new Date().getTime() - 1000 * 60 * 60 * 2;

            for (BrowserClient client : clients) {
                if (client.getCreatedDate().getTime() > maxClientAge) {
                    ChannelMessage channelMessage = new ChannelMessage(client.getToken(), payload);
                    channelService.sendMessage(channelMessage);
                } else {
                    oldClients.add(client);
                }
            }
            if (oldClients.size() > 0) {
                ofy().delete().entities(oldClients).now();
            }
        } catch (IOException e) {
            log.log(Level.SEVERE, "Could not convert message to json", e);
        }
    }

    public static void sendSummaryEmail(Game game, List<Match> matches) {
        SummaryDto summaryDto = SummaryService.getSummaryDto(game, matches);
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<table><thead><tr>");
        emailBody.append("<th>Player</th><th>Won</th><th>Goals</th><th>Elo</th>");
        emailBody.append("</tr></thead><tbody>");
        for (PlayerScoreDto playerScore : summaryDto.getPlayerScores()) {
            emailBody.append("<tr><td>");
            emailBody.append(playerScore.getPlayer());
            emailBody.append("</td><td>");
            emailBody.append(playerScore.getScore());
            emailBody.append("</td><td>");
            emailBody.append(playerScore.getGoals());
            emailBody.append("</td><td>");
            emailBody.append(playerScore.getEarnedEloPoints());
            emailBody.append("</td></tr>");
        }
        emailBody.append("</tbody></table>");
        emailBody.append("Note: you can disable this notification in the user section.");
        List<PlayerUser> players = ofy().load()
                .type(PlayerUser.class)
                .filter("player in", game.getPlayers())
                .list();
        List<String> recipients = new ArrayList<>();
        recipients.add("dani.eichhorn@squix.ch");
        for (PlayerUser player : players) {
            Boolean isEmailNotificationEnabled = player.getEmailNotification();
            String recipient = player.getEmail();
            if (isEmailNotificationEnabled != null && isEmailNotificationEnabled
                    && !Strings.isNullOrEmpty(recipient)) {
                recipients.add(recipient);
            }
        }

        sendEmail("Summary Game with " + JOINER.join(game.getPlayers()), emailBody.toString(),
                recipients);
    }

    private static void sendEmail(String subject, String msgBody, List<String> recipients) {
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

    public static void sendMeetingPointMessage(String subject, String message) {
    	List<PlayerUser> usersWithNotification = ofy().load().type(PlayerUser.class).filter("meetingPointNotification =", true).list();
        if (usersWithNotification.isEmpty()) {
        	return;
        }
    	try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("squix78@gmail.com", "NCA League Admin"));
            for (PlayerUser user : usersWithNotification) {
            	String recipient = user.getEmail();
            	if(!Strings.isNullOrEmpty(recipient)) {
            		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            	}
            	String pushBulletApiKey = user.getPushBulletApiKey();
            	if (!Strings.isNullOrEmpty(pushBulletApiKey)) {
            		sendPushBulletNote(pushBulletApiKey, subject, message);
            	}
            }

            msg.setSubject(subject);
            msg.setText(message, "utf-8", "html");
            Transport.send(msg);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not send email", e);
        }
    }

    public static void sendAdminMessage(String subject, String body) {
        try {
            Properties props = new Properties();
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("squix78@gmail.com", "NCA League Admin"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("squix78@gmail.com"));

            msg.setSubject(subject);
            // msg.setText(msgBody);
            msg.setText(body, "utf-8", "html");
            Transport.send(msg);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not send email", e);
        }
    }
    
    public static void sendPushBulletMessage(String apiKey, String httpBody) {
        try {
            URL url = new URL("https://api.pushbullet.com/v2/pushes");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Authorization",
            		"Basic "+ Base64.encodeBase64String((apiKey).getBytes()));

            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(httpBody);
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                log.log(Level.INFO, "Sent message");
            } else {
            	log.log(Level.SEVERE, "Could not send message: " + connection.getResponseCode() );
            }
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Server does not support UTF-8 encoding");
        } catch (MalformedURLException e) {
        	log.log(Level.SEVERE, "Could not send message: ", e);
        } catch (IOException e) {
        	log.log(Level.SEVERE, "Could not send message: ", e);
        }
    }
    public static void sendPushBulletNote(String apiKey, String title, String body) {
        try {
            StringBuilder httpBody = new StringBuilder();
            httpBody.append("type=note&");
            httpBody.append("title="+URLEncoder.encode(title, "UTF-8")+"&");
            httpBody.append("body="+URLEncoder.encode(body, "UTF-8"));
            sendPushBulletMessage(apiKey, httpBody.toString());
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Server does not support UTF-8 encoding");
        }
    }

    public static void sendPushBulletLink(String apiKey, String title, String url, String body) {
        try {
            StringBuilder httpBody = new StringBuilder();
            httpBody.append("type=link&");
            httpBody.append("title="+URLEncoder.encode("Game starts now.", "UTF-8")+"&");
            httpBody.append("url="+URLEncoder.encode(url, "UTF-8")+"&");
            if (!Strings.isNullOrEmpty(body)) {
                httpBody.append("body="+URLEncoder.encode(body, "UTF-8"));
            }

            sendPushBulletMessage(apiKey, httpBody.toString());
        } catch (UnsupportedEncodingException e) {
            log.log(Level.SEVERE, "Server does not support UTF-8 encoding");
        }
    }
    
    public static void callWebHooksForEndOfGame(Long gameId) {
		QueueFactory.getDefaultQueue().add(
				TaskOptions.Builder.withMethod(Method.POST).url("/rest/notifications/endOfGame/" + gameId));
    }
}
