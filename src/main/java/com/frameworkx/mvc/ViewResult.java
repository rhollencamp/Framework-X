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
package com.frameworkx.mvc;

import com.frameworkx.AbstractApplication;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Result object that will redirect to the view engine
 *
 * @author Robert Hollencamp
 */
public class ViewResult extends Result
{
	private static String controllerPostfix;
	private static String templateBase;
	private static String templateExtension;

	/**
	 * Forward a request to the template engine
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void execute(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException
	{
		// view data
		final Controller co = (Controller)request.getAttribute("com.frameworkx.controller");
		for (Map.Entry<String, Object> kvp : co.viewData.entrySet()) {
			request.setAttribute(kvp.getKey(), kvp.getValue());
		}

		// method name
		final Method m = (Method)request.getAttribute("com.frameworkx.controllerMethod");
		final String methodName = m.getName();

		// controller class name
		String className = co.getClass().getSimpleName();
		if (controllerPostfix != null && controllerPostfix.isEmpty() == false && className.endsWith(controllerPostfix)) {
			className = className.substring(0, className.length() - controllerPostfix.length());
		}

		// determine path to template and forward
		final String templateName = templateBase + "/" + className + "/" + methodName + "." + templateExtension;
		request.getRequestDispatcher(templateName).forward(request, response);
	}

	/**
	 * Load configuration options the first time a ViewResult is
	 * @param request
	 */
	public static void init(final AbstractApplication app)
	{
		controllerPostfix = app.getProperty("viewEngine.controllerPostfix");
		templateBase = app.getProperty("viewEngine.templateBaseDir");
		templateExtension = app.getProperty("viewEngine.templateExtension");
	}
}
