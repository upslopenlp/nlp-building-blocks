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

import com.upslopenlp.nlpbb.ner.model.Status;

/**
 * Defines status, health, and metrics functions.
 * 
 * @author UpslopeNLP
 *
 */
public interface StatusService {

	/**
	 * Gets the {@link Status} of the service.
	 * @return The {@link Status} of the service.
	 */
	public Status getStatus();
		
}