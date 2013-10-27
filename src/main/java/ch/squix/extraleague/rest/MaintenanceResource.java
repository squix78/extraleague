package ch.squix.extraleague.rest;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



public class MaintenanceResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
	    //AddIndexTask.startQuery(null);
	    return "OK";
	}

}
