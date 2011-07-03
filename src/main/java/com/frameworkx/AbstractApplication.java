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
package com.frameworkx;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * Base class for all Framework X Applications
 *
 * @author robert.hollencamp
 */
public abstract class AbstractApplication {

	private final Properties properties = new Properties();
	private String instance;
	private final Map<Pattern, RouteHandler> routes = new LinkedHashMap<Pattern, RouteHandler>();

	/**
	 * Initialize the framework
	 *
	 * This should not be called from client code
	 */
	public final void init() {
		this.loadDefaultProperties();
		this.loadAppProperties();

		this.instance = this.resolveInstance();
		if (this.instance == null || this.instance.isEmpty()) {
			throw new IllegalStateException("Instance can not be null or empty");
		}
		this.loadInstanceProperties();

		this.registerRoutes();
	}

	/**
	 * Read the default properties file
	 *
	 * @throws RuntimeException if default properties file can not be read
	 */
	private void loadDefaultProperties() {
		final InputStream is = AbstractApplication.class.getResourceAsStream("/com/frameworkx/framework-x.properties");
		try {
			if (is != null) {
				this.properties.load(is);
			} else {
				throw new RuntimeException("Unable to load default properties");
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Load Application properties
	 *
	 * @throws RuntimeException if application properties file can not be read
	 */
	private void loadAppProperties() {
		final InputStream is = this.getClass().getResourceAsStream("/framework-x.properties");
		try {
			if (is != null) {
				this.properties.load(is);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Load Application Instance properties
	 *
	 * @throws RuntimeException if an IOException occurs while reading instance properties file
	 */
	private void loadInstanceProperties() {
		final InputStream is = this.getClass().getResourceAsStream("/instance-" + this.instance + ".properties");
		try {
			if (is != null) {
				this.properties.load(is);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Register a route
	 *
	 * @param p
	 * @param rh
	 */
	protected void registerRoute(final Pattern p, final RouteHandler rh) {
		if (p == null) {
			throw new IllegalArgumentException("Pattern can not be null");
		}
		if (rh == null) {
			throw new IllegalArgumentException("Route Handler can not be null");
		}

		this.routes.put(p, rh);
	}

	/**
	 * Iterate through the routing table and find a matching entry. If a matching route is not found,
	 * a 404 is generated
	 *
	 * @param request
	 * @param response
	 */
	public void execute(final HttpServletRequest request, final HttpServletResponse response) {
		final String path = request.getRequestURI().substring(request.getContextPath().length());

		try {
			for (Map.Entry<Pattern, RouteHandler> kvp : this.routes.entrySet()) {
				Matcher m = kvp.getKey().matcher(path);
				if (m.matches()) {
					kvp.getValue().execute(request, response, m);
					return;
				}
			}
		} catch (Exception ex) {
			// @todo 500
			throw new RuntimeException(ex);
		}

		// @todo 404
	}

	/**
	 * Determine what instance we are running in. Instance is used to determine what config file to
	 * read (instance-{instance}.properties), and can also be referenced in client code.
	 *
	 * Common instance names are "dev", "qa", "stage", "live"
	 *
	 * Returned string can not be null or empty
	 *
	 * @return
	 */
	protected abstract String resolveInstance();

	/**
	 * Fill out the route table
	 */
	protected abstract void registerRoutes();
}
