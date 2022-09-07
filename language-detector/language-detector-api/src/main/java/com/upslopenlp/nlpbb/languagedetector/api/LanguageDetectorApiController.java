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
package com.upslopenlp.nlpbb.languagedetector.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upslopenlp.nlpbb.languagedetector.model.LanguageDetectionRequest;
import com.upslopenlp.nlpbb.languagedetector.model.api.LanguagesDetectionResponse;
import com.upslopenlp.nlpbb.languagedetector.model.api.Status;
import com.upslopenlp.nlpbb.languagedetector.model.exceptions.LanguageDetectionException;
import com.upslopenlp.nlpbb.languagedetector.model.services.LanguageDetectionService;

@Controller
public class LanguageDetectorApiController {
	
	private static final Logger LOGGER = LogManager.getLogger(LanguageDetectorApiController.class);

	@Autowired
	private LanguageDetectionService service;
	
	@RequestMapping(value="/api/language", method=RequestMethod.POST)
	public @ResponseBody LanguagesDetectionResponse detect(@RequestBody String text)
		throws LanguageDetectionException {
		
		return service.detect(new LanguageDetectionRequest(text));
		
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
