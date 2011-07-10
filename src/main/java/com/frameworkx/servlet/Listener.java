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
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Responsible for initializing a Framework X application. This is called when the webapp is started
 *
 * This needs to be added to the webapp's web.xml as a listener:
 * <listener>
 *   <listener-class>com.frameworkx.servlet.Listener</listener-class>
 * </listener>
 *
 * @author robert.hollencamp
 */
public class Listener implements ServletContextListener
{
	public void contextInitialized(final ServletContextEvent sce)
	{
		final String className = sce.getServletContext().getInitParameter("com.frameworkx.applicationImpl");
		if (className == null || className.isEmpty()) {
			throw new RuntimeException("Application Class must be specified in web.xml");
		}

		try {
			Class<? extends AbstractApplication> clazz = Class.forName(className).asSubclass(AbstractApplication.class);
			AbstractApplication app = clazz.getConstructor().newInstance();

			sce.getServletContext().setAttribute("com.frameworkx.application", app);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void contextDestroyed(final ServletContextEvent sce)
	{
	}
}
