package ch.squix.extraleague.model.league;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

public class LeagueDao {
	
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
			NamespaceManager.set(league.getDomain());
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withMethod(Method.GET).url(taskUrl));
		}
		} finally {
			NamespaceManager.set(oldNamespace);
		}
	}

}
