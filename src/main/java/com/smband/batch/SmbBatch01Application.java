package com.smband.batch;

import java.util.Date;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(value = {
		"classpath:property/${server.mode}/jdbc.properties",
}, encoding = "UTF-8", ignoreResourceNotFound = false)
public class SmbBatch01Application {

	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job1;
	
	@PostConstruct
	public void init() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		JobParametersBuilder builder = new JobParametersBuilder().addDate("newDate", new Date());
		
		jobLauncher.run(job1, builder.toJobParameters());
	}

	public static void main(String[] args) {
		// server.mode default 설정.
		String serverMode = System.getProperty("server.mode", "debug");
		System.setProperty("server.mode", serverMode);
//		SampleInst ins = new SampleInst() {{
//			setInt(10);
//		}};
//		
//		System.out.print("초기화 값: "+ ins.getInt());
		ApplicationContext context = SpringApplication.run(SmbBatch01Application.class, args);
		System.exit(SpringApplication.exit(context));
	}

}
