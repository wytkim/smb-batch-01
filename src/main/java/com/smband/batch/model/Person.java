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
package com.smband.batch.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 27.
 * @version 
 * @since 
 */
@Getter
@Setter
@ToString
public class Person {
	private String lastName;
	private String firstName;

	public Person() {
	}
	public Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
