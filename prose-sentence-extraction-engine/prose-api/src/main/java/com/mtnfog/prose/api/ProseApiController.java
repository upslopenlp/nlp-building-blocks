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
package com.mtnfog.prose.api;

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
import com.mtnfog.prose.model.api.Status;
import com.mtnfog.prose.model.services.SentenceDetectionService;

@Controller
public class ProseApiController {
	
	private static final Logger LOGGER = LogManager.getLogger(ProseApiController.class);

	@Autowired
	private SentenceDetectionService service;
	
	@RequestMapping(value="/api/sentences", method=RequestMethod.POST)
	public @ResponseBody String[] sentences(
			@RequestParam(value="language", defaultValue="eng", required=false) String language,
			@RequestBody String text)
		throws LanguageDetectionException {
		
		return service.detectSentences(text, language);
		
	}
	
	@RequestMapping(value="/api/status", method=RequestMethod.GET)
	public @ResponseBody Status status() {
		
		return new Status("Healthy");
		
	}

}
