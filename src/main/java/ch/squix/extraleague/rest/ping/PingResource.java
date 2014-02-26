package ch.squix.extraleague.rest.ping;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

import ch.squix.extraleague.model.client.BrowserClient;



public class PingResource extends ServerResource {
	
	@Get(value = "json")
	public PingDto execute() throws UnsupportedEncodingException {
		PingDto dto = new PingDto();
		dto.setStatus("OK");

	    return dto;
	}

}
