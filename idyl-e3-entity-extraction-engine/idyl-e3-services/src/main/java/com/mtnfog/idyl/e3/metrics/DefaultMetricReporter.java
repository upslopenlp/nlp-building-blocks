/*******************************************************************************
 * Copyright 2019 Mountain Fog, Inc.
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
package com.mtnfog.idyl.e3.metrics;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.mtnfog.idyl.e3.model.metrics.Metric;
import com.mtnfog.idyl.e3.model.metrics.MetricReporter;
import com.mtnfog.idyl.e3.model.metrics.Unit;

/**
 * Implementation of {@link MetricReporter} that outputs the metrics
 * to the enabled loggers.
 * 
 * @author Mountain Fog, Inc.
 *
 */
@Component
public class DefaultMetricReporter extends AbstractMetricReporter implements MetricReporter {

	private static final Logger LOGGER = LogManager.getLogger(DefaultMetricReporter.class);

	@Override
	public void report(String measurement, List<Metric> metrics) {

		/*for(MetricsPlugin metricsPlugin : pluginConfiguration.getMetricsPlugins()) {
			
			metricsPlugin.report(measurement, metrics);
			
		}*/
				
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void report(String measurement, String field, long value, Unit unit) {
		
		/*for(MetricsPlugin metricsPlugin : pluginConfiguration.getMetricsPlugins()) {
			
			metricsPlugin.report(measurement, field, value, unit);
			
		}*/
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reportElapsedTime(String measurement, String field, long startTime) {
		
		report(measurement, field, System.currentTimeMillis() - startTime, Unit.MILLISECONDS);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void logEntityExtractionTime(double entityExtractionTime) {
		
		LOGGER.trace("Logging entity extraction time: {}", entityExtractionTime);
		
		stats.addValue(entityExtractionTime);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getMeanEntityExtractionTime() {
		
		return stats.getMean();
		
	}
	
}
