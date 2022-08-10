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
package com.mtnfog.idyl.e3.model.metrics;

/**
* A metric of the system.
* 
* @author Mountain Fog, Inc.
*
*/
public class Metric {

	private String name;
	private long value;
	private Unit unit;
	
	/**
	 * Creates a new metric.
	 * @param name The name of the metric.
	 * @param value The value of the metric.
	 * @param unit The {@link Unit unit} of the metric.
	 */
	public Metric(String name, long value, Unit unit) {
		
		this.name = name;
		this.value = value;
		this.unit = unit;
		
	}
	
	/**
	 * Gets the metric's name.
	 * @return The metric's name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the metric's value.
	 * @return The metric's value.
	 */
	public long getValue() {
		return value;
	}
	
	/**
	 * Gets the metric's {@link Unit unit}.
	 * @return The metric's {@link Unit unit}.
	 */
	public Unit getUnit() {
		return unit;
	}
	
}
