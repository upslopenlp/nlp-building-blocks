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
package com.upslopenlp.nlpbb.tokenizer.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

import com.upslopenlp.nlpbb.tokenizer.model.services.TokenizationService;

@Component
public class DefaultTokenizationService implements TokenizationService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultTokenizationService.class);

	private final Tokenizer tokenizer;

	public DefaultTokenizationService() {

		try (final InputStream is = new FileInputStream("")) {

			final TokenizerModel tokenizerModel = new TokenizerModel(is);
			this.tokenizer = new TokenizerME(tokenizerModel);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public String[] tokenize(String text) {

		return tokenizer.tokenize(text);
		
	}
	
}
