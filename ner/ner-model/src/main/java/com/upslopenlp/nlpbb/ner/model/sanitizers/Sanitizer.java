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
package com.upslopenlp.nlpbb.ner.model.sanitizers;

import java.util.Set;

import com.upslopenlp.nlpbb.ner.model.Entity;

/**
 * Sanitizes the entities in the input text.
 * 
 * @author UpslopeNLP
 *
 */
public interface Sanitizer {

	/**
	 * Sanitizes the entities in the input text.
	 * @param text The text.
	 * @param entities The entities.
	 * @return The sanitized text.
	 */
	public String sanitize(String text, Set<Entity> entities);
	
}
