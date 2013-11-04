package ch.squix.extraleague.rest.player;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.rest.ping.PingDto;



public class PlayerResource extends ServerResource {
	
	@Get(value = "json")
	public PingDto execute() throws UnsupportedEncodingException {
		PingDto dto = new PingDto();
		dto.setStatus("OK");
	    return dto;
	}

}
