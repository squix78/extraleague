package ch.squix.extraleague.rest.ranking;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import ch.squix.extraleague.model.match.player.PlayerUser;
import ch.squix.extraleague.model.ranking.PlayerRanking;
import ch.squix.extraleague.model.ranking.Ranking;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class PersonalStatsResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(PersonalStatsResource.class.getName());

	
	@Get(value = "json")
	public PersonalStatsDto execute() throws UnsupportedEncodingException {
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		if (userService.isUserLoggedIn()) {
				
			String userId = currentUser.getUserId();
			PlayerUser player = ofy().load().type(PlayerUser.class).filter("appUserId", userId).first().now();

			if (player != null) {
				Ranking ranking = ofy().load().type(Ranking.class).order("-createdDate").first().now();
				if (ranking != null) {
					Optional<PlayerRanking> playerRanking = ranking.getPlayerRanking(player.getPlayer());
					if (playerRanking.isPresent()) {
						return PersonalStatsDtoMapper.mapToDto(playerRanking.get());
					}
				}
			}
		}
		return null;

	}




}
