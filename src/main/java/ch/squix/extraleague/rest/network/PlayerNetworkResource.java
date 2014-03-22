package ch.squix.extraleague.rest.network;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;



public class PlayerNetworkResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(PlayerNetworkDto.class.getName());
	
	@Get(value = "json")
	public Collection<PlayerNetworkDto> execute() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		Map<String, PlayerNetworkDto> networkMap = new HashMap<>();
		List<Game> games = ofy().load().type(Game.class).list();
		for (Game game : games) {
			for (String player : game.getPlayers()) {
				PlayerNetworkDto dto = networkMap.get(player);
				if (dto == null) {
					dto = new PlayerNetworkDto();
					dto.setName(player);
					networkMap.put(player, dto);
				}
				Set<String> partners = new HashSet<>(game.getPlayers());
				partners.remove(player);
				dto.getPartners().addAll(partners);
			}
		}
		return networkMap.values();
	}


}
