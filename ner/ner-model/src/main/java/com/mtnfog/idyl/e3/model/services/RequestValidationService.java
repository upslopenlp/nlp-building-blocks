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

/**
 * Defines functions for validating API requests.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public interface RequestValidationService {

	/**
	 * Validate the entity extraction parameters.
	 * The <code>context</code> will be set to the default context if <code>null</code>.
	 * The <code>language</code> will be set to <code>en</code> if <code>null</code>.
	 * @param text The text to process.
	 * @param confidence The confidence (0-100).
	 * @param context The context.
	 * @param documentId The documentId.
	 * @param language The language.
	 * @param order The entity sort order.
	 */
	public void validate(String[] text, int confidence, String context, String language, String order);
	
}
