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
package com.mtnfog.test.idyl.e3.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.mtnfog.idyl.e3.model.Status;

public class StatusTest {

	private static final Logger LOGGER = LogManager.getLogger(StatusTest.class);
	
	@Test
	public void serializeTest() {
		
		Status status = new Status("Healthy", 5.0);
		
		Gson gson = new Gson();
		String json = gson.toJson(status);
		
		LOGGER.info(json);
		
	}
	
	@Test
	public void doubleNaNTest() {
		
		double time = Double.NaN;
		
		Status status = new Status("Healthy", time);
		
		Gson gson = new Gson();
		String json = gson.toJson(status);
		
		LOGGER.info(json);
		
	}
	
}
