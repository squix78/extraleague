package ch.squix.extraleague.rest.error;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.appengine.labs.repackaged.com.google.common.base.Joiner;


public class ClientErrorResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(ClientErrorResource.class.getName());
	
	
	@Post(value = "json")
	public void logClientError(ClientErrorDto dto) {
		log.log(Level.SEVERE, 
				"Url: " + dto.getUrl() 
				+ "\n   message: " + dto.getMessage() 
				+ "\n   cause: " + dto.getCause() 
				+ "\n   userAgent: " + dto.getUserAgent()
				+ "\n   Country: " + getRequestHeader("X-AppEngine-Country")
				+ "\n   Region: " + getRequestHeader("X-AppEngine-Region")
				+ "\n   City: " + getRequestHeader("X-AppEngine-City")
				+ "\n   Location: " + getRequestHeader("X-AppEngine-CityLatLong")
				+ "\n   stackTrace: " + Joiner.on("\n   ").join(dto.getStackTrace()));
	}
	
	private  String getRequestHeader(String headerName) {
		Series headers = (Series) this.getRequest().getAttributes().get("org.restlet.http.headers");
		return headers.getFirstValue("Location");
	}

}
