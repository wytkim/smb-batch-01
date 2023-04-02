/*
 * Copyright (c) 2015 SM band, Inc.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SM band
 * , Inc. You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SM band.
 *
 */
package com.smband.batch.exception;

import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 30.
 * @version 
 * @since 
 */
@RequiredArgsConstructor
public class StepException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String message;
	
	@Override
	public String getMessage() {
		return this.message;
	}
	
	
}
