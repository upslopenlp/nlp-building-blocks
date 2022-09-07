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
package com.upslopenlp.nlpbb.tokenizer.services.utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TokenUtils {
	
	private static final Logger LOGGER = LogManager.getLogger(TokenUtils.class);

	public static String[] removeDuplicateTokens(String tokens[]) {
		
		LOGGER.trace("Removing duplicate tokens.");
		
		// Remove duplicate tokens.
		List<String> withDuplicates = new LinkedList<String>(Arrays.asList(tokens));
		List<String> withoutDuplicates = withDuplicates.stream()
			     .distinct()
			     .collect(Collectors.toList());
		tokens = withoutDuplicates.stream().toArray(String[]::new);
		
		
		return tokens;
		
	}
	
}
