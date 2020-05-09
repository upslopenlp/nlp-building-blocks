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
package com.mtnfog.verso.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mtnfog.verso.model.PreprocessingRequest;
import com.mtnfog.verso.model.api.Status;
import com.mtnfog.verso.model.services.PreprocessingService;

@Controller
public class VersoApiController {
	
	private static final Logger LOGGER = LogManager.getLogger(VersoApiController.class);

	@Autowired
	private PreprocessingService service;
	
	@RequestMapping(value="/api/preprocess", method=RequestMethod.POST)
	public @ResponseBody String preprocess(
			@RequestParam(value="lc", required=false, defaultValue="n") String lowercase,
			@RequestParam(value="uc", required=false, defaultValue="n") String uppercase,
			@RequestParam(value="minw", required=false, defaultValue="0") int minWordLength,
			@RequestParam(value="maxw", required=false, defaultValue="0") int maxWordLength,
			@RequestParam(value="wcd", required=false, defaultValue="n") String removeWordsContainingDigits,
			@RequestParam(value="d", required=false, defaultValue="n") String removeDigits,
			@RequestParam(value="p", required=false, defaultValue="n") String removePunctuation,
			@RequestParam(value="sw", required=false, defaultValue="n") String removeStopWords,
			@RequestParam(value="stem", required=false, defaultValue="n") String stem,
			@RequestParam(value = "csw", required=false) String[] customStopWords,
			@RequestParam(value="language", required=false, defaultValue="eng") String language,
			@RequestBody String text) {
		
		PreprocessingRequest request = new PreprocessingRequest();
		request.setLowercase(lowercase);
		request.setUppercase(uppercase);
		request.setMinWordLength(minWordLength);
		request.setMaxWordLength(maxWordLength);
		request.setRemoveWordsContainingDigits(removeWordsContainingDigits);
		request.setRemoveDigits(removeDigits);
		request.setRemovePunctuation(removePunctuation);
		request.setRemoveStopWords(removeStopWords);
		request.setCustomStopWords(customStopWords);
		request.setStem(stem);
		request.setLanguage(language);
		
		return service.preprocess(text, request);
		
	}
		
	@RequestMapping(value="/api/status", method=RequestMethod.GET)
	public @ResponseBody Status status() {
		
		return new Status("Healthy");
		
	}

}
