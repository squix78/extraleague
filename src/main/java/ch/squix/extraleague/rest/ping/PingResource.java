package ch.squix.extraleague.rest.ping;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



public class PingResource extends ServerResource {
	
	@Get(value = "json")
	public PingDto execute() throws UnsupportedEncodingException {
		PingDto dto = new PingDto();
		dto.setStatus("OK");
	    return dto;
	}

}
