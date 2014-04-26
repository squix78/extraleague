package ch.squix.extraleague.rest.result;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SummaryDto {
	
	private List<PlayerScoreDto> playerScores = new ArrayList<>();
	private List<MatchSummaryDto> matches = new ArrayList<>();

}
