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
package com.mtnfog.test.idyl.e3.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.gson.Gson;
import ai.idylnlp.model.entity.Entity;
import com.mtnfog.idyl.e3.model.Constants;
import com.mtnfog.idyl.e3.model.api.IdylE3EntityExtractionResponse;
import com.mtnfog.idyl.e3.model.exceptions.BadRequestException;
import com.mtnfog.idyl.e3.model.services.EntityExtractionService;
import com.mtnfog.idyl.e3.services.DefaultInitializationService;
import com.mtnfog.test.TestContext;
import com.neovisionaries.i18n.LanguageCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestContext.class, loader=AnnotationConfigContextLoader.class)
public class DefaultEntityExtractionServiceTest {

	private static final Logger LOGGER = LogManager.getLogger(DefaultEntityExtractionServiceTest.class);	
	
	@Autowired
	private EntityExtractionService entityExtractionService;

	@Autowired
	private DefaultInitializationService initializer;
	
	@Before
	public void before() throws InterruptedException {

		while (!initializer.isLoaded()) {
			Thread.sleep(1000);
		}

	}
	
	@Test
	@Ignore("Requires a model")
	public void extract() {

		String[] text = "George Washington was president.".split(" ");
		int confidence = 0;
		String context = "context";
		String documentId = "documentId";
		String language = LanguageCode.en.getAlpha3().toString();
		String type = null;
		
		IdylE3EntityExtractionResponse response = entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

		for(Entity entity : response.getEntities()) {
			LOGGER.info("Extracted entity: {}", entity.toString());
		}
		
		// 2 entities - one from each entity recognizer - but the duplicate strategy causes only one to be returned.
		assertEquals(1, response.getEntities().size());

	}
	
	@Test
	@Ignore("Requires a model")
	public void extractWithNullLanguage() {

		String[] text = "George Washington was president.".split(" ");
		int confidence = 0;
		String context = "context";
		String documentId = "documentId";
		String type = null;
		
		// Null language means use all languages.
		String language = null;

		IdylE3EntityExtractionResponse response = entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

		for(Entity entity : response.getEntities()) {
			LOGGER.info("Extracted entity: {}", entity.toString());
		}
		
		// 2 entities - one from each entity recognizer.
		assertTrue(response.getEntities().size() > 0);

	}
	
	@Test(expected = BadRequestException.class)
	public void extractWithInvalidLanguage() {

		String[] text = "George Washington was president.".split(" ");
		int confidence = 0;
		String context = "context";
		String documentId = "documentId";
		String language = "asdf";
		
		entityExtractionService.extract(text, confidence, context, documentId, language, "all", Constants.DEFAULT_SORT);

	}
	
	@Test
	@Ignore
	public void extractDictionary() {

		String[] text = "George Washington was president with tylenol.".split(" ");
		int confidence = 0;
		String context = "context";
		String documentId = "documentId";
		String language = LanguageCode.en.getAlpha3().toString();
		String type = null;
		
		IdylE3EntityExtractionResponse response = entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

		assertEquals(1, response.getEntities().size());
		
		Gson gson = new Gson();
		String entities = gson.toJson(response.getEntities());
		LOGGER.info(entities);

	}
	
	@Test(expected = BadRequestException.class)
	public void extractBadConfidenceNegative() {

		String[] text = "George Washington was president.".split(" ");
		int confidence = -1;
		String context = "context";
		String documentId = "documentId";
		String language = LanguageCode.en.getAlpha3().toString();
		String type = null;
		
		entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

	}

	@Test(expected = BadRequestException.class)
	public void extractBadConfidence() {

		String[] text = "George Washington was president.".split(" ");
		int confidence = 150;
		String context = "context";
		String documentId = "documentId";
		String language = LanguageCode.en.getAlpha3().toString();
		String type = null;
		
		entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

	}

	@Test(expected = BadRequestException.class)
	public void extractMissingText() {

		String[] text = {};
		int confidence = 0;
		String context = "context";
		String documentId = "documentId";
		String language = LanguageCode.en.getAlpha3().toString();
		String type = null;
		
		entityExtractionService.extract(text, confidence, context, documentId, language, type, Constants.DEFAULT_SORT);

	}

}
