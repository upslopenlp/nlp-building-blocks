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

import com.mtnfog.idyl.e3.model.api.IdylE3EntityExtractionResponse;

/**
 * Provides entity extraction capabilities.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public interface EntityExtractionService {

	/**
	 * Extracts entities from the text.
	 * @param text The text.
	 * @param confidence The confidence threshold.
	 * @param context The context.
	 * @param documentId The document ID.
	 * @param language The language of the source document, or <code>auto</code> if unknown.
	 * @param type The type of entity to extract.
	 * @param sort How to sort the returned entities.
	 * @return An {@link IdylE3EntityExtractionResponse}.
	 */
	IdylE3EntityExtractionResponse extract(String[] text, int confidence,
			String context, String documentId, String language, String type, String sort);
				
}
