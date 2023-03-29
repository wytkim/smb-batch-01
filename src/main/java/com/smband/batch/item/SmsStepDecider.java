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

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 29.
 * @version 
 * @since 
 */
public class SmsStepDecider implements JobExecutionDecider {
	/** 대기중 sms 존재 확인 */
	public static final String WAIT_STATUS 	= "WAIT_STATUS";
	/** 대기중 sms 없음 */
	public static final String NO_DATA		= "NO_DATA";
	/** 대기중 sms 존재함 */
	public static final String EXIST_DATA	= "EXIST_DATA";

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		String stepStatus = jobExecution.getExecutionContext().getString(WAIT_STATUS);
		FlowExecutionStatus flowExecutionStatus = EXIST_DATA.equals(stepStatus)? new FlowExecutionStatus(EXIST_DATA)
																				: new FlowExecutionStatus(NO_DATA);

		return flowExecutionStatus;
	}

}
