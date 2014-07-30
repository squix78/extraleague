package ch.squix.extraleague.rest.statistics;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.statistics.StatisticsService;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;


public class UpdateStatisticsResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		StatisticsService.updateStatistics();

		Environment env = ApiProxy.getCurrentEnvironment();
		String hostName = (String) env.getAttributes().get("com.google.appengine.runtime.default_version_hostname");
		return "OK " + hostName;
	}

}