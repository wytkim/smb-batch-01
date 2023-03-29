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
package com.smband.batch.processor;

import java.util.Random;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.smband.batch.model.SmsQueueData;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 29.
 * @version 
 * @since 
 */
@Slf4j
@Component("smsQueueProcessor")
public class SmsQueueProcessor implements ItemProcessor<SmsQueueData, SmsQueueData> {
	private ExecutionContext jobContext;
	private Random random = new Random(System.currentTimeMillis());
	@Override
	public SmsQueueData process(SmsQueueData item) throws Exception {
		//log.info("before processor item: {}", item);
		
		item.setStatus(random.nextBoolean()?"S":"F");
		
		if("F".equals(item.getStatus())) {
			
			String ids = jobContext.containsKey("queueIds")?jobContext.getString("queueIds"):null;
			if(ids==null) ids = ""+item.getQueueId();
			else ids = ids + ","+ item.getQueueId();
			
			jobContext.put("queueIds", ids);
		}
		
		return item;
	}

	@BeforeStep
	private void setStepExecution(StepExecution stepExecution) {
		log.info("Sms Queue Processor before step!");
		 this.jobContext = stepExecution.getJobExecution().getExecutionContext();
	}
}
