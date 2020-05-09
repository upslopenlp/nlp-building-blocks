/*******************************************************************************
 * Copyright 2018 Mountain Fog, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.mtnfog.renku.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mtnfog.renku.model.exceptions.UnauthorizedException;

@Component
public class ApiAuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LogManager.getLogger(ApiAuthenticationInterceptor.class);	
	
	public static final String AUTHORIZATION_HEADER = "Authorization";
	
	@Autowired
	private Environment environment;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
				
		boolean authorized = true;

		final String apiKey = environment.getProperty("api.key");				
		
		if(StringUtils.isNotEmpty(apiKey)) {
			
			// Use plain authentication. The API key itself should be provided in the header.
		
			final String authorization = request.getHeader(AUTHORIZATION_HEADER);

			if(!StringUtils.equals(apiKey, authorization)) {
				
				LOGGER.warn("API authentication is enabled but the received request does not contain a valid API key.");
				
				throw new UnauthorizedException("Authorization is required.");
				
			}
			
		} else {
			
			// No API authentication.
			
		}
		
		return authorized;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// Do nothing.

	}

}
