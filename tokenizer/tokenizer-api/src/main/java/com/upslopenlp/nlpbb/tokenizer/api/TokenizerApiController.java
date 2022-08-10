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
package com.upslopenlp.nlpbb.tokenizer.api;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upslopenlp.nlpbb.tokenizer.model.api.Status;
import com.upslopenlp.nlpbb.tokenizer.model.services.TokenizationService;

@Controller
public class TokenizerApiController {
	
	private static final Logger LOGGER = LogManager.getLogger(TokenizerApiController.class);

	@Autowired
	private TokenizationService service;
	
	@RequestMapping(value="/api/tokenize", method=RequestMethod.POST)
	public @ResponseBody String[] tokenize(@RequestBody String text) throws IOException {
		
		return service.tokenize(text);
		
	}

	@RequestMapping(value="/api/status", method=RequestMethod.GET)
	public @ResponseBody Status status() {
		
		return new Status("Healthy");
		
	}

}
