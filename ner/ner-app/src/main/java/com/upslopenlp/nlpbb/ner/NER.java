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

import java.io.File;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mtnfog.idyl.e3.metrics.MetricUtils;
import com.mtnfog.idyl.e3.stats.ConsoleStatsReporter;
import com.mtnfog.idyl.e3.stats.GraphiteStatsReporter;

import ai.idylnlp.model.nlp.ConfidenceFilter;
import ai.idylnlp.model.stats.StatsReporter;
import ai.idylnlp.nlp.filters.confidence.HeuristicConfidenceFilter;
import ai.idylnlp.nlp.filters.confidence.SimpleConfidenceFilter;
import ai.idylnlp.nlp.filters.confidence.serializers.LocalConfidenceFilterSerializer;

/**
 * Runnable main application class for Idyl E3.
 * 
 * @author UpslopeNLP
 *
 */
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
	
	@Bean
	public StatsReporter getStatsReporter() {
		
		if(StringUtils.equalsIgnoreCase(env.getProperty("stats.graphite.enabled"), "true")) {
			
			final String host = env.getProperty("stats.graphite.host", "localhost");
			final String port = env.getProperty("stats.graphite.port", "2003");
			final String prefix = env.getProperty("stats.graphite.prefix", "ner");
			final String interval = env.getProperty("stats.graphite.interval", "60");
			
			return new GraphiteStatsReporter(
					host, 
					Integer.valueOf(port), 
					prefix, 
					Long.valueOf(interval));
			
		} else {
			
			final long interval = Long.valueOf(env.getProperty("stats.console.interval", "300"));
			
			return new ConsoleStatsReporter(interval);
			
		}
		
	}
	
	@Bean
	public ConfidenceFilter getConfidenceFilter() throws Exception {
		
		ConfidenceFilter filter = null;
		
		if(StringUtils.equalsIgnoreCase("confidence.heuristics", "true")) {
			
			LOGGER.info("Using heuristic confidence filtering.");
				
			double alpha = Double.valueOf(env.getProperty("confidence.heuristics.alpha", "0.05"));
			int minSampleSize = Integer.valueOf(env.getProperty("confidence.heuristics.minSampleSize", "50"));
			
			final File directory = new File("data");
			final File confidencesFile = new File(directory, env.getProperty("confidence.filename", "confidences.dat"));
			
			LOGGER.info("Confidence values will be serialized to {}", confidencesFile.getAbsolutePath());
			
			LocalConfidenceFilterSerializer serializer = new LocalConfidenceFilterSerializer(confidencesFile);

			filter = new HeuristicConfidenceFilter(serializer, minSampleSize, alpha);			
			
		} else {
			
			LOGGER.info("Using simple confidence filtering.");
			
			filter = new SimpleConfidenceFilter();
			
		}
		
		filter.deserialize();
		
		return filter;
		
	}
	
	@Bean
	public String getSystemId() {
		
		String systemId = env.getProperty("system.id");
		
		if(StringUtils.isEmpty(systemId)) {
			
			systemId = MetricUtils.getSystemId();
			
		}

		return systemId;
		
	}

}
