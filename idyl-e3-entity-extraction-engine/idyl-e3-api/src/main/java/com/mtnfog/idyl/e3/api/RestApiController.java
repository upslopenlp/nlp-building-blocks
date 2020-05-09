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
package com.mtnfog.idyl.e3.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.mtnfog.idyl.e3.model.Constants;
import com.mtnfog.idyl.e3.model.Status;
import com.mtnfog.idyl.e3.model.api.IdylE3EntityExtractionResponse;
import com.mtnfog.idyl.e3.model.services.EntityExtractionService;
import com.mtnfog.idyl.e3.model.services.StatusService;

@Controller
public class RestApiController {
						
	@Autowired
	private EntityExtractionService entityExtractionService;
	
	@Autowired
	private StatusService statusService;
	
	@RequestMapping(value = {"/api/extract", "/api/status"}, method = RequestMethod.HEAD)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public void head() {
		// Response for Apache NiFi.
	}	
		
	@ResponseBody
	@RequestMapping(value = "/api/extract", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)	
	public IdylE3EntityExtractionResponse extract(
			@RequestParam(value = "confidence", required = false, defaultValue = Constants.DEFAULT_CONFIDENCE_THRESHOLD) int confidence,
			@RequestParam(value = "context", required = false, defaultValue = Constants.DEFAULT_CONTEXT) String context,
			@RequestParam(value = "documentid", required = false, defaultValue = Constants.DEFAULT_DOCUMENT_ID) String documentId,
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "sort", required = false, defaultValue = Constants.DEFAULT_SORT) String sort,
			@RequestBody String[] text,
			@RequestHeader(value = RestConstants.AUTHORIZATION_HEADER, required = false) String authorization) {

		return entityExtractionService.extract(text, confidence, context, documentId, language, type, sort);				
		
	}	
	
	@ResponseBody
	@RequestMapping(value = "/api/status", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public Status status() {				
		
		return statusService.getStatus();
		
	}
	
}
