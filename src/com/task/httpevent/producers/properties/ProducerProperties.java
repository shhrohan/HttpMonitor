package com.task.httpevent.producers.properties;

public class ProducerProperties extends BaseKafkaProperties {
	private static final long serialVersionUID = 1L;
	private static ProducerProperties props;
	
	public static ProducerProperties getInstance() {
		if(props == null) {
			props = new ProducerProperties();
		}
		return props;
	}
	
	private ProducerProperties() {
		super();
		this.put("acks", "all");
		this.put("retries", 0);
		this.put("batch.size", 16384);
		this.put("linger.ms", 1);
		this.put("buffer.memory", 33554432);
		this.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		this.put("value.serializer", "com.task.httpevent.producers.model.EventSerializer");
	}
}
