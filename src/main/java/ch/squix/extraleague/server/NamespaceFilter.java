// Copyright 2010 Google Inc. All Rights Reserved.

package ch.squix.extraleague.server;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import ch.squix.extraleague.model.league.League;

import com.google.appengine.api.NamespaceManager;
import com.googlecode.objectify.ObjectifyService;

public class NamespaceFilter implements Filter {

	private static final Logger log = Logger.getLogger(NamespaceFilter.class.getName());

	private FilterConfig filterConfig;
	private Map<String, League> registeredLeagues = new HashMap<>();

	static {
		ObjectifyService.register(League.class);
	}

	/*
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		filterConfig = config;
		initializeLeagueMap();
	}

	private void initializeLeagueMap() {
		List<League> leagues = ofy().load().type(League.class).list();
		registeredLeagues.clear();
		for (League league : leagues) {
			registeredLeagues.put(league.getDomain(), league);
		}
	}
	
	private boolean isLeagueRegistered(String domain) {
		if (registeredLeagues.get(domain) == null) {
			log.info("Domain " + domain + " unknown to this instance reloading the leagues");
			initializeLeagueMap();
		}
		return registeredLeagues.get(domain) != null;
	}

	/*
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		this.filterConfig = null;
	}
	
	private boolean isDefaultNamespace(String namespace) {
		String [] defaultDomains = {"ncaleague", "127.0.0.1", "localhost"};
		for (String defaultDomain : defaultDomains) {
			if (namespace != null & namespace.contains(defaultDomain)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		String namespace = request.getServerName();
		log.info("Server name: " + namespace);
		if (isDefaultNamespace(namespace)) {
			log.info("NCALEAGEUE domain, setting to default namespace");
			namespace = "";
		} else {
			if (!isLeagueRegistered(namespace)) {
				log.info("This domain is not a registered league: " + namespace);
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}
		NamespaceManager.set(namespace);

		// chain into the next request
		chain.doFilter(request, response);
	}
}