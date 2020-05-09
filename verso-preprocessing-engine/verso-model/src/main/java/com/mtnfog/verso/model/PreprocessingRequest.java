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
package com.mtnfog.verso.model;

public class PreprocessingRequest {
	
	private String lowercase = "n";
	private String uppercase = "n";
	private int minWordLength = Integer.MIN_VALUE;
	private int maxWordLength = Integer.MAX_VALUE;
	private String removeWordsContainingDigits = "n";
	private String removeDigits = "n";
	private String removePunctuation = "n";
	private String removeStopWords = "n";
	private String stem = "n";
	private String[] customStopWords = new String[] {};
	private String language = "eng";
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("lowercase = " + lowercase);
		sb.append("; uppercase = " + uppercase);
		sb.append("; min word length = " + minWordLength);
		sb.append("; max word length = " + maxWordLength);
		sb.append("; remove words containing digits = " + removeWordsContainingDigits);
		sb.append("; remove digits = " + removeDigits);
		sb.append("; remove punctuation = " + removePunctuation);
		sb.append("; stem = " + stem);
		sb.append("; remove stopwords = " + removeStopWords);
		sb.append("; language = " + language);
		
		return sb.toString();
		
	}

	public String getLowercase() {
		return lowercase;
	}

	public void setLowercase(String lowercase) {
		this.lowercase = lowercase;
	}

	public String getUppercase() {
		return uppercase;
	}

	public void setUppercase(String uppercase) {
		this.uppercase = uppercase;
	}

	public int getMinWordLength() {
		return minWordLength;
	}

	public void setMinWordLength(int minWordLength) {
		this.minWordLength = minWordLength;
	}

	public int getMaxWordLength() {
		return maxWordLength;
	}

	public void setMaxWordLength(int maxWordLength) {
		this.maxWordLength = maxWordLength;
	}

	public String getRemoveDigits() {
		return removeDigits;
	}

	public void setRemoveDigits(String removeDigits) {
		this.removeDigits = removeDigits;
	}

	public String getRemovePunctuation() {
		return removePunctuation;
	}

	public void setRemovePunctuation(String removePunctuation) {
		this.removePunctuation = removePunctuation;
	}

	public String getRemoveStopWords() {
		return removeStopWords;
	}

	public void setRemoveStopWords(String removeStopWords) {
		this.removeStopWords = removeStopWords;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String[] getCustomStopWords() {
		return customStopWords;
	}

	public void setCustomStopWords(String[] customStopWords) {
		this.customStopWords = customStopWords;
	}

	public String getRemoveWordsContainingDigits() {
		return removeWordsContainingDigits;
	}

	public void setRemoveWordsContainingDigits(String removeWordsContainingDigits) {
		this.removeWordsContainingDigits = removeWordsContainingDigits;
	}

	public String getStem() {
		return stem;
	}

	public void setStem(String stem) {
		this.stem = stem;
	}

}
