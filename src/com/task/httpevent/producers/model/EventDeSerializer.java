package com.task.httpevent.producers.model;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventDeSerializer implements Deserializer<Event> {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Event deserialize(String arg0, byte[] arg1) {
		ObjectMapper mapper = new ObjectMapper();
		Event user = null;
		try {
			user = mapper.readValue(arg1, Event.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	 
}