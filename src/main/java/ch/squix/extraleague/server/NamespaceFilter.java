// Copyright 2010 Google Inc. All Rights Reserved.

package ch.squix.extraleague.server;

import com.google.appengine.api.NamespaceManager;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * An example App Engine namespace setting filter.  
 * 
 * <p>This namespace filter provides for a number of strategies
 * as an example but is also careful not to override the
 * namespace where it has previously been set, for example, incoming 
 * task queue requests.
 */
public class NamespaceFilter implements Filter {


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
	if (namespace != null && namespace.contains("ncaleague") || namespace.contains("localhost")) {
		namespace = "";
	}
    NamespaceManager.set(namespace);

    
    // chain into the next request
    chain.doFilter(request, response) ;
  }
}