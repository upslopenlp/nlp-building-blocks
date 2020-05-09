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
package com.mtnfog.idyl.e3.stats;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import ai.idylnlp.model.entity.Entity;
import ai.idylnlp.model.manifest.ModelManifest;
import ai.idylnlp.model.stats.StatsReporter;

/**
 * Base class for statistic reporters.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public abstract class AbstractStatsReporter implements StatsReporter {
	
	protected MetricRegistry metrics;
	protected Map<String, Histogram> confidences;
	protected Map<String, Counter> counters;
	protected Map<String, Timer> timers;
	
	/**
	 * Initializes the required variables.
	 */
	public AbstractStatsReporter() {
		
		metrics = new MetricRegistry();
		confidences = new HashMap<String, Histogram>();
		counters = new HashMap<String, Counter>();
		timers = new HashMap<String, Timer>();
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void recordEntityExtraction(Entity entity, ModelManifest modelManifest) {
			
		final int intcon = (int) (entity.getConfidence() * 100);
		final String histogramName = makeMetricName(modelManifest);						
		
		if(confidences.get(histogramName) != null) {			
			
			confidences.get(histogramName).update(intcon);
			
		} else {
							
			Histogram histogram = metrics.histogram(histogramName);
			histogram.update(intcon);
			
			confidences.put(histogramName, histogram);
			
		}
				
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increment(String metricName) {
		
		increment(metricName, 1);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void increment(String metricName, long value) {
		
		Counter counter = counters.get(metricName);
		
		if(counter == null) {
			
			counter = metrics.counter(metricName);
			
		}
		
		counter.inc(value);
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void time(String metricName, long elapsedTime) {
		
		Timer timer = timers.get(metricName);
		
		if(timer == null) {
			
			timer = metrics.timer(metricName);
			
		}
		
		timer.update(elapsedTime, TimeUnit.MILLISECONDS);
		
	}
	
	private String makeMetricName(ModelManifest modelManifest) {
		
		return modelManifest.getLanguageCode() + "/" + modelManifest.getType() + "/" + modelManifest.getModelId();
		
	}
	
}
