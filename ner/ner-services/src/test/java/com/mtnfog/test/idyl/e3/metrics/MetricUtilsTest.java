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
package com.mtnfog.test.idyl.e3.metrics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.mtnfog.idyl.e3.metrics.MetricUtils;

public class MetricUtilsTest {
	
	private static final Logger LOGGER = LogManager.getLogger(MetricUtilsTest.class);

	@Test
	public void getSystemIdTest() {
		
		final String systemId = MetricUtils.getSystemId();
		
		assertNotNull(systemId);
		
		// Calling it twice should give back the same ID.
		
		final String systemId2 = MetricUtils.getSystemId();
		
		assertEquals(systemId, systemId2);
		
		LOGGER.info("Received system ID: {}", systemId);
		
	}
	
}
