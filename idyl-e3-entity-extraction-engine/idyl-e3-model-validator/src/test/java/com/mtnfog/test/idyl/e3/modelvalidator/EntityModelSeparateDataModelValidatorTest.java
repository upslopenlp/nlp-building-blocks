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
package com.mtnfog.test.idyl.e3.modelvalidator;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.mtnfog.idyl.e3.modelvalidator.EntityModelSeparateDataModelValidator;
import ai.idylnlp.model.training.FMeasureModelValidationResult;

@Ignore("Needs new models")
public class EntityModelSeparateDataModelValidatorTest {
	
	private static final Logger LOGGER = LogManager.getLogger(EntityModelSeparateDataModelValidatorTest.class);
	
	private static final String TRAINING_DATA_PATH = new File("src/test/resources/").getAbsolutePath();

	@Test
	public void validateCoNLL2003TestA() throws Exception {
		
		String[] args = {"-i", TRAINING_DATA_PATH + "/conll2003-eng.testa", "-f", "conll2003", "-m", TRAINING_DATA_PATH + "/mtnfog-en-person-test.bin", "-e", "idylami589012347"};
		
		EntityModelSeparateDataModelValidator validator = new EntityModelSeparateDataModelValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		LOGGER.info(result.getFmeasure().toString());
		
	}
	
	@Test
	public void validateCoNLL2003TestB() throws Exception {
		
		String[] args = {"-i", TRAINING_DATA_PATH + "/conll2003-eng.testb", "-f", "conll2003", "-m", TRAINING_DATA_PATH + "/mtnfog-en-person-test.bin", "-e", "idylami589012347"};
		
		EntityModelSeparateDataModelValidator validator = new EntityModelSeparateDataModelValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		LOGGER.info(result.getFmeasure().toString());
		
	}
	
	@Test
	public void validateOpenNLP() throws Exception {
		
		String[] args = {"-i", TRAINING_DATA_PATH + "/person-train.txt", "-f", "opennlp", "-m", TRAINING_DATA_PATH + "/mtnfog-en-person-test.bin", "-e", "idylami589012347"};
		
		EntityModelSeparateDataModelValidator validator = new EntityModelSeparateDataModelValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		LOGGER.info(result.getFmeasure().toString());
		
	}
	
	@Test
	public void validateIdylNLP() throws Exception {
		
		String[] args = {"-i", TRAINING_DATA_PATH + "/person-train.txt", "-f", "idylnlp", "-a", TRAINING_DATA_PATH + "/train-person-idyl-format-annotations.txt", "-m", TRAINING_DATA_PATH + "/mtnfog-en-person-test.bin", "-e", "idylami589012347"};
		
		EntityModelSeparateDataModelValidator validator = new EntityModelSeparateDataModelValidator();
		FMeasureModelValidationResult result = validator.process(args);
		
		LOGGER.info(result.getFmeasure().toString());
		
	}
	
}
