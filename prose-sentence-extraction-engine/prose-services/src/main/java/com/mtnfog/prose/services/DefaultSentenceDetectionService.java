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
package com.mtnfog.prose.services;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.manifest.ModelManifest;
import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.StandardModelManifest;
import ai.idylnlp.model.nlp.SentenceDetector;
import ai.idylnlp.nlp.sentence.BreakIteratorSentenceDetector;
import ai.idylnlp.nlp.sentence.ModelSentenceDetector;
import ai.idylnlp.opennlp.custom.modelloader.LocalModelLoader;
import ai.idylnlp.opennlp.custom.validators.TrueModelValidator;

import com.mtnfog.prose.model.api.exceptions.BadRequestException;
import com.mtnfog.prose.model.services.SentenceDetectionService;
import com.neovisionaries.i18n.LanguageCode;

import opennlp.tools.sentdetect.SentenceModel;

@Component
public class DefaultSentenceDetectionService implements SentenceDetectionService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultSentenceDetectionService.class);
	
	private Map<LanguageCode, SentenceDetector> models;
	
	public DefaultSentenceDetectionService() throws IOException {

		models = new HashMap<>();
		
		// Load custom models from the file system.
		loadModels();
		
	}

	@Override
	public String[] detectSentences(String text, String language) {
		
		LanguageCode languageCode = LanguageCode.getByCodeIgnoreCase(language);
		
		if(languageCode == null) {
			throw new BadRequestException("Invalid language code.");
		}
		
		// Look to see if there is a sentence model for this language.
		if(models.containsKey(languageCode)) {
			
			LOGGER.debug("Using model for language {}.", languageCode.getAlpha3().toString());			
			return models.get(languageCode).sentDetect(text);
			
		} else {
			
			LOGGER.debug("No model found for language {}.", languageCode.getAlpha3().toString());		
			return useBreakIterator(text, languageCode);
			
		}
		
	}
	
	private String[] useBreakIterator(String text, LanguageCode languageCode) {

		BreakIteratorSentenceDetector sentenceDetector = new BreakIteratorSentenceDetector(languageCode);
		
		if(!sentenceDetector.getLanguageCodes().contains(languageCode.getAlpha3().toString())) {
			throw new BadRequestException("Language code is not supported by the current configuration.");
		}
		
		return sentenceDetector.sentDetect(text);
		
	}
	
	private void loadModels() {
		
		final String modelsDirectory = new File(System.getProperty("user.dir"), "models").getAbsolutePath();
		
		final ModelValidator validator = new TrueModelValidator();
		
		LOGGER.info("Using model directory: " + modelsDirectory);
				
		// Get the available model manifests.
		List<ModelManifest> modelManifests = ModelManifestUtils.getModelManifests(modelsDirectory);
					
		if(CollectionUtils.isNotEmpty(modelManifests)) {
			
			for(ModelManifest modelManifest : modelManifests) {
				
				if(modelManifest.getGeneration() == ModelManifest.FIRST_GENERATION) {
				
					final StandardModelManifest standardModelManifest = (StandardModelManifest) modelManifest;
								    	
			    	try {
			    		
			    		LocalModelLoader<SentenceModel> modelLoader = new LocalModelLoader<SentenceModel>(validator, modelsDirectory);
			    		SentenceModel tokenModel = modelLoader.getModel(standardModelManifest, SentenceModel.class);			    		
			    		
			    		final File modelFile = new File(modelsDirectory, standardModelManifest.getModelFileName());
			    		
			    		LOGGER.info("Loading model file {}", modelFile);
			    		
			    		SentenceDetector modelTokenizer = new ModelSentenceDetector(tokenModel, modelManifest.getLanguageCode());
			    		
			    		models.put(modelManifest.getLanguageCode(), modelTokenizer);
			    	
			    	} catch (Exception ex) {
			    		
			    		LOGGER.error("Unable to load model " + modelManifest.getModelFileName(), ex);
			    		
			    	}
				
				}
				
			}
			
		}
		
	}
	
}
