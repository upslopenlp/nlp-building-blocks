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
package com.mtnfog.renku.api;

import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ai.idylnlp.model.nlp.language.LanguageDetectionException;
import ai.idylnlp.model.nlp.language.LanguageDetectionResponse;
import com.mtnfog.renku.model.LanguageDetectionRequest;
import com.mtnfog.renku.model.api.LanguagesDetectionResponse;
import com.mtnfog.renku.model.api.Status;
import com.mtnfog.renku.model.services.LanguageDetectionService;

@Controller
public class RenkuApiController {
	
	private static final Logger LOGGER = LogManager.getLogger(RenkuApiController.class);

	@Autowired
	private LanguageDetectionService service;
	
	@RequestMapping(value="/api/language", method=RequestMethod.POST)
	public @ResponseBody LanguageDetectionResponse detect(
			@RequestParam(value="limit", required=false, defaultValue="10") int limit,
			@RequestBody String text)
		throws LanguageDetectionException {
		
		return service.detect(new LanguageDetectionRequest(text, limit));
		
	}
	
	@RequestMapping(value="/api/language", method=RequestMethod.GET)
	public @ResponseBody LanguagesDetectionResponse detect(
			@RequestParam(value="limit", required=false, defaultValue="10") int limit,
			@RequestParam("q") List<String> input)
		throws LanguageDetectionException {
		
		List<LanguageDetectionResponse> responses = new LinkedList<>();
		
		for(String text : input) {
			
			responses.add(service.detect(new LanguageDetectionRequest(text, limit)));
			
		}
		
		return new LanguagesDetectionResponse(responses);
		
	}
	
	@RequestMapping(value="/api/languages", method=RequestMethod.GET)
	public @ResponseBody String[] languages() {
		
		return service.languages();
		
	}
	
	@RequestMapping(value="/api/status", method=RequestMethod.GET)
	public @ResponseBody Status status() {
		
		return new Status("Healthy");
		
	}

}
