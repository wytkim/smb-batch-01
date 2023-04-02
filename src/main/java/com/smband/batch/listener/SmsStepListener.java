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
package com.smband.batch.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 31.
 * @version 
 * @since 
 */
@Slf4j
@Component
public class SmsStepListener implements StepExecutionListener {

	@Override
	public void beforeStep(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		log.info("## start step name: {}", stepName);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		log.info("## after step: {}", stepExecution.getSummary());
		
		return stepExecution.getExitStatus();
	}
}
