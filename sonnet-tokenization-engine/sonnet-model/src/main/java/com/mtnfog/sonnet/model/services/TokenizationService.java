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
package com.mtnfog.sonnet.model.services;

public interface TokenizationService {
	
	/**
	 * Tokenize the text.
	 * @param text The text to tokenize.
	 * @param language The language code.
	 * @param stem Whether or not to stem the tokens. A value
	 * of <code>1</code> indicates that stemming will be performed.
	 * @param removeDuplicates Whether or not to remove duplicate tokens.
	 * A value of <code>1</code> indicates duplicate tokens will be removed.
	 * @return The tokens.
	 */
	String[] tokenize(String text, String language, int stem, int removeDuplicates);
	
}
