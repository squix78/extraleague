// Copyright 2010 Google Inc. All Rights Reserved.

package ch.squix.extraleague.server;

import ch.squix.extraleague.model.ranking.tasks.ManualBadgeTask;

import com.google.appengine.api.NamespaceManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class NamespaceFilter implements Filter {

  private static final Logger log = Logger.getLogger(NamespaceFilter.class.getName());
	
  private FilterConfig filterConfig;

/* @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override
  public void init(FilterConfig config) throws ServletException {
	  filterConfig = config;
  }
  
  /* @see javax.servlet.Filter#destroy()
   */
  @Override
  public void destroy() {
    this.filterConfig = null;
  }

  /* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, 
   *     javax.servlet.ServletResponse, javax.servlet.FilterChain)
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
	String namespace = request.getServerName();
	log.info("Server name" + namespace);
	if (namespace != null && namespace.contains("ncaleague")) {
		log.info("NCALEAGEUE domain, setting to default namespace");
		namespace = "";
	}
    NamespaceManager.set(namespace);

    
    // chain into the next request
    chain.doFilter(request, response) ;
  }
}