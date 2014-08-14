package ch.squix.extraleague.rest.migration;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

import ch.squix.extraleague.rest.games.GamesResource;
import ch.squix.extraleague.rest.games.mode.GameModeEnum;
import ch.squix.extraleague.rest.mode.GameModeDto;



public class NamespaceMigrationResource extends ServerResource {
	
	private static final Logger log = Logger.getLogger(GamesResource.class.getName());
	
	@Get(value = "json")
	public String execute() throws UnsupportedEncodingException {
		QueueFactory.getDefaultQueue().add(TaskOptions.Builder
				.withMethod(Method.GET)
				.url("/rest/admin/migration/namespace/task"));
		
		return "OK";
	}
}