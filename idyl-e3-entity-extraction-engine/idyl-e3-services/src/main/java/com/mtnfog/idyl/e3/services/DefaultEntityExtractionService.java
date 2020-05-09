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
package com.mtnfog.idyl.e3.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mtnfog.idyl.e3.model.api.IdylE3EntityExtractionResponse;
import com.mtnfog.idyl.e3.model.exceptions.InternalServerErrorException;
import com.mtnfog.idyl.e3.model.exceptions.ServiceUnavailableException;
import com.mtnfog.idyl.e3.model.metrics.MetricReporter;
import com.mtnfog.idyl.e3.model.metrics.Unit;
import com.mtnfog.idyl.e3.model.services.EntityExtractionService;
import com.mtnfog.idyl.e3.model.services.InitializationService;
import com.mtnfog.idyl.e3.model.services.RequestValidationService;
import ai.idylnlp.model.entity.Entity;
import ai.idylnlp.model.nlp.DuplicateEntityStrategy;
import ai.idylnlp.model.nlp.EntityOrder;
import ai.idylnlp.model.nlp.ner.EntityExtractionRequest;
import ai.idylnlp.model.nlp.ner.EntityExtractionResponse;
import ai.idylnlp.model.nlp.ner.EntityRecognizer;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Default implementation of {@link EntityExtractionService}.
 * 
 * @author Mountain Fog, Inc.
 *
 */
@Component
public class DefaultEntityExtractionService implements EntityExtractionService {

	private static final Logger LOGGER = LogManager.getLogger(DefaultEntityExtractionService.class);		
	
	@Autowired
	private RequestValidationService requestValidator;
	
	@Autowired
	private InitializationService initializer;
	
	@Autowired
	private MetricReporter metricReporter;
	
	@Autowired
	private Environment env;

	@Override
	public IdylE3EntityExtractionResponse extract(String[] text, int confidence, String context,
			String documentId, String language, String type, String sort) {
		
		if(!initializer.isLoaded()) {
			
			LOGGER.info("The Idyl E3 models are being loaded.");
			
			throw new ServiceUnavailableException("The Idyl E3 models are being loaded.");
			
		}
		
		LOGGER.trace("Extracting under context {} and document ID {}.", context);
		
		requestValidator.validate(text, confidence, context, language, sort);
		
		EntityOrder order = EntityOrder.fromValue(sort);
		
		LanguageCode languageCode = null;
		
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
		
		IdylE3EntityExtractionResponse idylE3EntityExtractionResponse = null;
		
		try {
		
			Set<Entity> entities = new HashSet<>();
			long extractionTime = 0;
			
			for(EntityRecognizer entityRecognizer : initializer.getEntityRecognizers()) {			
				
				EntityExtractionResponse response = entityRecognizer.extractEntities(entityExtractionRequest);
				
				entities.addAll(response.getEntities());
				extractionTime += response.getExtractionTime();
				
			}
			
			idylE3EntityExtractionResponse = new IdylE3EntityExtractionResponse(entities, extractionTime);
			
			// Add the value to the rolling average.
			metricReporter.logEntityExtractionTime(idylE3EntityExtractionResponse.getExtractionTime());
			metricReporter.report(MetricReporter.MEASUREMENT_EXTRACTION, "entityExtractionTime", idylE3EntityExtractionResponse.getExtractionTime(), Unit.MILLISECONDS);
			
		} catch (Exception ex) {
			
			LOGGER.error("Unable to process extraction request.", ex);
			
			throw new InternalServerErrorException("An error occurred processing the request. See the Idyl E3 log for more information.");
			
		}

		return idylE3EntityExtractionResponse;
				
	}
	
	private DuplicateEntityStrategy getDuplicateEntityHandlingStrategy() {
		
		final String strategy = env.getProperty("duplicate.entity.handling.strategy", "highest");
		
		if(StringUtils.equalsIgnoreCase(strategy, "retain")) {
			return DuplicateEntityStrategy.RETAIN_DUPICATES;
		} else if(StringUtils.equalsIgnoreCase(strategy, "highest")) {
			return DuplicateEntityStrategy.USE_HIGHEST_CONFIDENCE;
		}
		
		// No value matched.
		LOGGER.warn("Invalid duplicate entity handling strategy: {}", strategy);
		
		return DuplicateEntityStrategy.RETAIN_DUPICATES;
		
	}
		
}
