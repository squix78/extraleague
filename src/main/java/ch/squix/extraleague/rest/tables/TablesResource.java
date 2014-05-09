package ch.squix.extraleague.rest.tables;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;



public class TablesResource extends ServerResource {
	
	@Get(value = "json")
	public TablesDto[] execute() throws UnsupportedEncodingException {
		return new TablesDto[] {
				new TablesDto("Park"), 
				new TablesDto("Albis"), 
				new TablesDto("Bern"), 
				new TablesDto("Skopje"), 
				new TablesDto("Flurstrasse")};
	}

}
