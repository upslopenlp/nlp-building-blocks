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
package com.mtnfog.verso;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

import com.mtnfog.verso.model.PreprocessingRequest;
import com.mtnfog.verso.services.DefaultPreprocessingService;

public class CLIRunner {

	public static void main(String[] args) throws ParseException, IOException {

		Options options = new Options();
		options.addOption("f", true, "(Required) The file to preprocess.");
		options.addOption("lc", false, "Lowercase the text.");
		options.addOption("uc", false, "Uppercase the text.");
		options.addOption("minw", true, "Minimum word length.");
		options.addOption("maxw", true, "Maximum word length.");
		options.addOption("wcd", false, "Remove words containing digits.");
		options.addOption("d", false, "Remove digits.");
		options.addOption("p", false, "Remove punctuation.");
		options.addOption("stem", false, "Stem the words.");
		options.addOption("sw", false, "Remove stop words.");
		options.addOption("csw", true, "A file containing custom stop words.");		
		options.addOption("language", true, "The language of the input file. Defaults to eng.");
		
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		
		if(cmd.hasOption("f")) {
		
			final String fileName = cmd.getOptionValue("f");
			
			File file = new File(fileName);
			
			if(!file.exists()) {
				
				System.err.println("The input file does not exist.");
				return;
				
			}
			
			PreprocessingRequest request = new PreprocessingRequest();
			
			if(cmd.hasOption("lc")) {
				request.setLowercase("y");
			}
			
			if(cmd.hasOption("uc")) {
				request.setUppercase("y");
			}
			
			if(cmd.hasOption("minw")) {
				request.setMinWordLength(Integer.valueOf(cmd.getOptionValue("minw")));
			}
			
			if(cmd.hasOption("maxw")) {
				request.setMaxWordLength(Integer.valueOf(cmd.getOptionValue("maxw")));
			}
			
			if(cmd.hasOption("wcd")) {
				request.setRemoveWordsContainingDigits("y");
			}
			
			if(cmd.hasOption("d")) {
				request.setRemoveDigits("y");
			}
			
			if(cmd.hasOption("p")) {
				request.setRemovePunctuation("y");
			}
			
			if(cmd.hasOption("sw")) {
				request.setRemoveStopWords("y");
			}
			
			if(cmd.hasOption("stem")) {
				request.setStem("y");
			}
			
			if(cmd.hasOption("csw")) {
				
				File swFile = new File(cmd.getOptionValue("csw"));
				
				if(swFile.exists()) {
				
					List<String> s = FileUtils.readLines(swFile, Charset.defaultCharset());
					request.setCustomStopWords(s.toArray(new String[s.size()]));
				
				} else {
					
					System.err.println("The stop words file does not exist.");
					return;
					
				}
				
			}
			
			if(cmd.hasOption("language")) {
				request.setLanguage(cmd.getOptionValue("language"));
			} else {
				request.setLanguage("eng");
			}
			
			DefaultPreprocessingService preprocessingService = new DefaultPreprocessingService();
			String text = preprocessingService.preprocess(file, request);
			
			System.out.println(text);
			
		} else {
			
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("verso", options);

		}
		
	}

}
