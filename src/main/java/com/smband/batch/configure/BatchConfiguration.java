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
package com.smband.batch.configure;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.smband.batch.item.SmsStepDecider;
import com.smband.batch.listener.JobCompletionNotificationListener;
import com.smband.batch.listener.SmsQueueJobListener;
import com.smband.batch.model.Person;
import com.smband.batch.model.SmsQueueData;
import com.smband.batch.processor.PersonItemProcessor;

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
@EnableBatchProcessing(dataSourceRef = "mainDataSource", transactionManagerRef = "tranManager")
@Configuration
public class BatchConfiguration {
	
	
	
	@Bean
	public FlatFileItemReader<Person> reader() {
		
		
		return new FlatFileItemReaderBuilder<Person>()
				.name("personItemReader")
				.resource(new ClassPathResource("sample-data.csv"))
				.delimited()
				.names("firstName", "lastName")
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
					setTargetType(Person.class);
				}})
				.build();
				
	}
	
	@Bean
	public PersonItemProcessor processor() {
		return new PersonItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Person> writer(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Person>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
				.dataSource(dataSource)
				.build();
		
	}
	
	@Bean
	public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
		return new JobBuilder("importUserJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
	}

	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager tranManager, JdbcBatchItemWriter<Person> writer) {
		return new StepBuilder("step1", jobRepository)
				.<Person, Person>chunk(10, tranManager)
				.reader(reader())
				.processor(processor())
				.writer(writer)
				.build();
	}
	
	// jdbccursor reader 구성
	@Bean
	public ItemReader<SmsQueueData> smsQueueItemReader(DataSource dataSource){
		log.info("smsQueue reader: {}", dataSource);
		return new JdbcCursorItemReaderBuilder<SmsQueueData>()
				.name("smsQueueItemReader")
				.fetchSize(10)
				.dataSource(dataSource)
				.beanRowMapper(SmsQueueData.class)
				.sql("select queue_id, name, tel, message, status from sms_queue where status=?")
				.queryArguments("I")
				.maxItemCount(1000000)
				.currentItemCount(0)
				.maxRows(100)
				.build();
	}
	
	@Bean
	@StepScope
	public ItemReader<SmsQueueData> smsQueueItemReader2 (DataSource dataSource){
//		ExecutionContext context = stepExecution.getJobExecution().getExecutionContext();
//		String value = (String)context.get("temp");
//		if(value == null) {
//			value = "임시";
//		}else {
//			value = value+" 임시";
//		}
//		context.put("temp", value);
//		log.info("context value: {}", value);
		HashMap<String, Order> sortKey = new HashMap<>();
        sortKey.put("queue_id", Order.ASCENDING);
        
		return new JdbcPagingItemReaderBuilder<SmsQueueData>()
                .name("smsQueueItemReader2")
                .dataSource(dataSource)
                .pageSize(10)
                .beanRowMapper(SmsQueueData.class)
//                .fetchSize(CHUNK_SIZE)
                .currentItemCount(0)
                .maxItemCount(1000000)
                .selectClause("queue_id, name, tel, message, status")
                .fromClause("from sms_queue")
                .whereClause("status='I'")
                .sortKeys(sortKey)
                .build();
	}
	
//	@Bean
//	public ItemProcessor<SmsQueueData, SmsQueueData> smsQueueProcessor(){
//		return new SmsQueueProcessor();
//	}
	
	@Bean
	public ItemWriter<SmsQueueData> smsQueueWriter(DataSource dataSource){
		//return new SampleItemWriter();
		return new JdbcBatchItemWriterBuilder<SmsQueueData>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql("update sms_queue set status=:status where queue_id=:queueId")
				//.sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
				.dataSource(dataSource)
				.build();
	}
	
	@Bean
	public Step smsQueueStep (JobRepository jobRepository, PlatformTransactionManager tranManager
			, ItemReader<SmsQueueData> smsQueueItemReader2
			, ItemProcessor<SmsQueueData, SmsQueueData> smsQueueProcessor
			, ItemWriter<SmsQueueData> smsQueueWriter
			) {
		return new StepBuilder("smsQueueStep", jobRepository)
				.<SmsQueueData, SmsQueueData>chunk(10, tranManager)
				.reader(smsQueueItemReader2)
				.processor(smsQueueProcessor)
				.writer(smsQueueWriter)
				.build();
	}
	
	@Bean
	public Step checkSmsStep(JobRepository jobRepository, PlatformTransactionManager tranManager, Tasklet smsCheckTaskLet) {
		return new StepBuilder("checkSmsStep", jobRepository)
				.tasklet(smsCheckTaskLet, tranManager)
				.build();
	}
	
	@Bean
	public JobExecutionDecider smsDecider() {
		return new SmsStepDecider();
	}
	
	@Bean
	public Job smsQueueJob(JobRepository jobRepository, Step smsQueueStep, Step checkSmsStep,  SmsQueueJobListener listener
			, JobExecutionDecider smsDecider) {
		return new JobBuilder("smsQueueJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.start(checkSmsStep)
				.next(smsDecider)
					.on(SmsStepDecider.EXIST_DATA).to(smsQueueStep)
				//.start(smsQueueStep)
				//.flow(step1)
				.end()
				.build(); 
	}
}
