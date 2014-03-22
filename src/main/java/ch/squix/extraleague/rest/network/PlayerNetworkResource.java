package ch.squix.extraleague.rest.network;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.rest.ranking.RankingDto;
import ch.squix.extraleague.rest.ranking.RankingDtoMapper;



public class PlayerNetworkResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(PlayerNetworkDto.class.getName());
	
	@Get(value = "json")
	public Collection<PlayerNetworkDto> execute() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -30);
		Map<String, List<String>> networkMap = new HashMap<>();
		List<Game> games = ofy().load().type(Game.class).list();
		for (Game game : games) {
			for (String player : game.getPlayers()) {
				List<String> partnerList = networkMap.get(player);
				if (partnerList == null) {
					partnerList = new ArrayList<>();
					networkMap.put(player, partnerList);
				}
				Set<String> partners = new HashSet<>(game.getPlayers());
				partners.remove(player);
				partnerList.addAll(partners);
			}
		}
		List<PlayerNetworkDto> playerList = new ArrayList<>();
		Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
		
		for (Map.Entry<String, List<String>> entry : networkMap.entrySet()) {
			RankingDto rankingDto = RankingDtoMapper.getPlayerRanking(entry.getKey(), ranking);
			PlayerNetworkDto dto = new PlayerNetworkDto();
			dto.setName(entry.getKey());
			if (rankingDto != null) {
				dto.setRanking(rankingDto.getRanking());
			}
			Set<String> allPartners = new HashSet<>(entry.getValue());
			for (String partner : allPartners) {
				PartnerDto partnerDto = new PartnerDto();
				partnerDto.setPartner(partner);
				partnerDto.setFrequency(Collections.frequency(entry.getValue(), partner));
				dto.getPartners().add(partnerDto);
			}
			playerList.add(dto);
		}
		return playerList;
	}


}
