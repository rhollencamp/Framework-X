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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for Framework-X plugins. All plugins need to implement
 *
 * @author Robert Hollencamp
 */
public interface Plugin
{
	public void init(String name, AbstractApplication app);

	public void onRequestReceived(HttpServletRequest request, HttpServletResponse response);

	public void onRequestFinally(HttpServletRequest request, HttpServletResponse response);
}
