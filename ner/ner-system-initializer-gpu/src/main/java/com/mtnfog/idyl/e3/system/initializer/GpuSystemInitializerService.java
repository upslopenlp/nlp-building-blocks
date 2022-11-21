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
package com.upslopenlp.idyl.e3.system.initializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nd4j.jita.conf.CudaEnvironment;
import org.springframework.stereotype.Component;

import com.upslopenlp.idyl.e3.model.Backend;
import com.upslopenlp.idyl.e3.model.services.SystemInitializerService;

/**
 * Initializes a GPU system.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class GpuSystemInitializerService implements SystemInitializerService {

	private static final Logger LOGGER = LogManager.getLogger(GpuSystemInitializerService.class);
	
	@Override
	public void initialize() {
		
		int processors = getProcessors();
		
		LOGGER.info("Initializing GPU system with {} device(s).", processors);
		
		if(processors == 0) {
			
			LOGGER.warn("No GPU devices were detected.");
			
		} else {
				
			LOGGER.info("CUDA version: {}", CudaEnvironment.getInstance().getCurrentDeviceArchitecture());
		
			if(processors > 1) {
			
				LOGGER.info("Enabling multi-GPU use.");
				
				CudaEnvironment.getInstance().getConfiguration().allowMultiGPU(true);
				
			}
			
		}
		
	}

	@Override
	public int getProcessors() {
		
		return CudaEnvironment.getInstance().getConfiguration().getAvailableDevices().size();
		
	}
	
	@Override
	public Backend getBackend() {
		return Backend.GPU;
	}

}
