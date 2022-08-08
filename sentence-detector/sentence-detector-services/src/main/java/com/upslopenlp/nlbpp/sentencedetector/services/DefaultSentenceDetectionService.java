/*******************************************************************************
 * Copyright 2018 Mountain Fog, Inc.
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
package com.upslopenlp.nlbpp.sentencedetector.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import com.upslopenlp.nlbpp.sentencedetector.model.services.SentenceDetectionService;

@Component
public class DefaultSentenceDetectionService implements SentenceDetectionService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultSentenceDetectionService.class);
	
	private final SentenceDetector sentenceDetector;
	
	public DefaultSentenceDetectionService() throws IOException {

		final String model = System.getenv("sentence.model");
		final InputStream in = new ClassPathResource(model).getInputStream();
		final SentenceModel sentenceModel = new SentenceModel(in);

		this.sentenceDetector = new SentenceDetectorME(sentenceModel);

		in.close();

	}

	@Override
	public String[] detectSentences(String text) {

		return sentenceDetector.sentDetect(text);

	}

}
