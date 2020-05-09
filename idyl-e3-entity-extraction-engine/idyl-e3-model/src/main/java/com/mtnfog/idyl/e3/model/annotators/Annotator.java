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
package com.mtnfog.idyl.e3.model.annotators;

import org.apache.commons.collections4.multimap.UnmodifiableMultiValuedMap;

import ai.idylnlp.model.entity.Entity;

/**
 * Annotate the input text with the entities.
 * 
 * @author Mountain Fog, Inc.
 *
 */
@FunctionalInterface
public interface Annotator {

	/**
	 * Annotates the text with the entities.
	 * @param text The text.
	 * @param entityStarts A map of entity start locations to entity.
	 * @param entityEnds A map of entity end locations to entity.
	 * @return The annotated text.
	 */
	public String annotate(String text, UnmodifiableMultiValuedMap<Integer, Entity> entityStarts, UnmodifiableMultiValuedMap<Integer, Entity> entityEnds);
	
}
