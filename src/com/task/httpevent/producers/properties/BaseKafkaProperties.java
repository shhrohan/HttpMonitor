package com.task.httpevent.producers.properties;

import java.util.Properties;

import com.task.httpevent.producers.constants.Constants;

public class BaseKafkaProperties extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseKafkaProperties() {
		this.put("bootstrap.servers", Constants.KAFKA_HOST + ":" + Constants.KAFKA_LISTENING_PORT);
	}
	
}
