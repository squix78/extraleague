package ch.squix.extraleague.rest.matches;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
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
import ch.squix.extraleague.notification.NotificationService;
import ch.squix.extraleague.notification.UpdateMatchMessage;
import ch.squix.extraleague.notification.UpdateOpenGamesMessage;
import ch.squix.extraleague.rest.games.GameDto;
import ch.squix.extraleague.rest.games.GameDtoMapper;
import ch.squix.extraleague.rest.games.GamesResource;
import ch.squix.extraleague.rest.games.OpenGameService;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;



public class MatchesResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public List<MatchDto> execute() throws UnsupportedEncodingException {
		String gameId = (String) this.getRequestAttributes().get("gameId");
		List<MatchDto> matches = MatchDtoMapper.mapToDtoList(ofy().load().type(Match.class).filter("gameId = ", Long.valueOf(gameId)).list());
		sortMatches(matches);

		log.info("Listing table for " + gameId + ". Found " + matches.size() + " matches for this game");
		return matches;
	}
	
	@Post(value = "json")
	public MatchDto update(MatchDto dto) {
		log.info("Received game to save");
		Match match = ofy().load().type(Match.class).id(dto.getId()).now();
		if (match == null) {
			match = new Match();
		}
		match.setGameId(dto.getGameId());
		if (match.getStartDate() == null) {
			match.setStartDate(dto.getStartDate());
		}
		match.setTeamA(dto.getTeamA());
		match.setTeamB(dto.getTeamB());
		match.setTeamAScore(dto.getTeamAScore());
		match.setTeamBScore(dto.getTeamBScore());
		match.setScorers(dto.getScorers());
		if (match.getTeamAScore() >= 5 || match.getTeamBScore() >= 5) {
			log.info("Game is finished");
			match.setEndDate(new Date());
		}
		ofy().save().entity(match).now();
		
		// Update game
		List<Match> matches = ofy().load().type(Match.class).filter("gameId = ", dto.getGameId()).list();
		List<MatchDto> matchDtos = MatchDtoMapper.mapToDtoList(matches);
		sortMatches(matchDtos);
		Integer numberOfCompletedMatches = 0;
		Integer sumOfMaxGoals = 0;
		for (MatchDto candiateMatch : matchDtos) {
			Integer maxGoalsPerMatch = Math.max(candiateMatch.getTeamAScore(), candiateMatch.getTeamBScore());
			sumOfMaxGoals += maxGoalsPerMatch;
			if (maxGoalsPerMatch >= 5) {
				numberOfCompletedMatches++;
			}
		}
		Game game = ofy().load().type(Game.class).id(dto.getGameId()).now();
		game.setNumberOfCompletedMatches(numberOfCompletedMatches);
		game.setGameProgress(sumOfMaxGoals / 20d);
		// Set the date for the first goal
		if (sumOfMaxGoals > 0 && game.getFirstGoalDate() == null) {
			game.setFirstGoalDate(new Date());
		}
		if (numberOfCompletedMatches >=4) {
			log.info("4 Games reached. Setting game endDate");
			game.setEndDate(new Date());
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withMethod(Method.GET).url("/rest/updateRankings"));
			NotificationService.sendMessage(new UpdateOpenGamesMessage(OpenGameService.getOpenGames()));
			NotificationService.sendSummaryEmail(game, matches);
			
		} else {
			GameDto gameDto = GameDtoMapper.mapToDto(game);
			NotificationService.sendMessage(new UpdateMatchMessage(gameDto, dto));
		}
		ofy().save().entity(game).now();
		dto.setId(match.getId());
		return dto;
	}

	private void sortMatches(List<MatchDto> matches) {
		Collections.sort(matches, new Comparator<MatchDto>() {

			@Override
			public int compare(MatchDto o1, MatchDto o2) {
				return o1.getMatchIndex().compareTo(o2.getMatchIndex());
			}
		});
	}

}
