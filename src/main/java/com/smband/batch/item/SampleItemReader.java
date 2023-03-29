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

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import com.smband.batch.model.SmsQueueData;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 29.
 * @version 
 * @since 
 */
public class SampleItemReader implements ItemReader<SmsQueueData>{

	@Override
	public SmsQueueData read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO add method stub
		// return null;
		
		// TODO must implement ItemReader<SmsQueueData>.read()
		throw new RuntimeException ("no implemented ItemReader<SmsQueueData>.read()!");
	}
	
	
}
