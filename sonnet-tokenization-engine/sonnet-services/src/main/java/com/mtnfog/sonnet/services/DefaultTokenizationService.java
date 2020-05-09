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
package com.mtnfog.sonnet.services;

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
import ai.idylnlp.nlp.tokenizers.BreakIteratorTokenizer;
import ai.idylnlp.nlp.tokenizers.ModelTokenizer;
import ai.idylnlp.opennlp.custom.modelloader.LocalModelLoader;
import ai.idylnlp.opennlp.custom.validators.TrueModelValidator;

import com.mtnfog.sonnet.model.exceptions.BadRequestException;
import com.mtnfog.sonnet.model.services.TokenizationService;
import com.mtnfog.sonnet.services.utils.TokenUtils;
import com.neovisionaries.i18n.LanguageCode;

import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer.ALGORITHM;
import opennlp.tools.tokenize.TokenizerModel;

@Component
public class DefaultTokenizationService implements TokenizationService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultTokenizationService.class);
	
	private Map<LanguageCode, ModelTokenizer> models;
		
	public DefaultTokenizationService() throws IOException {

		models = new HashMap<>();
		
		// Load custom models from the file system.
		loadModels();
		
	}

	@Override
	public String[] tokenize(String text, String language, int stem, int removeDuplicates) {
		
		final LanguageCode languageCode = LanguageCode.getByCodeIgnoreCase(language);
		
		if(languageCode == null) {
			throw new BadRequestException("Missing language code.");
		}
		
		// Look to see if there is a token model for this language.
		// If so, use the ModelTokenizer instead.
		
		String[] tokens;
		
		if(models.containsKey(languageCode)) {
			
			LOGGER.trace("Using model to tokenize.");
			tokens = models.get(languageCode).tokenize(text);
			
		} else {
		
			tokens = useBreakIterator(text, languageCode);
			
		}
		
		if(stem == 1) {
			
			LOGGER.trace("Preparing to stem tokens.");
					
			// Get the stemmer from OpenNLP. If there is no stemmer
			// for this language throw an exception to the client.

			final ALGORITHM algorithm;
			
			try {
				algorithm = SnowballStemmer.ALGORITHM.getByLanguageCode(language);
			} catch (IllegalArgumentException ex) {
				throw new BadRequestException("No stemmer exists for language " + language);
			}
			
			// Now that we have the stemming implementation we can get a stemmer.
			final SnowballStemmer stemmer = new SnowballStemmer(algorithm);
			
			// Stem the tokens.
			for(int x = 0; x < tokens.length; x++) {
				
				LOGGER.trace("Stemming token {}.", tokens[x]);
				
				// Stem each token and replace the token with the stemmed
				// token in the array.				
				final String token = tokens[x];
				final String stemmedToken = stemmer.stem(token).toString();
				tokens[x] = stemmedToken;
				
			}
			
		}
		
		if(removeDuplicates == 1) {			
			tokens = TokenUtils.removeDuplicateTokens(tokens);
		}
		
		return tokens;
		
	}
	
	private String[] useBreakIterator(String text, LanguageCode languageCode) {

		BreakIteratorTokenizer tokenizer = new BreakIteratorTokenizer(languageCode);
		
		if(!tokenizer.getLanguageCodes().contains(languageCode.getAlpha3().toString())) {
			throw new BadRequestException("Language code is not supported by the current configuration.");
		}
		
		return tokenizer.tokenize(text);
		
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
			    		
			    		LocalModelLoader<TokenizerModel> modelLoader = new LocalModelLoader<TokenizerModel>(validator, modelsDirectory);
			    		TokenizerModel tokenModel = modelLoader.getModel(standardModelManifest, TokenizerModel.class);			    		
			    		
			    		final File modelFile = new File(modelsDirectory, standardModelManifest.getModelFileName());
			    		
			    		LOGGER.info("Loading model file {}", modelFile);
			    		
			    		ModelTokenizer modelTokenizer = new ModelTokenizer(tokenModel, modelManifest.getLanguageCode());
			    		
			    		models.put(modelManifest.getLanguageCode(), modelTokenizer);
			    	
			    	} catch (Exception ex) {
			    		
			    		LOGGER.error("Unable to load model " + modelManifest.getModelFileName(), ex);
			    		
			    	}
				
				}
				
			}
			
		}
		
	}
	
}
