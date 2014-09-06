package ch.squix.extraleague.rest.ping;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.utils.SystemProperty;



public class VersionResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		return SystemProperty.Environment.applicationVersion.get().replaceAll("(\\d*)\\.(\\d*)$", "$1");
	}

}
