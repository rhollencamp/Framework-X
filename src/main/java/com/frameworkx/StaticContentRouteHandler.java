/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frameworkx;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serve static content like CSS and Javascript files directly from the servlet container
 * 
 * @author rhollencamp
 */
public class StaticContentRouteHandler implements RouteHandler {

	private final String mf;

	/**
	 * Serve static content based on the entire string matched by the route pattern
	 */
	public StaticContentRouteHandler() {
		this.mf = null;
	}

	/**
	 * Serve static content located at pattern. Format Elements are replaced by groups from the matcher,
	 * with {0} = matcher.group(0), {1} = matcher.group(1) etc
	 *
	 * @param pattern MessageFormat pattern
	 */
	public StaticContentRouteHandler(final String pattern) {
		this.mf = pattern;
	}

	/**
	 * Resolve resource path and forward
	 *
	 * @param request
	 * @param response
	 * @param m
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response, Matcher m) throws ServletException, IOException {
		String path;

		if (this.mf != null) {
			Object[] args = new Object[m.groupCount() + 1];

			for (int i = 0; i < m.groupCount(); i++) {
				args[i] = m.group(i);
			}

			path = MessageFormat.format(this.mf, args);
		} else {
			path = m.group(0);
		}

		request.getRequestDispatcher(path).forward(request, response);
	}
}
