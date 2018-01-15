package ch.squix.extraleague.model.ranking;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Serialize;

import lombok.Data;

@Entity
@Cache
@Data 
public class Ranking {
	
	@Id
	private Long id;
	
	@Index
	private Date createdDate;
	
	@Serialize(zip=true)
	private List<PlayerRanking> playerRankings = new ArrayList<>();
	
	@Ignore
	private Map<String, PlayerRanking> playerRankingMap = new HashMap<>();

	
	public Optional<PlayerRanking> getPlayerRanking(String player) {
	    if (playerRankingMap.size() != playerRankings.size()) {
	        for (PlayerRanking playerRanking : playerRankings) {
	            playerRankingMap.put(playerRanking.getPlayer(), playerRanking);
	        }
	    }
	    return Optional.ofNullable(playerRankingMap.get(player));
	}


}
