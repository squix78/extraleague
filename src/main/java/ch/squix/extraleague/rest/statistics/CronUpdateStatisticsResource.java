package ch.squix.extraleague.rest.statistics;

import java.io.UnsupportedEncodingException;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.league.LeagueDao;
import ch.squix.extraleague.model.statistics.StatisticsService;

import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;


public class CronUpdateStatisticsResource extends ServerResource {
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		LeagueDao.runCronOverNamespaces("/rest/updateStatistics");
		return "OK";
	}

}