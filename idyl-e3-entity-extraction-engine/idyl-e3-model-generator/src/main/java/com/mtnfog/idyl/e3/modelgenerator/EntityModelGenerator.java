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
package com.mtnfog.idyl.e3.modelgenerator;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

import ai.idylnlp.models.opennlp.training.EntityModelOperations;
import ai.idylnlp.training.definition.model.TrainingDefinitionReader;

/**
 * Executable class for creating entity models.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class EntityModelGenerator extends BaseStandardModelGenerator {
	
	public static void main(String[] args) throws Exception {
		
		EntityModelGenerator entityModelGenerator = new EntityModelGenerator();
		entityModelGenerator.process(args);
		
	}

	@Override
	public void process(String[] args) throws Exception {		
		
		output("\nIdyl E3 Entity Model Generator");
		output("Version: " + getVersion());
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(getOptions(), args);

		TrainingDefinitionReader reader = getTrainingDefinitionReader(cmd.getOptionValue(BaseRunnable.TRAINING_DEFINITION_ARGUMENT_NAME));

		if(reader != null && validateTrainingDefinition(reader)) {
		
			train(reader);
			
		}
		
	}	
	
	@Override
	public File train(TrainingDefinitionReader reader) throws Exception {				
				
		final String modelFile = reader.getTrainingDefinition().getModel().getFile();
		final File manifestFile = new File(modelFile + ".manifest");
		
		long startTime = System.currentTimeMillis();
		
		// Train the model.
		final String modelId = EntityModelOperations.train(reader);
		
		// Generate the model's manifest.
		generateManifest(manifestFile, modelId, modelFile, reader);
				
		// Show a summary.
		output("Entity model generated complete. Summary:");			
		output("Model file   : " + modelFile);
		output("Manifest file : " + manifestFile.getName());
		output("Time Taken    : " + (System.currentTimeMillis() - startTime) + " ms");
		
		return manifestFile;
		
	}
	
}
