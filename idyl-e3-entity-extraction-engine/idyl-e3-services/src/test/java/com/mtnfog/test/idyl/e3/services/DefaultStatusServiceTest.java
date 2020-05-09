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
package com.mtnfog.test.idyl.e3.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.mtnfog.idyl.e3.model.Status;
import com.mtnfog.idyl.e3.services.DefaultInitializationService;
import com.mtnfog.idyl.e3.services.DefaultStatusService;
import com.mtnfog.test.TestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=TestContext.class, loader=AnnotationConfigContextLoader.class)
public class DefaultStatusServiceTest {

	@Autowired
	private DefaultStatusService statusService;

	@Autowired
	private DefaultInitializationService initializer;
		
	@Before
	public void before() throws InterruptedException {

		while (!initializer.isLoaded()) {
			Thread.sleep(1000);
		}

	}

	@Test
	public void status() {

		Status status = statusService.getStatus();

		assertEquals("Healthy", status.getStatus());
		assertFalse(Double.isNaN(status.getMeanExtractionTime()));

	}

}
