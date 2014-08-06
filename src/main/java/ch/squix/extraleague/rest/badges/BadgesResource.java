package ch.squix.extraleague.rest.badges;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.badge.BadgeEnum;
import ch.squix.extraleague.model.statistics.Statistics;
import ch.squix.extraleague.rest.games.OpenGamesResource;



public class BadgesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public Map<String, BadgeEnumDto> execute() {
		Statistics statistics = ofy().load().type(Statistics.class).first().now();
		Map<String, Integer> badgeHistogram = new HashMap<>();
		if (statistics != null) {
			badgeHistogram = statistics.getBadgeHistogram();
		}
		Map<String, BadgeEnumDto> badgeMap = new LinkedHashMap<>();
		for (BadgeEnum badge : BadgeEnum.values()) {
			BadgeEnumDto dto = new BadgeEnumDto();
			dto.setName(badge.name());
			dto.setBadgeType(badge.getBadgeType().name());
			dto.setFaClass(badge.getFaClass());
			dto.setIndex(badge.getIndex());
			dto.setDescription(badge.getDescription());
			dto.setBadgeCount(badgeHistogram.get(badge.name()));
			dto.setJsRegex(badge.getJsRegex());
			dto.setAchievementPoints(badge.getAchievementPoints());
			badgeMap.put(badge.name(), dto);
		}
		return badgeMap;
	}



}
