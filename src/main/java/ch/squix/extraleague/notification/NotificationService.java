package ch.squix.extraleague.notification;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;

import ch.squix.extraleague.model.client.BrowserClient;
import ch.squix.extraleague.rest.notification.NotificationTokenResource;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class NotificationService {
	
	private static final Logger log = Logger.getLogger(NotificationTokenResource.class.getName());
	
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

}
