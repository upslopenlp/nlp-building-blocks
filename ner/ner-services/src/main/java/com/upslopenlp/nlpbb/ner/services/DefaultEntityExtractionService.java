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

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.neovisionaries.i18n.LanguageCode;
import com.upslopenlp.nlpbb.ner.model.Entity;
import com.upslopenlp.nlpbb.ner.model.api.EntityExtractionResponse;
import com.upslopenlp.nlpbb.ner.model.exceptions.InternalServerErrorException;
import com.upslopenlp.nlpbb.ner.model.services.EntityExtractionService;
import com.upslopenlp.nlpbb.ner.model.services.RequestValidationService;

/**
 * Default implementation of {@link EntityExtractionService}.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class DefaultEntityExtractionService implements EntityExtractionService {

	private static final Logger LOGGER = LogManager.getLogger(DefaultEntityExtractionService.class);		
	
	@Autowired
	private RequestValidationService requestValidator;

	@Autowired
	private Environment env;

	@Override
	public EntityExtractionResponse extract(String[] text, int confidence, String context,
											String documentId, String language, String type, LanguageCode languageCode) {

		LOGGER.trace("Extracting under context {} and document ID {}.", context);
		
		requestValidator.validate(text, confidence, context, language);

		if(StringUtils.isNotEmpty(language)) {

			languageCode = LanguageCode.getByCodeIgnoreCase(language);

			if(languageCode == null) {
				LOGGER.warn("Request contained invalid language code: {}", languageCode);
			}

		}

		// Create the extraction request.
		EntityExtractionRequest entityExtractionRequest = new EntityExtractionRequest(text)
				.withConfidenceThreshold(confidence)
				.withContext(context)
				.withDocumentId(documentId)
				.withType(type)
				.withOrder(order)
				.withDuplicateEntityStrategy(getDuplicateEntityHandlingStrategy())
				.withLanguage(languageCode);
		
		EntityExtractionResponse entityExtractionResponse = null;
		
		try {
		
			Set<Entity> entities = new HashSet<>();
			long extractionTime = 0;
			
			for(EntityRecognizer entityRecognizer : initializer.getEntityRecognizers()) {			
				
				EntityExtractionResponse response = entityRecognizer.extractEntities(entityExtractionRequest);
				
				entities.addAll(response.getEntities());
				extractionTime += response.getExtractionTime();
				
			}
			
			entityExtractionResponse = new EntityExtractionResponse(entities, extractionTime);

		} catch (Exception ex) {
			
			LOGGER.error("Unable to process extraction request.", ex);
			
			throw new InternalServerErrorException("An error occurred processing the request. See the Idyl E3 log for more information.");
			
		}

		return entityExtractionResponse;
				
	}

}
