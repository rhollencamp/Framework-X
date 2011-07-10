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

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Simple Result object that writes contentType and content to the response
 *
 * @author rhollencamp
 */
public class ContentResult extends Result
{
	final private String contentType;
	final private String content;

	/**
	 * Set the content type and the content
	 *
	 * @param contentType
	 * @param content
	 */
	public ContentResult(final String contentType, final String content)
	{
		this.contentType = contentType;
		this.content = content;
	}

	/**
	 * Write the supplied content-type and content to the response
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@Override
	public void execute(final HttpServletRequest request, final HttpServletResponse response) throws IOException
	{
		response.setContentType(this.contentType);
		response.getWriter().write(this.content);
	}
}
