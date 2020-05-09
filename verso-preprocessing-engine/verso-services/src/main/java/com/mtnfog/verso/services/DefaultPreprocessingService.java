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
package com.mtnfog.verso.services;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mtnfog.verso.model.PreprocessingRequest;
import com.mtnfog.verso.model.services.PreprocessingService;

import opennlp.tools.stemmer.Stemmer;
import opennlp.tools.stemmer.snowball.SnowballStemmer;

@Component
public class DefaultPreprocessingService implements PreprocessingService {
	
	private static final Logger LOGGER = LogManager.getLogger(DefaultPreprocessingService.class);

	private Map<String, String[]> stopwords;
	
	public DefaultPreprocessingService() {
		
		// https://gist.github.com/sebleier/554280
		final String[] englishStopwords = {"i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now"};
		
		stopwords = new HashMap<>();
		stopwords.put("eng", englishStopwords);
		
	}
	
	@Override
	public String preprocess(File file, PreprocessingRequest request) throws IOException {
		
		List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
		
		StringBuilder sb = new StringBuilder();
		
		for(String line : lines) {
			
			String processedLine = preprocess(line, request);
			sb.append(processedLine + "\n");
		
		}
		
		return sb.toString();
		
	}
	
	@Override
	public String preprocess(String text, PreprocessingRequest request) {
		
		LOGGER.debug("Preprocessing input text with request: {}", request.toString());
		
		if(request.getUppercase().equalsIgnoreCase("y")) {
			text = text.toUpperCase();
		}
		
		if(request.getLowercase().equalsIgnoreCase("y")) {
			text = text.toLowerCase();
		}
		
		if(request.getRemovePunctuation().equalsIgnoreCase("y")) {
			text = text.replaceAll("\\p{P}", "");
		}
		
		if(request.getRemoveWordsContainingDigits().equalsIgnoreCase("y")) {
			text = text.replaceAll("\\w*\\d\\w* *", "");
		}
		
		if(request.getRemoveDigits().equalsIgnoreCase("y")) {
			text = text.replaceAll("\\d","");
		}
		
		if(request.getRemoveStopWords().equalsIgnoreCase("y")) {
			
			// Remove common stopwords for the language.
			for(String stopword : stopwords.get(request.getLanguage())) {
				text = text.replaceAll("\\b" + stopword + "\\b", "");
			}
			
		}
		
		if(request.getCustomStopWords() != null && request.getCustomStopWords().length > 0) {
			
			// Remove custom stopwords received in the request.
			for(String stopword : request.getCustomStopWords()) {
				text = text.replaceAll("\\b" + stopword + "\\b", "");
			}
			
		}
		
		if(request.getMinWordLength() > 0) {
			text = text.replaceAll("\\b\\w{1," + request.getMinWordLength() + "}\\b\\s?", "");
		}
		
		if(request.getMaxWordLength() > 0) {
			text = text.replaceAll("\\b\\w{" + request.getMaxWordLength() + ",}\\b\\s?", "");
		}
		
		if(request.getStem().equalsIgnoreCase("y")) {
			
			// Stem each word in the text.
			
			Stemmer stemmer = null;
			
			if(request.getLanguage().equalsIgnoreCase("eng")) {
			
				stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.ENGLISH);
				
			} else if(request.getLanguage().equalsIgnoreCase("spa")) {
					
				stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.SPANISH);
				
			} else if(request.getLanguage().equalsIgnoreCase("fre")) {
				
				stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.FRENCH);
				
			} else if(request.getLanguage().equalsIgnoreCase("deu")) {
				
				stemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.GERMAN);				
				
			} else {
				
				throw new IllegalArgumentException("No stemmer available for language " + request.getLanguage());
				
			}
			
			List<String> stems = new LinkedList<>();
			
			for(String word : text.split(" ")) {
			
				String stemmed = stemmer.stem(word).toString();
				
				stems.add(stemmed);
				
			}
			
			text = String.join(" ", stems);
			
		}
		
		text = text.trim().replaceAll(" +", " ");
				
		return text;
		
	}

}
