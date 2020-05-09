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
package com.mtnfog.renku.model.services;

import ai.idylnlp.model.nlp.language.LanguageDetectionException;
import ai.idylnlp.model.nlp.language.LanguageDetectionResponse;
import com.mtnfog.renku.model.LanguageDetectionRequest;

public interface LanguageDetectionService {

	/**
	 * Detects the language of the input text.
	 * @param request A {@link LanguageDetectionRequest}. 
	 * @return A {@link LanguageDetectionResponse}.
	 * @throws LanguageDetectionException
	 */
	public LanguageDetectionResponse detect(LanguageDetectionRequest request) throws LanguageDetectionException;
	
	/**
	 * Gets the languages supported by the language model.
	 * @return The languages supported by the language model.
	 */
	public String[] languages();
	
}
