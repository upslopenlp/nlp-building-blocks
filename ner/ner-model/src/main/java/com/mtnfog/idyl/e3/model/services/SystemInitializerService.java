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
package com.mtnfog.idyl.e3.model.services;

import com.mtnfog.idyl.e3.model.Backend;

/**
 * Interface for initializing the low-level system.
 * 
 * @author UpslopeNLP
 *
 */
public interface SystemInitializerService {

	/**
	 * Initializes the low-level system.
	 */
	public void initialize();
	
	/**
	 * Gets the count of available processors.
	 * @return The count of available processors.
	 */
	public int getProcessors();
	
	/**
	 * Gets the {@link Backend}.
	 * @return The {@link Backend}.
	 */
	public Backend getBackend();
	
}
