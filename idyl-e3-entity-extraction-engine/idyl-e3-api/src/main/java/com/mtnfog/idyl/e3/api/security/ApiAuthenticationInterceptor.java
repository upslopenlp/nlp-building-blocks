/*******************************************************************************
 * Copyright 2019 Mountain Fog, Inc.
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
package com.mtnfog.idyl.e3.api.security;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mtnfog.idyl.e3.api.RestConstants;
import com.mtnfog.idyl.e3.model.exceptions.BadRequestException;
import com.mtnfog.idyl.e3.model.exceptions.InternalServerErrorException;
import com.mtnfog.idyl.e3.model.exceptions.UnauthorizedException;

/**
 * Provides API authentication for API requests.
 * 
 * @author Mountain Fog, Inc.
 *
 */
@Component
public class ApiAuthenticationInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LogManager.getLogger(ApiAuthenticationInterceptor.class);	
	
	@Autowired
	private Environment env;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
				
		boolean authorized = true;
			
		if(StringUtils.equalsIgnoreCase(env.getProperty("api.authentication.enabled"), "true")) {
			
			final String authorization = request.getHeader(RestConstants.AUTHORIZATION_HEADER);
			
			if(StringUtils.isNotEmpty(authorization)) {
				
				final String apiKey = env.getProperty("api.authentication.key");
				
				if(StringUtils.equalsIgnoreCase(env.getProperty("api.authentication.method"), "plain")) {
					
					// Use plain authentication. The API key itself is provided in the header.
	
					if(!StringUtils.equals(apiKey, authorization)) {
						
						LOGGER.warn("API authentication is enabled but the received request does not contain a valid API key.");
						
						throw new UnauthorizedException("Authorization is required.");
						
					}
					
				} else if(StringUtils.equalsIgnoreCase(env.getProperty("api.authentication.method"), "hmacsha512v1")) {										
					
					// The API request is HMACSHA512v1 signed.
					// The value of the authorization header is the Hex HMACSHA512. We need to compute
					// ours based on the request and compare the two values.
					
					// Make sure there is an x-idyle3-date header and it has a value.
					if(StringUtils.isEmpty(request.getHeader("x-idyle3-date"))) {
						throw new BadRequestException("Missing x-idyle3-date header value.");
					}
					
					if(isRequestWithinTimeWindow(request.getHeader("x-idyle3-date"))) {
						throw new UnauthorizedException("API request was signed longer than " + env.getProperty("api.authentication.time.window", "5") + " minutes ago.");
					}
					
					// Compute the signature.
					final String computedHmacSha512 = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, apiKey).hmacHex(computeV1RequestSignature(request));
					
					// Compare the signature.
					if(!StringUtils.equals(computedHmacSha512, authorization)) {
						
						LOGGER.warn("API authentication is enabled but the signature of the received request is invalid.");
						
						throw new UnauthorizedException("Authorization is required.");
						
					}
					
				} else {
					
					LOGGER.warn("Invalid API authentication method. Refer to the API documentation for valid values.");
					
					throw new InternalServerErrorException("Idyl E3 API authorization is incorrectly configured.");
					
				}
				
			} else {
				
				LOGGER.warn("API authentication is enabled but no API key was present in the received request.");
				
				throw new UnauthorizedException("Authorization is required.");
				
			}
			
		}
		
		return authorized;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// Do nothing.

	}
	
	private String computeV1RequestSignature(HttpServletRequest request) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		sb.append(request.getMethod().toUpperCase() + "\n");
		sb.append(request.getHeader("host") + "\n");
		sb.append(request.getHeader("x-idyle3-date") + "\n");
		sb.append(request.getHeader("x-idyle3-hash"));
		
		final String signature = sb.toString();
		
		LOGGER.debug("Computed request signature: " + signature);
		
		return signature;
        
	}
	
	private boolean isRequestWithinTimeWindow(String xIdylE3Date) {
		
		final Date now = new Date();
		final Date apiRequestDate = fromIso8601(xIdylE3Date);
		
		final int timeWindow = Integer.valueOf(env.getProperty("api.authentication.time.window", "5"));
		
		final long window = timeWindow * 60 * 1000;
		final long difference = now.getTime() - apiRequestDate.getTime();
		
		return (Math.abs(difference) >= window);
		
	}
	
	private Date fromIso8601(String iso8601) {
		
		try {
		
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			
			return dateFormat.parse(iso8601);
			
		} catch (Exception ex) {
			
			LOGGER.error("The timestamp received in the x-idyle3-date header is malformed: " + iso8601);
			
			throw new BadRequestException("Malformed timestamp in value of x-idyle3-date header.");
			
		}
        
	}

}
