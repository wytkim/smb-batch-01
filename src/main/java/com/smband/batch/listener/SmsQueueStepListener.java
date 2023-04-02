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
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.annotation.OnProcessError;
import org.springframework.batch.core.annotation.OnReadError;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.stereotype.Component;

import com.smband.batch.model.SmsQueueData;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 30.
 * @version 
 * @since 
 */
@Slf4j
@Component
public class SmsQueueStepListener {

	@OnReadError
	public void onReadError(Exception e) {
		if(e instanceof FlatFileParseException){
        	FlatFileParseException ffpe = (FlatFileParseException) e;
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("An error occured while processing the " + 
            	ffpe.getLineNumber() +
                " line of the file.Below was the faulty " + 
                "input.\n");
            errorMessage.append(ffpe.getInput() + "\n");
            
            log.error(errorMessage.toString(), ffpe);            
        }else{
        	log.error("An error has occurred", e);            
        }
	}

	@OnProcessError
	public void onProcessError(SmsQueueData item, Exception e) {
		log.error("sms queue process exception data: {}", item);
		log.error("process error exception: {}", e.getClass().toString());
	}
	
	@OnWriteError
	public void onWriteError(Exception e, Chunk<? extends SmsQueueData> items) {
		log.error("write exception chunk size: {}", items.size());
		log.error("writer error exception: {}", e.getClass().toString());
	}
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		String stepName = stepExecution.getStepName();
		log.info("## start step name: {}", stepName);
	}
	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		log.info("## after step: {}", stepExecution.getSummary());
		
		return stepExecution.getExitStatus();
	}
}
