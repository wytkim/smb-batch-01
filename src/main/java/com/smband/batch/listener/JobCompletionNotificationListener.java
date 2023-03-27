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

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.smband.batch.model.Person;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 27.
 * @version 
 * @since 
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

	private final JdbcTemplate jdbcTemplate;
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");
			
			jdbcTemplate.query("SELECT first_name, last_name FROM people", 
					(rs, row) -> new Person(rs.getString(1), rs.getString(2))
			).forEach(person -> log.info("Found <{{}}> in the database.", person));
		}
	}
	
}
