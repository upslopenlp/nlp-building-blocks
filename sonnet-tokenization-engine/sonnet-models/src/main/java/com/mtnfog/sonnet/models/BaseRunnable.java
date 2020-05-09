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
package com.mtnfog.sonnet.models;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.Options;
import org.apache.commons.io.FileUtils;

import ai.idylnlp.models.opennlp.training.model.TrainingAlgorithm;

/**
 * Base class for runnable model generators.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public abstract class BaseRunnable {
	
	protected static final String TRAINING_DEFINITION_ARGUMENT_NAME = "td";

	/**
	 * The default number of iterations.
	 */
	protected static final int DEFAULT_ITERATIONS = 100;
	
	/**
	 * The default cutoff.
	 */
	protected static final int DEFAULT_CUTOFF = 5;
	
	/**
	 * The default number of threads.
	 */
	protected static final int DEFAULT_THREADS = 1;
		
	/**
	 * The default algorithm.
	 */
	protected static final TrainingAlgorithm DEFAULT_ALGORITHM = TrainingAlgorithm.PERCEPTRON;
	
	/**
	 * The default number of folds for cross validation.
	 */
	protected static final int DEFAULT_FOLDS = 10;
	
	/**
	 * Gets the command line {@link Options options}.
	 * @return The {@link Options options}.
	 */
	public abstract Options getOptions();
	
	/**
	 * Outputs a message to standard out.
	 * @param message The message.
	 */
	protected static void output(String message) {
		System.out.println(message);
	}
	
	/**
	 * Gets the version of the currently executing jar.
	 * @return
	 */
	protected String getVersion() {		
		return BaseRunnable.class.getPackage().getImplementationVersion();		
	}
	
	/**
	 * Reads the feature generator description from the file system.
	 * @param featureGeneratorXmlFile The full path to the feature generator XML file.
	 * @return The contents of the XML file.
	 * @throws IOException Thrown if the file does not exist.
	 */
	protected String getFeatureGeneratorXml(String featureGeneratorXmlFile) throws IOException {
		
		File file = new File(featureGeneratorXmlFile);
		if(!file.exists()) {
			throw new IOException("Feature generator XML file " + featureGeneratorXmlFile + " does not exist.");
		}
		
		return FileUtils.readFileToString(file);
		
	}
	
}
