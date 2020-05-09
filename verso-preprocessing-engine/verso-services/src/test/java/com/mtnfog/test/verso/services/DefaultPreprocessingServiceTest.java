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
package com.mtnfog.test.verso.services;

import static org.junit.Assert.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mtnfog.verso.model.PreprocessingRequest;
import com.mtnfog.verso.services.DefaultPreprocessingService;

public class DefaultPreprocessingServiceTest {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultPreprocessingServiceTest.class);

	@Test
	public void test1() {
		
		DefaultPreprocessingService service = new DefaultPreprocessingService();
		
		PreprocessingRequest request = new PreprocessingRequest();
		request.setLowercase("y");
		request.setUppercase("n");
		request.setRemoveWordsContainingDigits("n");
		request.setRemovePunctuation("y");
		request.setMinWordLength(2);
		request.setMaxWordLength(10);
		request.setRemoveDigits("y");
		request.setRemoveStopWords("y");
		request.setLanguage("eng");
		
		String processed = service.preprocess("George Washington was 1 president.", request);
		
		assertEquals("george president", processed);
		
	}
	
}
