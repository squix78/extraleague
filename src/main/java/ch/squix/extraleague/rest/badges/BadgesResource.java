package ch.squix.extraleague.rest.badges;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.ranking.badge.BadgeEnum;
import ch.squix.extraleague.rest.games.OpenGamesResource;



public class BadgesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(OpenGamesResource.class.getName());
	
	@Get(value = "json")
	public Map<String, BadgeDto> execute() {
		Map<String, BadgeDto> badgeMap = new LinkedHashMap<>();
		for (BadgeEnum badge : BadgeEnum.values()) {
			BadgeDto dto = new BadgeDto();
			dto.setName(badge.name());
			dto.setBadgeType(badge.getBadgeType().name());
			dto.setFaClass(badge.getFaClass());
			dto.setIndex(badge.getIndex());
			dto.setDescription(badge.getDescription());
			badgeMap.put(badge.name(), dto);
		}
		return badgeMap;
	}



}
