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
package com.mtnfog.idyl.e3.model.services;

import java.util.List;

import ai.idylnlp.model.nlp.ner.EntityRecognizer;

/**
 * Defines functions for initializing Idyl E3.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public interface InitializationService {

	/**
	 * Initializes Idyl E3 by creating the {@link IdylPipeline pipeline}.
	 */
	void initialize();
		
	/**
	 * Gets if model loading is complete.
	 * @return <code>true</code> if all models have been loaded;
	 * otherwise <code>false</code>.
	 */
	boolean isLoaded();
	
	/**
	 * Gets a list of {@link EntityRecognizer}.
	 * @return A list of {@link EntityRecognizer}.
	 */
	List<EntityRecognizer> getEntityRecognizers();
	
}
