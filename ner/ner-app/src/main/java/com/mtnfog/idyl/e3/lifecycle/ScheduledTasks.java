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
package com.mtnfog.idyl.e3.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ai.idylnlp.model.nlp.ConfidenceFilter;

@Component
public class ScheduledTasks {
	
	private static final Logger LOGGER = LogManager.getLogger(ScheduledTasks.class);
	
	@Autowired
	private ConfidenceFilter confidenceFilter;

	@Scheduled(fixedRate = 180000)
	public void serialize() throws Exception {
		
		if(confidenceFilter.isDirty()) {
		
			LOGGER.info("Executing scheduled serialization of confidence values.");
			
			confidenceFilter.serialize();
		
		}
		
	}
	
}
