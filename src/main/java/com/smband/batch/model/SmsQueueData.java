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

import lombok.Data;

/**
 * <pre>
 * 개요:
 * </pre>
 * @author ytkim
 * @create 2023. 3. 29.
 * @version 
 * @since 
 */
@Data
public class SmsQueueData {
	private int queueId;
	private String name;
	private String tel;
	private String message;
	private String status;
}
