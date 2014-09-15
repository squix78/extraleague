package ch.squix.extraleague.rest.ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import ch.squix.extraleague.model.match.Match;
import ch.squix.extraleague.model.ranking.EternalRanking;
import ch.squix.extraleague.model.ranking.Ranking;
import ch.squix.extraleague.model.ranking.RankingService;

import com.google.appengine.api.NamespaceManager;
import com.google.common.base.Strings;

public class RankingResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(RankingResource.class.getName());

	
	@Get(value = "json")
	public List<RankingDto> execute() throws UnsupportedEncodingException {
		String rankingType = getQuery().getValues("type");
		if (Strings.isNullOrEmpty(rankingType) || "All".equals(rankingType)) {
			log.info("Requesting ranking for namespace: " + NamespaceManager.get());
			Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
			if (ranking == null) {
				return new ArrayList<>();
			}
			
			return RankingDtoMapper.convertToDto(ranking);
		} else if ("Eternal".equals(rankingType)) {
			EternalRanking eternalRanking = ofy().load().type(EternalRanking.class).first().now();
			if (eternalRanking == null) {
				return new ArrayList<>();
			}
			return RankingDtoMapper.convertToDto(eternalRanking);
		} else if ("tag".equals(rankingType)) {
			String tag = getQuery().getValues("tag");
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -30);
			List<Match> matches = ofy().load().type(Match.class).filter("tags =", tag).filter("startDate > ", calendar.getTime()).list();
			Ranking ranking = RankingService.calculateRankingFromMatches(matches);
			if (ranking == null) { 
				return new ArrayList<>();
			}
			return RankingDtoMapper.convertToDto(ranking);
		}
		return new ArrayList<>(); 
	}




}