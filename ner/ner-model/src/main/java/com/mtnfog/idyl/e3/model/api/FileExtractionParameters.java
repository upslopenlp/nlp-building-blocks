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
package com.mtnfog.idyl.e3.model.api;

import com.mtnfog.idyl.e3.model.Constants;

/**
 * Parameters used when extracting entities from text
 * in an uploaded file.
 * 
 * @author Mountain Fog, Inc.
 *
 */
public class FileExtractionParameters {

	private int confidence = Constants.MINIMUM_CONFIDENCE_THRESHOLD_VALUE;
	private String context = Constants.DEFAULT_CONTEXT;
	private String category = Constants.DEFAULT_CATEGORY;
	private String documentId = Constants.DEFAULT_DOCUMENT_ID;
	private String language = null;
	private String type = null;
	private String sort = Constants.DEFAULT_SORT;
	private String[] metadataKeys;
	private String[] metadataValues;
	
	/**
	 * Creates new parameters with default values.
	 */
	public FileExtractionParameters() {
		
	}
	
	/**
	 * Creates new parameters.
	 * @param context The context.
	 * @param documentId The document ID.
	 */
	public FileExtractionParameters(String context, String documentId) {
		
		this.context = context;
		this.documentId = documentId;
		
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String[] getMetadataKeys() {
		return metadataKeys;
	}

	public void setMetadataKeys(String[] metadataKeys) {
		this.metadataKeys = metadataKeys;
	}

	public String[] getMetadataValues() {
		return metadataValues;
	}

	public void setMetadataValues(String[] metadataValues) {
		this.metadataValues = metadataValues;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
