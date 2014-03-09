package ch.squix.extraleague.rest.statistics;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.statistics.StatisticsService;


public class UpdateStatisticsResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		StatisticsService.updateStatistics();

		return "OK";
	}

}