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
package com.mtnfog.renku;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.mtnfog.renku.model.LanguageDetectionRequest;
import com.mtnfog.renku.model.services.LanguageDetectionService;
import com.mtnfog.renku.services.OpenNLPLanguageDetectionService;

import ai.idylnlp.model.nlp.language.LanguageDetectionException;

public class RenkuLambdaFunction implements RequestHandler<String, String> {
	
	private LanguageDetectionService service;
	private Gson gson;
	
	public RenkuLambdaFunction() throws IOException {
		
		service = new OpenNLPLanguageDetectionService();
		gson = new Gson();
		
	}
	
	@Override
    public String handleRequest(String input, Context context) {
        
		try {
		
			List<Pair<String, Double>> languages = service.detect(new LanguageDetectionRequest(input, 5)).getLanguages();
		
			return gson.toJson(languages);
			
		} catch (LanguageDetectionException ex) {
			
			return gson.toJson("Unable to detect languages.");
			
		}
		
    }
    
}
	