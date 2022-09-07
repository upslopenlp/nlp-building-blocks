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
package com.mtnfog.idyl.e3.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Various Idyl E3 constants.
 * 
 * @author UpslopeNLP
 *
 */
public class Constants { 
	
	private Constants() {
		// Utility class.
	}
				
	/**
	 * The minimum acceptable value for the confidence threshold.
	 */
	public static final int MINIMUM_CONFIDENCE_THRESHOLD_VALUE = 0;
	
	/**
	 * The maximum acceptable value for the confidence threshold.
	 */
	public static final int MAXIMUM_CONFIDENCE_THRESHOLD_VALUE = 100;
			
	/**
	 * The default operation for text requests.
	 */
	public static final String DEFAULT_OPERATION = "annotate";
		
	/**
	 * The OpenNLP annotation format.
	 */
	public static final String ANNOTATION_FORMAT_OPENNLP = "opennlp";
	
	/**
	 * The default annotation format.
	 */
	public static final String DEFAULT_ANNOTATION_FORMAT = ANNOTATION_FORMAT_OPENNLP;
	
	/**
	 * The default confidence threshold value if none is provided.
	 */
	public static final String DEFAULT_CONFIDENCE_THRESHOLD = "0";
	
	/**
	 * The default value for the context if none is provided.
	 */
	public static final String DEFAULT_CONTEXT = "not-set";
	
	/**
	 * The default value for the document ID if none is provided.
	 */
	public static final String DEFAULT_DOCUMENT_ID = "not-set";
	
	/**
	 * The default value for the entity sort order if none is provided.
	 */
	public static final String DEFAULT_SORT = "confidence";
	
	/**
	 * The default value for the category if none is provided.
	 */
	public static final String DEFAULT_CATEGORY = StringUtils.EMPTY;
							
}
