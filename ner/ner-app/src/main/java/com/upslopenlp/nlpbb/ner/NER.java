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
package com.upslopenlp.nlpbb.ner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
		MultipartAutoConfiguration.class,
		JacksonAutoConfiguration.class, 
		MongoAutoConfiguration.class,
		MongoDataAutoConfiguration.class, 
		SecurityAutoConfiguration.class
})
@Configuration
@EnableScheduling
@Order(Ordered.HIGHEST_PRECEDENCE)
public class NER extends SpringBootServletInitializer {
	
	private static final Logger LOGGER = LogManager.getLogger(NER.class);
	
	@Autowired
	private Environment env;
	
	public static void main(String[] args) throws Exception {
						
		LOGGER.info("Starting Idyl E3.");
		
		SpringApplication.run(NER.class, args);
		
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
				
		return application.sources(NER.class);
		
	}

}
