package com.task.httpevent.producers.properties;

public class ConsumerProperties extends BaseKafkaProperties {

	private static final long serialVersionUID = 1L;
	private static ConsumerProperties props;
	
	public static ConsumerProperties getInstance() {
		if(props == null) {
			props = new ConsumerProperties();
		}
		return props;
	}
	
	private ConsumerProperties() {
		super();
		this.put("group.id", "group-1");
		this.put("enable.auto.commit", "true");
		this.put("auto.commit.interval.ms", "1000");
		this.put("auto.offset.reset", "earliest");
		this.put("session.timeout.ms", "30000");
		this.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		this.put("value.deserializer", "com.task.httpevent.producers.model.EventDeSerializer");
	}

}
