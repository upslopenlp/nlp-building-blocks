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
package com.mtnfog.idyl.e3.system.initializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mtnfog.idyl.e3.model.Backend;
import com.mtnfog.idyl.e3.model.services.SystemInitializerService;

/**
 * Initializes a CPU system.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class NativeSystemInitializerService implements SystemInitializerService {

	private static final Logger LOGGER = LogManager.getLogger(NativeSystemInitializerService.class);
	
	@Override
	public void initialize() {
		
		LOGGER.info("Initializing native system with {} logical thread(s).", getProcessors());
		
	}

	/**	
	 * {@inheritDoc}
	 * This will give you the number of logical threads. 
	 * If you have hyper-threading on, this will be double the number of cores.
	 */
	@Override
	public int getProcessors() {
		
		return Runtime.getRuntime().availableProcessors();
		
	}
	
	@Override
	public Backend getBackend() {
		return Backend.NATIVE;
	}

}
