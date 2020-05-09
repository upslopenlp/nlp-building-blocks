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
import java.nio.charset.Charset;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import ai.idylnlp.models.deeplearning.training.DeepLearningEntityModelOperations;
import ai.idylnlp.models.deeplearning.training.model.DeepLearningTrainingDefinition;

/**
 * Executable class for creating deep learning entity models.
 * 
 * @author Mountain Fog, Inc.
 *
 */
@Configuration
@ComponentScan("com.mtnfog.idyl.e3")
public class DeepLearningEntityModelGenerator extends BaseDeepLearningModelGenerator {
	
	public static void main(String[] args) throws Exception {

		DeepLearningEntityModelGenerator entityModelGenerator = new DeepLearningEntityModelGenerator();
		entityModelGenerator.process(args);
		
	}
		
	@Override
	public void process(String[] args) throws Exception {
						
		output("\nIdyl E3 Deep Learning Entity Model Generator");
		output("Version: " + getVersion());
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(getOptions(), args);
		
		final File file = new File(cmd.getOptionValue("td"));
		final String json = FileUtils.readFileToString(file, Charset.defaultCharset());

		train(json);
		
	}	
	
	@Override
	public File train(String json) throws Exception {			
		
		DeepLearningEntityModelOperations ops = new DeepLearningEntityModelOperations();
		
		DeepLearningTrainingDefinition definition = ops.deserializeDefinition(json);
				
		final String modelFile = definition.getOutput().getOutputFile();
		final File manifestFile = new File(modelFile + ".manifest");
		
		long startTime = System.currentTimeMillis();
		
		// Train the model.
		final String modelId = ops.train(definition);
		
		// Generate the model's manifest.
		generateManifest(manifestFile, modelId, modelFile, definition);
				
		// Show a summary.
		output("Entity model generated complete. Summary:");			
		output("Model file   : " + modelFile);
		output("Manifest file : " + manifestFile.getName());
		output("Time Taken    : " + (System.currentTimeMillis() - startTime) + " ms");
		
		return manifestFile;
		
	}
	
}
