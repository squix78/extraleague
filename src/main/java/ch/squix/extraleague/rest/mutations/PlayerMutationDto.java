package ch.squix.extraleague.rest.mutations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import ch.squix.extraleague.rest.result.PlayerScoreDto;

@Data
public class PlayerMutationDto {

	private List<String> players;
	private String value;
	private List<String> descriptions = new ArrayList<>();
	private List<PlayerScoreDto> playerScores = new ArrayList<>();
	
	private List<BadgeMutationDto> wonBadges = new ArrayList<>();
	private List<BadgeMutationDto> lostBadges = new ArrayList<>();
	
	private Date createdDate;
 

}
