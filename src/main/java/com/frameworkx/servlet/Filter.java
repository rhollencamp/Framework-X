/**
 * Framework X - Java Web Application Framework
 * Copyright (C) 2011 Robert Hollencamp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frameworkx.servlet;

import com.frameworkx.AbstractApplication;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter that intercepts all requests and handles them within the framework
 *
 * @author robert.hollencamp
 */
public class Filter implements javax.servlet.Filter {

	private FilterConfig config;
	private AbstractApplication app;

	/**
	 * Initialize the framework when started in servlet environment
	 *
	 * @param config
	 * @throws ServletException
	 */
	public void init(final FilterConfig config) throws ServletException {
		this.config = config;
		this.app = (AbstractApplication) this.config.getServletContext().getAttribute("com.frameworkx.application");
		this.app.init();
	}

	/**
	 * Called every request; dispatches requests into the framework
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		this.app.execute((HttpServletRequest) request, (HttpServletResponse) response);
		return;
	}

	public void destroy() {
	}
}
