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
package com.mtnfog.idyl.e3.model.metrics;

import java.util.List;

public interface MetricReporter {
	
	/**
	 * A measurement for entity extraction.
	 */
	public static final String MEASUREMENT_EXTRACTION = "Extraction";
	
	/**
	 * A measurement for text annotation.
	 */
	public static final String MEASUREMENT_ANNOTATION = "Annotation";
	
	/**
	 * A measurement for text sanitization.
	 */
	public static final String MEASUREMENT_SANTIZATION = "Sanitization";
	
	/**
	 * Report the metrics.
	 * @measurement The measurement.
	 * @param metrics A list of {@link Metric metrics}.
	 */
	public void report(String measurement, List<Metric> metrics);
	
	/**
	 * Report a metric.
	 * @param measurement The measurement.
	 * @param field The field.
	 * @param value The value.
	 * @param The metric's {@link Unit unit}.
	 */
	public void report(String measurement, String field, long value, Unit unit);
	
	/**
	 * Report the elapsed time for an event.
	 * @param measurement The measurement.
	 * @param field The field.
	 * @param startTime The start time in milliseconds of the event.
	 */
	public void reportElapsedTime(String measurement, String field, long startTime);
	
	/**
	 * Logs an entity extraction time.
	 * @param value An entity extraction time.
	 */
	public void logEntityExtractionTime(double entityExtractionTime);
	
	/**
	 * Gets the mean entity extraction time.
	 * @return The mean entity extraction time.
	 */
	public double getMeanEntityExtractionTime();
	
}
