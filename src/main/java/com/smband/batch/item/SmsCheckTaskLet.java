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
package com.smband.batch.item;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.smband.batch.mapper.SampleMapper;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 29.
 * @version 
 * @since 
 */
@Component("smsCheckTaskLet")
public class SmsCheckTaskLet implements Tasklet {
	@Autowired
	private SampleMapper sampleMapper;
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		int cnt = this.sampleMapper.waitSmsCount();
		ExecutionContext jobContext = contribution.getStepExecution().getJobExecution().getExecutionContext();
		jobContext.put(SmsStepDecider.WAIT_STATUS, cnt>0?SmsStepDecider.EXIST_DATA:SmsStepDecider.NO_DATA);
		
		return RepeatStatus.FINISHED;
	}
}
