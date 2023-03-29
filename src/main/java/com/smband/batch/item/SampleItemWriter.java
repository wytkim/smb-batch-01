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

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

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
public class SampleItemWriter implements ItemWriter<SmsQueueData> {

	@Override
	public void write(Chunk<? extends SmsQueueData> chunk) throws Exception {
		log.info("writer chunk size: {}", chunk.size());
		chunk.forEach(data->{
			log.info("writer data: {}", data);
		});
	}

}
