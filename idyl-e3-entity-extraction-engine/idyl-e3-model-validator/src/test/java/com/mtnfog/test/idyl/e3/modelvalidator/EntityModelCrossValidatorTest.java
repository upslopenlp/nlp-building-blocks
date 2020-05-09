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

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.Ignore;

import com.mtnfog.idyl.e3.modelvalidator.EntityModelCrossValidator;

import ai.idylnlp.model.training.FMeasureModelValidationResult;

public class EntityModelCrossValidatorTest {

	private static final String TRAINING_DATA_PATH = new File("src/test/resources/").getAbsolutePath();

	@Ignore("Maxent is no longer a supported algorithm")
	@Test
	public void process() throws Exception {

		final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir");

		final String DEFINITION_FILE = TRAINING_DATA_PATH + File.separator + "training-definition-maxent.xml";
		final String INPUT_FILE = TRAINING_DATA_PATH + File.separator + "person-train.txt";

		FileUtils.copyFile(new File(INPUT_FILE), new File(TEMP_DIRECTORY + File.separator + "person-train.txt"));

		final String[] args = new String[]{"-td", DEFINITION_FILE, "-f", "3"};

		EntityModelCrossValidator validator = new EntityModelCrossValidator();
		FMeasureModelValidationResult result = validator.process(args);

		assertNotNull(result);

	}

}
