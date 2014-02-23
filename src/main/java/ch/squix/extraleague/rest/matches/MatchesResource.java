package ch.squix.extraleague.rest.matches;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.game.Game;
import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.RankingService;
import ch.squix.extraleague.rest.games.GamesResource;



public class MatchesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public List<MatchDto> execute() throws UnsupportedEncodingException {
		String gameId = (String) this.getRequestAttributes().get("gameId");
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", Long.valueOf(gameId)).list();
		sortMatches(matches);
		Collections.sort(matches, new Comparator<Match>() {
		    
		    @Override
		    public int compare(Match o1, Match o2) {
		        return o1.getMatchIndex().compareTo(o2.getMatchIndex());
		    }
		});
		log.info("Listing table for " + gameId + ". Found " + matches.size() + " matches for this game");
		List<MatchDto> matchDtos = new ArrayList<>();
		for (Match match : matches) {
			MatchDto dto = MatchDtoMapper.mapToDto(match);
			matchDtos.add(dto);
		}
		return matchDtos;
	}
	
	@Post(value = "json")
	public MatchDto update(MatchDto dto) {
		log.info("Received game to save");
		Match match = ofy().load().type(Match.class).id(dto.getId()).now();
		if (match == null) {
			match = new Match();
		}
		match.setGameId(dto.getGameId());
		match.setStartDate(dto.getStartDate());
		match.setTeamA(dto.getTeamA());
		match.setTeamB(dto.getTeamB());
		match.setTeamAScore(dto.getTeamAScore());
		match.setTeamBScore(dto.getTeamBScore());
		match.setTable(dto.getTable());
		if (match.getTeamAScore() >= 5 || match.getTeamBScore() >= 5) {
			log.info("Game is finished");
			match.setEndDate(new Date());
		}
		ofy().save().entity(match).now();
		
		// Update game
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", dto.getGameId()).list();
		sortMatches(matches);
		Integer numberOfCompletedMatches = 0;
		for (Match candiateMatch : matches) {
			if (candiateMatch.getTeamAScore() >= 5 || candiateMatch.getTeamBScore() >= 5) {
				numberOfCompletedMatches++;
			}
		}
		Game game = ofy().load().type(Game.class).id(dto.getGameId()).now();
		game.setNumberOfCompletedMatches(numberOfCompletedMatches);
		if (numberOfCompletedMatches >=4) {
			log.info("4 Games reached. Setting game endDate");
			game.setEndDate(new Date());
			RankingService.calculateRankings();
		}
		ofy().save().entity(game).now();
		dto.setId(match.getId());
		return dto;
	}

	private void sortMatches(List<Match> matches) {
		Collections.sort(matches, new Comparator<Match>() {

			@Override
			public int compare(Match o1, Match o2) {
				return o1.getMatchIndex().compareTo(o2.getMatchIndex());
			}
		});
	}

}
