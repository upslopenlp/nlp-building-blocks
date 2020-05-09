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
package com.mtnfog.test.sonnet.services;

import java.text.BreakIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.neovisionaries.i18n.LanguageCode;

public class DefaultTokenizationServiceTest {

	@Test
	@Ignore("Used to get supported languages")
	public void locales() {
		
		Locale[] locales = BreakIterator.getAvailableLocales();
		
		Map<String, String> languages = new TreeMap<>();
		
		for(Locale l : locales) {
		
			//System.out.println(l.getLanguage() + ", " + l.getDisplayLanguage());
			
			if(StringUtils.isNotEmpty(l.getLanguage())) {
				
				LanguageCode code = LanguageCode.getByLocale(l);
				
				languages.put(code.getAlpha3().toString(), code.getName());
				
			}
			
		}
		
		for(String l : languages.keySet()) {
			
			System.out.println(l + "," + languages.get(l));
			
		}
		
	}
	
}
