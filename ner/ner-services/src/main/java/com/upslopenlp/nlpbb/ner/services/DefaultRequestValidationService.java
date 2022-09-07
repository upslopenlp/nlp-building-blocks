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
package com.upslopenlp.nlpbb.ner.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.upslopenlp.nlpbb.ner.model.Constants;
import com.upslopenlp.nlpbb.ner.model.exceptions.BadRequestException;
import com.upslopenlp.nlpbb.ner.model.services.RequestValidationService;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Default implementation of {@link RequestValidationService}.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class DefaultRequestValidationService implements RequestValidationService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultRequestValidationService.class);
	
	@Autowired
	private Environment env;
	
	@Override
	public void validate(String[] text, int confidence, String context, String language, LanguageCode languageCode) {
		
		// This default values cannot be set in the controller because it is not a constant.
		if(StringUtils.isEmpty(context)) {
			context = env.getProperty("default.context", "not-set");			
		}

		if(confidence < Constants.MINIMUM_CONFIDENCE_THRESHOLD_VALUE || confidence > Constants.MAXIMUM_CONFIDENCE_THRESHOLD_VALUE) {			
			throw new BadRequestException("Confidence value must be an integer between 0 and 100.");			
		}
		
		if(context.length() > 255) {
			throw new BadRequestException("The context cannot exceed 255 characters.");		
		}
		
		if(text.length == 0) {
			LOGGER.info("Consumed entity extraction request is invalid due to empty text.");
			throw new BadRequestException("Input text is required.");
		}

	}
		
}
