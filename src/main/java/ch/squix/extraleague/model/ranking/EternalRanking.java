package ch.squix.extraleague.model.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Serialize;

@Entity
@Cache
@Data 
public class EternalRanking {
	
	@Id
	private Long id;
	
	private Date createdDate;
	
	@Serialize(zip=true)
	private List<PlayerRanking> playerRankings = new ArrayList<>();

	@Ignore
	private Map<String, PlayerRanking> playerRankingMap = new HashMap<>();

	
	public PlayerRanking getPlayerRanking(String player) {
	    if (playerRankingMap.size() != playerRankings.size()) {
	        for (PlayerRanking playerRanking : playerRankings) {
	            playerRankingMap.put(playerRanking.getPlayer(), playerRanking);
	        }
	    }
	    return playerRankingMap.get(player);
	}


}
