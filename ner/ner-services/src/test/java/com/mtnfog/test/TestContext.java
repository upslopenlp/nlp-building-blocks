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
package com.mtnfog.test;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.mtnfog.idyl.e3.metrics.DefaultMetricReporter;
import com.mtnfog.idyl.e3.model.metrics.MetricReporter;
import com.mtnfog.idyl.e3.services.DefaultStatusService;
import com.mtnfog.idyl.e3.stats.ConsoleStatsReporter;
import com.mtnfog.test.TestValidator;
import ai.idylnlp.model.ModelValidator;
import ai.idylnlp.model.nlp.ConfidenceFilter;
import ai.idylnlp.model.stats.StatsReporter;
import ai.idylnlp.nlp.filters.confidence.HeuristicConfidenceFilter;
import ai.idylnlp.nlp.filters.confidence.serializers.LocalConfidenceFilterSerializer;

@Configuration
@ComponentScan("com.mtnfog.idyl.e3")
public class TestContext {
	
	@Bean
	public String systemId() {
		return UUID.randomUUID().toString();
	}
	
	@Bean
	public StatsReporter statsReporter() {
		return new ConsoleStatsReporter(30);
	}
	
	@Bean
	public ConfidenceFilter getConfidenceFilter() throws IOException {

		File confidencesFile = File.createTempFile("confidences", "dat");
		LocalConfidenceFilterSerializer serializer = new LocalConfidenceFilterSerializer(confidencesFile);
	
		return new HeuristicConfidenceFilter(serializer, 50, 0.05);
					
	}
	
	@Bean
	public DefaultStatusService statusService() {
		return new DefaultStatusService();
	}
	
	@Bean
	public ModelValidator Validator() {
		return new TestValidator(true);
	}
	
	@Bean
	public MetricReporter metricReporter() {
		return new DefaultMetricReporter();
	}
	
}
