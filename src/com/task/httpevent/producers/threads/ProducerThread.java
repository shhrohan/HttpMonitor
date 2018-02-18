package com.task.httpevent.producers.threads;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.task.httpevent.producers.constants.Constants;
import com.task.httpevent.producers.model.Event;
import com.task.httpevent.producers.properties.ProducerProperties;

public class ProducerThread extends Thread {

	String region;
	Integer node_Id;
	String attribute;
	Integer value;

	public ProducerThread(String region, Integer node_Id, String attribute, Integer value) {
		super();
		this.region = region;
		this.node_Id = node_Id;
		this.attribute = attribute;
		this.value = value;
	}

	@Override
	public void run() {

		Producer<String, Event> producer = null;
		try {
			producer = new KafkaProducer<>(ProducerProperties.getInstance());
			Event msg = new Event(this.region, this.node_Id, this.attribute, this.value,
					new Timestamp(new Date().getTime()));
			producer.send(new ProducerRecord<String, Event>(Constants.KAFKA_TOPIC, msg));
			//System.out.println("Sent :" + msg.region +" [Node:" + msg.nodeId+"] [code:" + this.value+"]");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}

}