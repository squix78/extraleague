package ch.squix.extraleague.model.mutations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.result.PlayerScoreDto;

import com.google.common.base.Joiner;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Cache
@Data
public class PlayerMutation {
	
	@Id
	private Long id;
	
	private List<String> players = new ArrayList<>();

	private List<String> descriptions = new ArrayList<>();

	private List<PlayerScoreDto> playerScores = new ArrayList<>();
	
	@Index
	private Date createdDate;
	
	public PlayerMutation() {
		this.createdDate = new Date();		
	}
	
	public PlayerMutation(String... players) {
		this.players = Arrays.asList(players);
		this.createdDate = new Date();
	}
	
	public PlayerMutation(List<String> players) {
		this.players = players;
		this.createdDate = new Date();
	}

	@Override
	public String toString() {
		Joiner joiner = Joiner.on("/").skipNulls();
		return joiner.join(players) + "/" + joiner.join(descriptions);
	}
	
	public String getPlayersKey() {
		Joiner joiner = Joiner.on(",").skipNulls();
		List<String> sortedPlayers = new ArrayList<>(players);
		Collections.sort(players);
		return joiner.join(sortedPlayers);
		
	}


	
	
}
