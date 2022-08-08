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
package com.upslopenlp.nlpbb.languagedetector.services;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;

import com.upslopenlp.nlpbb.languagedetector.model.LanguageDetectionRequest;
import com.upslopenlp.nlpbb.languagedetector.model.api.LanguagesDetectionResponse;
import com.upslopenlp.nlpbb.languagedetector.model.exceptions.LanguageDetectionException;
import com.upslopenlp.nlpbb.languagedetector.model.services.LanguageDetectionService;

@Component
public class OpenNLPLanguageDetectionService implements LanguageDetectionService {
	
	private static final Logger LOGGER = LogManager.getLogger(OpenNLPLanguageDetectionService.class);
	
	private final LanguageDetector languageDetector;
	
	public OpenNLPLanguageDetectionService() throws IOException {
		
		final InputStream in = new ClassPathResource("langdetect-183.bin").getInputStream();

		final LanguageDetectorModel languageDetectorModel = new LanguageDetectorModel(in);
		this.languageDetector = new LanguageDetectorME(languageDetectorModel);

		in.close();
		
	}
	
	@Override
	public LanguagesDetectionResponse detect(LanguageDetectionRequest request) throws
			LanguageDetectionException {

		final Language[] languages = languageDetector.predictLanguages(request.getText());

		final LanguagesDetectionResponse languagesDetectionResponse = new LanguagesDetectionResponse(languages);

		return languagesDetectionResponse;

	}

	@Override
	public String[] languages() {

		final String[] languages = new String[]{"afr", "ara", "ast", "aze", "bak", "bel", "ben", "bos", "bre", "bul", "cat", "ceb", "ces", "che", "cmn", "cym", "dan", "deu", "ekk", "ell", "eng", "epo", "est", "eus", "fao", "fas", "fin", "fra", "fry", "gle", "glg", "gsw", "guj", "heb", "hin", "hrv", "hun", "hye", "ind", "isl", "ita", "jav", "jpn", "kan", "kat", "kaz", "kir", "kor", "lat", "lav", "lim", "lit", "ltz", "lvs", "mal", "mar", "min", "mkd", "mlt", "mon", "mri", "msa", "nan", "nds", "nep", "nld", "nno", "nob", "oci", "pan", "pes", "plt", "pnb", "pol", "por", "pus", "ron", "rus", "san", "sin", "slk", "slv", "som", "spa", "sqi", "srp", "sun", "swa", "swe", "tam", "tat", "tel", "tgk", "tgl", "tha", "tur", "ukr", "urd", "uzb", "vie", "vol", "war", "zul"};
		
		return languages;
		
	}

}
