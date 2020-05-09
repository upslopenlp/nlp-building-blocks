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
package com.mtnfog.test.renku.api;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import com.google.gson.Gson;
import ai.idylnlp.model.nlp.language.LanguageDetectionResponse;

public class RenkuApiControllerTest {

	@Test
	public void response() {
		
		List<Pair<String, Double>> languages = new LinkedList<>();
		languages.add(new ImmutablePair<String, Double>("eng", 0.64));
		languages.add(new ImmutablePair<String, Double>("fre", 0.46));
		
		LanguageDetectionResponse response = new LanguageDetectionResponse(languages);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(languages));
		
	}
	
}
