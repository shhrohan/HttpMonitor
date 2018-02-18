package com.task.httpevent.producers.pubsub;

import java.util.Arrays;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import com.task.httpevent.producers.constants.Constants;
import com.task.httpevent.producers.handlers.EventHandler;
import com.task.httpevent.producers.model.Event;
import com.task.httpevent.producers.properties.ConsumerProperties;

public class HttpCodeConsumer {

	public static void start() {
		KafkaConsumer<String, Event> kafkaConsumer = new KafkaConsumer<>(ConsumerProperties.getInstance());
		kafkaConsumer.subscribe(Arrays.asList(Constants.KAFKA_TOPIC));
		System.out.println("[Consumer Started...]");
		while (true) {
			ConsumerRecords<String, Event> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, Event> record : records) {
				EventHandler.handle(record.value());
			}
			kafkaConsumer.commitSync();
		}
	}

	public static void main(String[] args) {
		start();
	}
	
}
