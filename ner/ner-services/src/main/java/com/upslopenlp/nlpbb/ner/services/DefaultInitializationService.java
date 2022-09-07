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

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import opennlp.tools.namefind.TokenNameFinderModel;

import com.upslopenlp.nlpbb.ner.model.services.SystemInitializerService;

/**
 * Default implementation of {@link InitializationService} to configure
 * Idyl E3's {@link IdylPipeline pipeline}.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class DefaultInitializationService implements InitializationService {

	private static final Logger LOGGER = LogManager.getLogger(DefaultInitializationService.class);
	
	private List<EntityRecognizer> entityRecognizers;
	private boolean loaded;
	
	@Autowired
	private ConfidenceFilter confidenceFilter;

	@Autowired
	private SystemInitializerService initializerService;
	
	@Override
	public boolean isLoaded() {		
		return loaded;
	}
	
	@Override
	public List<EntityRecognizer> getEntityRecognizers() {
		return entityRecognizers;
	}
	
	@Override
	@PostConstruct
	public void initialize() {
		
		LOGGER.info("Initializing Idyl E3.");
		
		initializerService.initialize();
		
		entityRecognizers = new LinkedList<EntityRecognizer>();
		loaded = false;
		
		final ModelValidator validator = new DefaultModelValidator();
		
		try {

			final String modelsDirectory = new File("models").getAbsolutePath();
	
			LOGGER.info("Using model directory: " + modelsDirectory);
			
			// Create the model loaders.
			ModelLoader<TokenNameFinderModel> entityModelLoader = new LocalModelLoader<TokenNameFinderModel>(validator, modelsDirectory);
			
			// Create the configuration given the modelLoader and the models.	
			OpenNLPEntityRecognizerConfiguration modelEntityRecognizerConfiguration = new OpenNLPEntityRecognizerConfiguration.Builder()
				.withEntityModelLoader(entityModelLoader)
				.withConfidenceFilter(confidenceFilter)
				.build();
			
			modelEntityRecognizerConfiguration.setStatsReporter(statsReporter);
			
			// Create the Deep Learning configuration.
			DeepLearningEntityRecognizerConfiguration deepLearningEntityRecognizerConfiguration = 
					new DeepLearningEntityRecognizerConfiguration.Builder()
					.build(modelsDirectory);
			
			deepLearningEntityRecognizerConfiguration.setConfidenceFilter(confidenceFilter);
			deepLearningEntityRecognizerConfiguration.setStatsReporter(statsReporter);			
						
			// Get the available model manifests.
			List<ModelManifest> modelManifests = ModelManifestUtils.getModelManifests(modelsDirectory);
						
			if(CollectionUtils.isNotEmpty(modelManifests)) {
			
				for(ModelManifest modelManifest : modelManifests) {										
					
						StandardModelManifest standardModelManifest = (StandardModelManifest) modelManifest;
													
						// Load the token name finder model (entity model).
						
					    LOGGER.info("Entity Class: {}, Model File Name: {}, Language Code: {}",
					    		modelManifest.getType(), modelManifest.getModelFileName(), modelManifest.getLanguageCode()); 		    			  
					    
				    	LOGGER.info("Enabling model {}.", modelManifest.getModelFileName());		
				    	
						// Add the entity model to the configuration.
						modelEntityRecognizerConfiguration.addEntityModel(modelManifest.getType(), modelManifest.getLanguageCode(), standardModelManifest);						

				}
				
			} else {
				
				LOGGER.warn("No model manifest files were found.");
				
			}
														
			// Load the models in a separate thread to not block startup.
			final Thread modelLoadingThread = new Thread() {
				
			    @Override
				public void run() {
			    			    		
			    	// Create the entity recognizers.
			    	EntityRecognizer opennlpEntityRecognizer = new OpenNLPEntityRecognizer(modelEntityRecognizerConfiguration);
			    	EntityRecognizer deepLearningEntityRecognizer = new DeepLearningEntityRecognizer(deepLearningEntityRecognizerConfiguration);
			    	
			    	// Add the entity recognizers to the list.
			    	entityRecognizers.add(opennlpEntityRecognizer);
			    	entityRecognizers.add(deepLearningEntityRecognizer);		 

			    	// Initialize pattern-based entity recognizers.
			    	try {
						final File patternsFile = new File("patterns.properties");
						if(patternsFile.exists()) {
							final List<String> lines = IOUtils.readLines(new FileInputStream(patternsFile));
							for(String line : lines) {
								// Expected format is: entitytype=regex
								final String entityType = line.substring(0, line.indexOf("="));
								final Pattern pattern = Pattern.compile(line.substring(entityType.indexOf("=") + 1));
								entityRecognizers.add(new RegularExpressionEntityRecognizer(pattern, entityType));
							}
							
						} else {
							LOGGER.info("Skipping pattern entity recognizers because no patterns.properties file found.");
						}
			    	} catch (Exception ex) {
			    		LOGGER.error("Unable to initialize pattern entity recognizers.", ex);
			    	}

			    	loaded = true;
			    	
			    }				
			
			};

			// Start the thread to load the models.
			modelLoadingThread.start();
			
			// All done.
			LOGGER.info("Idyl E3 API is ready. Models may still be loading.");
			
		} catch (Exception ex) {

			LOGGER.error("Unable to initialize Idyl E3.", ex);

		}
				
	}
			
}
