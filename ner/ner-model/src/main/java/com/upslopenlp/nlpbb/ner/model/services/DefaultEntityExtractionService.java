/*******************************************************************************
 * Copyright 2022 UpslopeNLP
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
package com.upslopenlp.nlpbb.ner.model.services;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.neovisionaries.i18n.LanguageCode;
import com.upslopenlp.nlpbb.ner.model.Entity;

/**
 * Default implementation of {@link EntityExtractionService}.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class DefaultEntityExtractionService implements EntityExtractionService {

	private static final Logger LOGGER = LogManager.getLogger(DefaultEntityExtractionService.class);		

	@Autowired
	private Environment env;

	@Override
	public Set<Entity> extract(String[] text, String type, LanguageCode languageCode) {

		// TODO: Get the model for the language.

		final Set<Entity> entities = new HashSet<>();

		return entities;
				
	}

}
