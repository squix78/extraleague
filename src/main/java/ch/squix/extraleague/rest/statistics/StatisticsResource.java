package ch.squix.extraleague.rest.statistics;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.statistics.Statistics;


public class StatisticsResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(StatisticsResource.class.getName());
	
	@Get(value = "json")
	public StatisticsDto execute() {
		
		Statistics statistics = ofy().load().type(Statistics.class).first().now();
		if (statistics != null) {
			log.info("Found statistics object");
			return StatisticsDtoMapper.mapToDto(statistics);
		}
		log.info("Found no statistics object");
		return null;
	}

}