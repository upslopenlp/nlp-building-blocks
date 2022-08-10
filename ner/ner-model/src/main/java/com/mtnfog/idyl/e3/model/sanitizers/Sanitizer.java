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
package com.mtnfog.idyl.e3.model.sanitizers;

import java.util.Set;

import ai.idylnlp.model.entity.Entity;

/**
 * Sanitizes the entities in the input text.
 * 
 * @author Mountain Fog, Inc.
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
