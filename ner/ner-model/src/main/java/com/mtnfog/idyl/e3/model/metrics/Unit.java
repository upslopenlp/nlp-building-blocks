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

/**
 * A unit for a {@link Metric metric}.
 * 
 * @author UpslopeNLP
 *
 */
public enum Unit {

	/**
	 * A measure of time in milliseconds.
	 */
	MILLISECONDS("ms"),
	
	/**
	 * A measure by count.
	 */
	COUNT("count");
	
	private String unit;
	
	private Unit(String unit) {
		this.unit = unit;
	}
	
	/**
	 * Gets the name of the unit.
	 * @return The name of the unit.
	 */
	public String getUnit() {
		return unit;
	}
	
}
