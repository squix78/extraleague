package ch.squix.extraleague.rest.matches.export;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Goal;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.rest.games.GamesResource;
import ch.squix.extraleague.rest.matches.GoalDto;



public class MatchExportResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public List<MatchExportDto> execute() throws UnsupportedEncodingException {

		List<Match> matches = ofy().load().type(Match.class).list();
		log.info("Loaded number of matches: " + matches.size());
		List<MatchExportDto> dtos = new ArrayList<>();
		for (Match match : matches) {
			MatchExportDto dto = new MatchExportDto();
			dto.setGameId(match.getGameId());
			dto.setTeamAOffense(match.getTeamA()[0]);
			dto.setTeamADefense(match.getTeamA()[1]);
			dto.setTeamBOffense(match.getTeamB()[0]);
			dto.setTeamBDefense(match.getTeamB()[1]);

			dto.setTeamAScore(match.getTeamAScore());
			dto.setTeamBScore(match.getTeamBScore());
			dto.setScorers(match.getScorers());
			dto.setStartDate(match.getStartDate());
			dto.setEndDate(match.getEndDate());
			dto.setMatchIndex(match.getMatchIndex());
			dto.setWinProbabilityTeamA(match.getWinProbabilityTeamA());
			dto.setWinPointsTeamA(match.getWinPointsTeamA());
			dto.setWinPointsTeamB(match.getWinPointsTeamB());
			dto.setMaxGoals(match.getMaxGoals());
			dto.setMaxMatches(match.getMaxMatches());
			dto.setPositionSwappingAllowed(match.getPositionSwappingAllowed());
			dto.setVersion(match.getVersion());

			for (Goal goal : match.getGoals()) {
				GoalDto goalDto = new GoalDto();
				goalDto.setScorer(goal.getScorer());
				goalDto.setTime(goal.getTime());
				dto.getGoals().add(goalDto);
			}
			dtos.add(dto);

		}
		return dtos; 

	}


}
