package ch.squix.extraleague.model.league;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class LeagueDao {
	
	private static final Logger log = Logger.getLogger(LeagueDao.class.getName());
	
	public static List<League> loadLeaguesFromDefaultNamespace() {
		String oldNamespace = NamespaceManager.get();
		try {
			NamespaceManager.set("");
			List<League> leagues = ofy().load().type(League.class).list();
			League league = new League();
			league.setName("NCA League");
			league.setDomain("");
			leagues.add(league);
			return leagues;
		} finally {
		  NamespaceManager.set(oldNamespace);
		}
	}
	
	public static void runCronOverNamespaces(String taskUrl) {
		List<League> leagues = LeagueDao.loadLeaguesFromDefaultNamespace();
		String oldNamespace = NamespaceManager.get();
		try {
			for (League league : leagues) {
				log.info("Creating task for namespace " + league.getDomain() + " with url " + taskUrl);
				NamespaceManager.set(league.getDomain());
				QueueFactory.getDefaultQueue().add(TaskOptions.Builder.withMethod(Method.GET).url(taskUrl));
				
			}
		} finally {
			NamespaceManager.set(oldNamespace);
		}
	}

	public static League getCurrentLeague() {
		String namespace = NamespaceManager.get();
		log.info("Loading leage for namespace: " + namespace);
		return ofy().load().type(League.class).filter("domain = ", namespace).first().now();
	}

}
