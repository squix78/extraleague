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
import java.util.Arrays;
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
import com.google.appengine.api.taskqueue.Queue;
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
        
        addEmailToSendQueue("Summary Game with " + JOINER.join(game.getPlayers()), emailBody.toString(), recipients);

    }

    public static void addEmailToSendQueue(String subject, String emailBody, List<String> recipients) {
		Queue notificationQueue = QueueFactory.getQueue("notification");
        SendEmailDeferredTask emailTask = new SendEmailDeferredTask(
        		subject, 
        		emailBody.toString(),
                recipients);
		notificationQueue.add(TaskOptions.Builder.withPayload(emailTask));
	}
	

    public static void addPushBulletMessageToSendQueue(String pushBulletApiKey, String subject, String message) {
		Queue notificationQueue = QueueFactory.getQueue("notification");
        SendPushBulletDeferredTask pushBulletTask = SendPushBulletDeferredTask.createPushBulletNoteTask(pushBulletApiKey, subject, message);
		notificationQueue.add(TaskOptions.Builder.withPayload(pushBulletTask));
	}
    public static void addPushBulletLinkMessageToSendQueue(String apiKey, String title, String url, String body) {
    	Queue notificationQueue = QueueFactory.getQueue("notification");
    	SendPushBulletDeferredTask pushBulletTask = SendPushBulletDeferredTask.sendPushBulletLink(apiKey, title, url, body);
    	notificationQueue.add(TaskOptions.Builder.withPayload(pushBulletTask));
    }



    public static void sendMeetingPointMessage(String subject, String message) {
    	List<PlayerUser> usersWithNotification = ofy().load().type(PlayerUser.class).filter("meetingPointNotification =", true).list();
        if (usersWithNotification.isEmpty()) {
        	return;
        }
    	try {

            List<String> recipients = new ArrayList<>();
            for (PlayerUser user : usersWithNotification) {
            	String recipient = user.getEmail();
            	if(!Strings.isNullOrEmpty(recipient)) {
            		recipients.add(recipient);
            	}
            	String pushBulletApiKey = user.getPushBulletApiKey();
            	if (!Strings.isNullOrEmpty(pushBulletApiKey)) {
            		addPushBulletMessageToSendQueue(pushBulletApiKey, subject, message);
            	}
            }

            addEmailToSendQueue(subject, message, recipients);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not send email", e);
        }
    }


	public static void sendAdminMessage(String subject, String body) {
		List<String> recipients = Arrays.asList("squix78@gmail.com");
		addEmailToSendQueue(subject, body, recipients);
    }
    

    
    public static void callWebHooksForEndOfGame(Long gameId) {
		QueueFactory.getDefaultQueue().add(
				TaskOptions.Builder.withMethod(Method.POST).url("/rest/notifications/endOfGame/" + gameId));
    }

}
