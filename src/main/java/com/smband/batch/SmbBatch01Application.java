package com.smband.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SmbBatch01Application {

	public static void main(String[] args) {
//		SampleInst ins = new SampleInst() {{
//			setInt(10);
//		}};
//		
//		System.out.print("초기화 값: "+ ins.getInt());
		ApplicationContext context = SpringApplication.run(SmbBatch01Application.class, args);
		System.exit(SpringApplication.exit(context));
	}

}
