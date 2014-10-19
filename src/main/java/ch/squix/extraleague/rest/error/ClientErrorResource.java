package ch.squix.extraleague.rest.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class ClientErrorResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(ClientErrorResource.class.getName());
	
	
	@Post(value = "json")
	public static void logClientError(ClientErrorDto dto) {
		log.log(Level.SEVERE, 
				"Url: " + dto.getUrl() 
				+ ", message: " + dto.getMessage() 
				+ ", cause: " + dto.getCause() 
				+ ", userAgent: " + dto.getUserAgent());
	}

}
