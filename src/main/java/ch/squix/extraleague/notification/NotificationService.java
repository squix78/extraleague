package ch.squix.extraleague.notification;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.codehaus.jackson.map.ObjectMapper;

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
import com.google.common.base.Joiner;

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
		emailBody.append("Player\tWon\tGoals\tElo\n");
		for (PlayerScoreDto playerScore : summaryDto.getPlayerScores()) {
			emailBody.append(playerScore.getPlayer());
			emailBody.append("\t");
			emailBody.append(playerScore.getScore());
			emailBody.append("\t");
			emailBody.append(playerScore.getGoals());
			emailBody.append("\t");
			emailBody.append(playerScore.getEarnedEloPoints());
			emailBody.append("\n");
		}
		List<PlayerUser> players = ofy().load().type(PlayerUser.class).filter("player in", game.getPlayers()).list();
		List<String> recipients = new ArrayList<>();
		recipients.add("dani.eichhorn@squix.ch");
		for (PlayerUser player : players) {
			Boolean isEmailNotificationEnabled = player.getEmailNotification();
			String recipient = player.getEmail();
			if (isEmailNotificationEnabled != null && isEmailNotificationEnabled && recipient != null) {
				recipients.add(recipient);
			}
		}
		
		sendEmail("Summary Game with " + JOINER.join(game.getPlayers()), emailBody.toString(), recipients);
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
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("daniel.eichhorn@netcetera.com", "NCA League Admin"));
		    for (String recipient : recipients) {
			    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
		    }
		    msg.setSubject(subject);
		    msg.setText(msgBody);
		    Transport.send(msg);

		} catch (Exception e) {
		    log.log(Level.SEVERE, "Could not send email", e);
		} 
	}

}
