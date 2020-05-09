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
package com.mtnfog.idyl.e3.modelvalidator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang3.StringUtils;

import ai.idylnlp.model.nlp.subjects.CoNLL2003SubjectOfTrainingOrEvaluation;
import ai.idylnlp.model.nlp.subjects.IdylNLPSubjectOfTrainingOrEvaluation;
import ai.idylnlp.model.nlp.subjects.OpenNLPSubjectOfTrainingOrEvaluation;
import ai.idylnlp.model.nlp.subjects.SubjectOfTrainingOrEvaluation;
import ai.idylnlp.model.training.FMeasureModelValidationResult;
import ai.idylnlp.models.opennlp.training.EntityModelOperations;

/**
 * Runnable class for performing model validation using separate data.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class EntityModelSeparateDataModelValidator extends BaseValidator {

	public static void main(String args[]) throws Exception {
		
		EntityModelSeparateDataModelValidator validator = new EntityModelSeparateDataModelValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		if(result != null) {
			
			System.out.println("F-Measure: " + result.getFmeasure().toString());
			
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FMeasureModelValidationResult process(String[] args) throws Exception {
		
		output("\nIdyl E3 Model Separate Data Evaluator");
		output("Version: " + getVersion());
		
		CommandLine cmd;
		
		try {
			
			CommandLineParser parser = new DefaultParser();
			cmd = parser.parse(getOptions(), args);
		
		} catch(Exception ex) {
			
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Invalid usage.", getOptions());
			
			return null;
			
		}
						
		final String input = cmd.getOptionValue("i");		
		final String model = cmd.getOptionValue("m");	
		final String format = cmd.getOptionValue("f");
		final String annotations = cmd.getOptionValue("a");
				
		SubjectOfTrainingOrEvaluation subjectOfTraining;
		
		if(StringUtils.equalsIgnoreCase(format, "conll2003")) {
			subjectOfTraining = new CoNLL2003SubjectOfTrainingOrEvaluation(input);
		} else if(StringUtils.equalsIgnoreCase(format, "conll2003")) {
			subjectOfTraining = new IdylNLPSubjectOfTrainingOrEvaluation(input, annotations);
		} else {
			subjectOfTraining = new OpenNLPSubjectOfTrainingOrEvaluation(input);
		}
				
		// Separate data validation does not care about the algorithm or the feature generator because they are determined from the model.
		EntityModelOperations entityModelOperations = new EntityModelOperations(null, null);
		
		return entityModelOperations.separateDataEvaluate(subjectOfTraining, model);				
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Options getOptions() {
		
		Options options = new Options();
				
		Option inputFileOption = new Option("i", true, "The input training file, e.g. person.train");
		inputFileOption.setRequired(true);
		options.addOption(inputFileOption);
						
		Option modelOption = new Option("m", true, "The full path to the model to evaluate.");
		modelOption.setRequired(true);
		options.addOption(modelOption);
		
		Option encryptionKeyOption = new Option("e", true, "The model's encryption key.");
		encryptionKeyOption.setRequired(true);
		options.addOption(encryptionKeyOption);
		
		Option inputFileFormatOption = new Option("f", true, "The format of the input training file.");
		inputFileFormatOption.setRequired(true);
		options.addOption(inputFileFormatOption);
		
		Option annotationsOption = new Option("a", true, "The file containing the annotations.");
		annotationsOption.setRequired(false);
		options.addOption(annotationsOption);
		
		return options;
		
	}
	
}
