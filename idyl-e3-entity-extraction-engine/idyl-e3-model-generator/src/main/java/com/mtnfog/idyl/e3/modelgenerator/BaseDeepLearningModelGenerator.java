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
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;

import ai.idylnlp.model.manifest.ModelManifestUtils;
import ai.idylnlp.model.manifest.SecondGenModelManifest;
import ai.idylnlp.models.deeplearning.training.model.DeepLearningTrainingDefinition;

import com.neovisionaries.i18n.LanguageCode;

/**
 * Base class for model generators.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public abstract class BaseDeepLearningModelGenerator extends BaseRunnable {
	
	/**
	 * Processes the command line arguments to create a model.
	 * @param args The command line arguments.
	 * @throws Exception Thrown if the model cannot be created.
	 */
	public abstract void process(String[] args) throws Exception;
	
	/**
	 * Train a model.
	 * @param json A JSON-serialized {@link DeepLearningTrainingDefinition}.
	 * @return The model manifest {@link File file}.
	 * @throws Exception Thrown if the model cannot be generated.
	 */
	public abstract File train(String json) throws Exception;
	
	/**
	 * Gets the command line options for the runnable.
	 */
	public Options getOptions() {
		
		Options options = new Options();
		
		Option trainingDefinitionFileOption = new Option(BaseRunnable.TRAINING_DEFINITION_ARGUMENT_NAME, true, "The path to the training definition file.");
		trainingDefinitionFileOption.setRequired(true);
		options.addOption(trainingDefinitionFileOption);
		
		return options;
		
	}
		
	/**
	 * Validates a model's name.
	 * @param name The name of the model.
	 * @return <code>true</code> if the model's name validates successfully.
	 */
	public boolean validateName(String name) {

		if(StringUtils.isEmpty(name)) {
			
			return false;
			
		} else {
		
			return name.length() > 0 && name.length() <= 10 && name.matches("[A-Za-z0-9_]+");
			
		}
		
	}
	
	/**
	 * Gets a value used for the <code>creator.version</code> property
	 * in the model manifest.
	 * @return A formatted string for the <code>creator.version</code> property. The
	 * value will be formatted as <code>idyl-e3-2.3.0</code> where 2.3.0 is the
	 * version of Idyl E3 that generated the model.
	 */
	public String getCreatorVersion() {
		
		return "idyl-e3-" + getVersion();
		
	}
	
	public void generateManifest(File manifestFile, String modelId, String modelFile, DeepLearningTrainingDefinition definition) throws IOException {
		
		final String name = definition.getName();
		final String type = definition.getEntityType();
		final String language = definition.getLanguage();
		final LanguageCode languageCode = LanguageCode.getByCodeIgnoreCase(language);

		// No need for a model or word vectors URLs.
		final String modelSource = "";
		final String vectorsSource = "";
		
		final SecondGenModelManifest modelManifest = new SecondGenModelManifest(modelId, modelFile, languageCode,
				type, name, getCreatorVersion(), definition.getTrainingData().getWordVectorsFile(), definition.getHyperParameters().getWindowSize(),
				modelSource, vectorsSource, new Properties());
		
		// Generate the model's manifest.			
		ModelManifestUtils.generateSecondGenModelManifest(manifestFile, modelManifest);
		
	}
	
}
