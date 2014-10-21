package ch.squix.extraleague.rest.notification;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.client.BrowserClient;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;



public class NotificationTokenResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(NotificationTokenResource.class.getName());
	
	@Get(value = "json")
	public NotificationTokenDto execute() throws UnsupportedEncodingException {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		Random random = new Random();
		String clientId = String.valueOf(random.nextLong());
		String token = channelService.createChannel(clientId);
		BrowserClient client = new BrowserClient();
		client.setClientId(clientId);
		client.setToken(token);
		ofy().save().entities(client);
		NotificationTokenDto dto = new NotificationTokenDto();
		dto.setToken(token);
		return dto;
	}
	

}
