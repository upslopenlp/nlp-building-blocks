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
package com.upslopenlp.nlpbb.ner.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.upslopenlp.nlpbb.ner.model.Status;
import com.upslopenlp.nlpbb.ner.model.exceptions.ServiceUnavailableException;
import com.upslopenlp.nlpbb.ner.model.metrics.MetricReporter;
import com.upslopenlp.nlpbb.ner.model.services.StatusService;

/**
 * Default implementation of {@link StatusService}.
 * 
 * @author UpslopeNLP
 *
 */
@Component
public class DefaultStatusService implements StatusService {

	private static final Logger LOGGER = LogManager.getLogger(DefaultStatusService.class);

	@Override
	public Status getStatus() {

		final Status status = new Status();

		status.setStatus("Healthy");

		return status;

	}
		
}
