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

import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.Chunk;
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
 * @create 2023. 3. 30.
 * @version 
 * @since 
 */
public class SmsChunkListener implements ItemReadListener<SmsQueueData>
			, ItemProcessListener<SmsQueueData, SmsQueueData>, ItemWriteListener<SmsQueueData> 
			, ChunkListener, StepExecutionListener{

	
	@Override
	public void beforeRead() {
		// TODO add method stub
		// ItemReadListener.super.beforeRead();
		
		// TODO must implement SmsChunkListener.beforeRead()
		throw new RuntimeException ("no implemented SmsChunkListener.beforeRead()!");
	}

	@Override
	public void afterRead(SmsQueueData item) {
		// TODO add method stub
		// ItemReadListener.super.afterRead(item);
		
		// TODO must implement SmsChunkListener.afterRead()
		throw new RuntimeException ("no implemented SmsChunkListener.afterRead()!");
	}

	@Override
	public void onReadError(Exception ex) {
		// TODO add method stub
		// ItemReadListener.super.onReadError(ex);
		
		// TODO must implement SmsItemReadListener.onReadError()
		throw new RuntimeException ("no implemented SmsItemReadListener.onReadError()!");
	}

	@Override
	public void onProcessError(SmsQueueData item, Exception e) {
		// TODO add method stub
		// ItemProcessListener.super.onProcessError(item, e);
		
		// TODO must implement SmsItemReadListener.onProcessError()
		throw new RuntimeException ("no implemented SmsItemReadListener.onProcessError()!");
	}

	@Override
	public void onWriteError(Exception exception, Chunk<? extends SmsQueueData> items) {
		// TODO add method stub
		// ItemWriteListener.super.onWriteError(exception, items);
		
		// TODO must implement SmsItemReadListener.onWriteError()
		throw new RuntimeException ("no implemented SmsItemReadListener.onWriteError()!");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO add method stub
		// return StepExecutionListener.super.afterStep(stepExecution);
		
		// TODO must implement SmsChunkListener.afterStep()
		throw new RuntimeException ("no implemented SmsChunkListener.afterStep()!");
	}

	

}
