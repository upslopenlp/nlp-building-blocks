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
package com.upslopenlp.nlpbb.tokenizer.api.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.upslopenlp.nlpbb.tokenizer.model.exceptions.BadRequestException;
import com.upslopenlp.nlpbb.tokenizer.model.exceptions.InternalServerErrorException;

@ControllerAdvice
public class RestApiExceptions {

	private static final Logger LOGGER = LogManager.getLogger(RestApiExceptions.class);

	@ResponseBody
	@ExceptionHandler({BadRequestException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleBadRequestException(Exception ex) {
		return ex.getMessage();
	}
	
	@ResponseBody
	@ExceptionHandler({MissingServletRequestParameterException.class, HttpMessageNotReadableException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public String handleMissingParameterException(Exception ex) {
		final String message = "A required parameter is missing or contains an invalid value.";
		LOGGER.error(message, ex);
		return message;
	}
	
	@ResponseBody
	@ExceptionHandler({InternalServerErrorException.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleInternalServerErrorException(InternalServerErrorException ex) {
	    return ex.getMessage();
	}
		
	@ResponseBody
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleUnknownException(Exception ex) {
		LOGGER.error("Unknown exception caught.", ex);
	    return "An unknown error has occurred.";
	}
	
}
