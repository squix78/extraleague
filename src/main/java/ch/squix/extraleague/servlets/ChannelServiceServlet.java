package ch.squix.extraleague.servlets;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.squix.extraleague.model.client.BrowserClient;

import com.google.appengine.api.channel.ChannelPresence;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

public class ChannelServiceServlet extends HttpServlet {
	
	static {
		ObjectifyService.register(BrowserClient.class);
	}

	private static final Logger log = Logger.getLogger(ChannelServiceServlet.class.getName());
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		ChannelPresence presence = channelService.parsePresence(req);
		log.info("Client: " + presence.clientId());
		if (!presence.isConnected()) {
		    List<BrowserClient> clients = ofy().load().type(BrowserClient.class).filter("clientId", presence.clientId()).list();
		    log.info("Deleting " + clients.size() + " browser clients");
		    ofy().delete().entities(clients).now();
		}
	}

}
