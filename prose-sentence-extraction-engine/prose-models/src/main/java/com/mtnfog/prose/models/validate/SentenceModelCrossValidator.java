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
package com.mtnfog.prose.models.validate;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import ai.idylnlp.model.training.FMeasure;
import ai.idylnlp.model.training.FMeasureModelValidationResult;
import ai.idylnlp.models.opennlp.training.SentenceModelOperations;
import ai.idylnlp.training.definition.TrainingDefinitionFileReader;
import ai.idylnlp.training.definition.model.TrainingDefinitionReader;

import com.mtnfog.prose.models.BaseRunnable;

/**
 * Runnable class for performing cross validation on sentence models.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class SentenceModelCrossValidator extends BaseValidator {
	
	public static void main(String[] args) throws Exception {
		
		SentenceModelCrossValidator validator = new SentenceModelCrossValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		if(result != null) {
			
			output("- Evaluation Result:\n" + result.getFmeasure().toString());
			
			for(FMeasure measure : result.getFmeasures()) {
				
				output("- Fold result:\n" + measure.toString());
				
			}
			
		}
		
	}
	
	@Override
	public FMeasureModelValidationResult process(String[] args) throws Exception {
		
		output("\nProse Sentence Model Cross Validator");
		output("Version: " + getVersion());
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(getOptions(), args);
				
		final File trainingDefinitionFile = new File(cmd.getOptionValue("td"));
		
		output("Beginning cross-validation using definition file: " + trainingDefinitionFile.getAbsolutePath());
		
		if(!trainingDefinitionFile.exists()) {
			
			output("The training definition file does not exist.");
			return null;
			
		}
		
		int folds = 10;
		if(cmd.hasOption("f")) {
			folds = Integer.valueOf(cmd.getOptionValue("f"));
		}
		
		TrainingDefinitionReader reader = new TrainingDefinitionFileReader(trainingDefinitionFile);
		
		return SentenceModelOperations.crossValidate(reader, folds);
		
	}
	
	@Override
	public Options getOptions() {
		
		Options options = new Options();
		
		Option trainingDefinitionFileOption = new Option(BaseRunnable.TRAINING_DEFINITION_ARGUMENT_NAME, true, "The path to the training definition file.");
		trainingDefinitionFileOption.setRequired(true);
		options.addOption(trainingDefinitionFileOption);
		
		Option foldsOption = new Option("f", true, "The number of folds. Defaults to 10.");
		foldsOption.setRequired(false);
		options.addOption(foldsOption);
		
		return options;
		
	}
	
}
