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
package com.smband.batch;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 27.
 * @version 
 * @since 
 */
public class SampleInst {

	private int i;
	public int getInt() {
		return i;
	}
	
	public void setInt(int data) {
		System.out.println("int: "+data);
		i=data;
	}
}
