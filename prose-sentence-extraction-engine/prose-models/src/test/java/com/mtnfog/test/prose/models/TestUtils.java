/*******************************************************************************
 * Copyright 2018 Mountain Fog, Inc.
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
package com.mtnfog.test.prose.models;

import java.io.File;
import java.io.IOException;

public class TestUtils {

	/**
	 * Creates a temporary file that is deleted upon exit.
	 * @param prefix The file name's prefix.
	 * @param extension The file extension.
	 * @return A temporary {@link File file}.
	 * @throws IOException Thrown if the temporary file cannot be generated.
	 */
	public static File getRandomFile(String prefix, String extension) throws IOException {
		
		File tempFile = File.createTempFile(prefix, "." + extension);
		tempFile.deleteOnExit();
		
		return tempFile;
		
	}
	
	public static File getRandomModelFile() throws IOException {
		
		File tempFile = File.createTempFile("model", ".bin");
		tempFile.deleteOnExit();
		
		return tempFile;
		
	}
	
}
