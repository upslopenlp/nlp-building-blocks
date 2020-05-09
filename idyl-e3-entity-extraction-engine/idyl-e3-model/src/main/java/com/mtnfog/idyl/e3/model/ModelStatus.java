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
package com.mtnfog.idyl.e3.model;

/**
 * Describes that status of a model.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public enum ModelStatus {

	/**
	 * The model is available but not loaded.
	 */
	AVAILABLE("Available"),
	
	/**
	 * The model is loaded.
	 */
	LOADED("Loaded"),
	
	/**
	 * The model could not be loaded and
	 * is not available.
	 */
	ERROR("Error");
	
	private String status;
	
	private ModelStatus(String status) {
		
		this.status = status;
		
	}
	
	/**
	 * Gets the status of the model.
	 * @return The status of the model.
	 */
	public String getStatus() {
		
		return status;
		
	}
	
	/**
	 * Gets the status of the model.
	 * @return The status of the model.
	 */
	@Override
	public String toString() {
		
		return status;
		
	}
	
}
