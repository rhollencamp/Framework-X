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
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * Simple plugin that adds an X-Powered-By header to responses
 *
 * @author Robert Hollencamp
 */
public class PoweredByPlugin implements Plugin
{
	private String headerValue = "Framework-X";

	/**
	 * When the plugin is initialized, find the Framework-X version by reading the pom.properties
	 * file Maven generates
	 *
	 * If anything goes wrong while trying to determine the version, do not display a version
	 *
	 * @param name
	 * @param app
	 */
	public void init(final String name, final AbstractApplication app)
	{
		InputStream is = PoweredByPlugin.class.getResourceAsStream("/META-INF/maven/com.framework-x/framework-x/pom.properties");
		try {
			if (is != null) {
				Properties p = new Properties();
				p.load(is);
				String version = p.getProperty("version");
				if (version != null) {
					this.headerValue = "Framework-X/" + version;
				}
			}
		} catch (IOException ex) {
			// ignore
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * When the request is initially received, add the header
	 *
	 * @param request
	 * @param response
	 */
	public void onRequestReceived(final HttpServletRequest request, final HttpServletResponse response)
	{
		response.addHeader("X-Powered-By", this.headerValue);
	}

	/**
	 * Nothing to do here
	 *
	 * @param request
	 * @param response
	 */
	public void onRequestFinally(HttpServletRequest request, HttpServletResponse response)
	{
	}

	/**
	 * Nothing to do here
	 *
	 * @param request
	 * @param response
	 */
	public void onUncaughtException(HttpServletRequest request, HttpServletResponse response)
	{
	}
}
