/*******************************************************************************
 * Copyright 2022 UpslopeNLP
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
package com.upslopenlp.nlpbb.ner.api;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.neovisionaries.i18n.LanguageCode;
import com.upslopenlp.nlpbb.ner.model.Entity;
import com.upslopenlp.nlpbb.ner.model.services.EntityExtractionService;

@Controller
public class RestApiController {
						
	@Autowired
	private EntityExtractionService entityExtractionService;

	@RequestMapping(value = {"/api/extract", "/api/status"}, method = RequestMethod.HEAD)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public void head() {
		// Response for Apache NiFi.
	}	
		
	@ResponseBody
	@RequestMapping(value = "/api/extract", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)	
	public Set<Entity> extract(
			@RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "type", required = false) String type,
			@RequestBody String[] text) {

		final LanguageCode languageCode = LanguageCode.getByCode(language);

		return entityExtractionService.extract(text, type, languageCode);
		
	}	
	
	@ResponseBody
	@RequestMapping(value = "/api/status", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	@ResponseStatus(HttpStatus.OK)
	public String status() {
		
		return "Healthy";
		
	}
	
}
