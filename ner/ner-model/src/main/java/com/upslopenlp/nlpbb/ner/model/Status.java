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
package com.upslopenlp.nlpbb.ner.model;

/**
 * Represents the status of the Idyl E3 instance.
 * 
 * @author UpslopeNLP
 *
 */
public class Status {

	private String status;
	private String version;

	public Status() {
		
		version = Status.class.getPackage().getImplementationVersion();
		
	}
	
	public Status(String status, double meanExtractionTime) {
		
		if(Double.isNaN(meanExtractionTime)) {
			meanExtractionTime = 0.0;
		}
		
		this.status = status;

		version = Status.class.getPackage().getImplementationVersion();
		
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}
	
}
