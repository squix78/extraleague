package ch.squix.extraleague.rest.notification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.League;
import ch.squix.extraleague.model.league.LeagueDao;



public class WebhookEndOfGameNotificationResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(WebhookEndOfGameNotificationResource.class.getName());
	
	@Post(value = "json")
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		String gameId = (String) this.getRequestAttributes().get("gameId");
		log.info("Notifying end of game for id: " + gameId);
		League league = LeagueDao.getCurrentLeague();
		if (league != null && league.getWebhookUrl() != null) {
		    callWebhook(league.getWebhookUrl(), league.getRequestHeaders(), gameId);
		} else {
		    log.info("No matching league found");
		    return "No league found";
		}
		return "OK";
	}
	
	private static void callWebhook(String webhook, Map<String, String> requestHeaders, String gameId) {
	       try {
	            URL url = new URL(webhook + gameId);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setDoOutput(true);
	            connection.setRequestMethod("POST");
	            
	            if (requestHeaders != null) {
        	            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
        		            connection.setRequestProperty(entry.getKey(), entry.getValue());
        		            log.info("Adding header field: " + entry.getKey() + ": " + entry.getValue());
        	            }
	            }
	    
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
	

}
