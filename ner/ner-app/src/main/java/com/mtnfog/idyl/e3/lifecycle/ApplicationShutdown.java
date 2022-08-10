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
package com.mtnfog.idyl.e3.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import ai.idylnlp.model.nlp.ConfidenceFilter;

@Component
public class ApplicationShutdown implements ApplicationListener<ContextClosedEvent> {

	private static final Logger LOGGER = LogManager.getLogger(ApplicationShutdown.class);

	@Autowired
	private ConfidenceFilter confidenceFilter;
	
	@Override
	public void onApplicationEvent(final ContextClosedEvent event) {
		
		LOGGER.info("Idyl E3 is shutting down.");
		
		try {
			confidenceFilter.serialize();
		} catch (Exception ex) {
			LOGGER.error("Unable to serialize confidence values.", ex);
		}
		
	}
		
}
