package com.task.httpevent.producers.handlers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.task.httpevent.producers.model.Event;

public class EventHandler {

	private static Map<Timestamp, Map<String,Map<Integer,Long>>> timeStampedRegionHealthMap = new HashMap<>();
	
	public static Map<Timestamp, Map<String, Map<Integer, Long>>> getHealthMap() {
		return timeStampedRegionHealthMap;
	}
	
	public static synchronized void handle(Event event) {
		if(event != null) {
			
			Timestamp t = getTimeStampTillMin(event);
			Map<String,Map<Integer,Long>> regionHealthMap = new HashMap<>();
			Map<Integer,Long> attributeCountMap = new HashMap<>();
			
			if(timeStampedRegionHealthMap.containsKey(t)) {
				regionHealthMap = timeStampedRegionHealthMap.get(t);
			}
			if(regionHealthMap.containsKey(event.region)) {
				attributeCountMap = regionHealthMap.get(event.region);
			}
			if(attributeCountMap.containsKey(event.attributeValue)) {
				Long count =attributeCountMap.get(event.attributeValue);
				count++;
				attributeCountMap.put(event.attributeValue, count);
			}else {
				attributeCountMap.put(event.attributeValue, 1l);
			}
			regionHealthMap.put(event.region, attributeCountMap);
			timeStampedRegionHealthMap.put(t, regionHealthMap);
		}
	}

	private static Timestamp getTimeStampTillMin(Event event) {
		LocalDateTime dateTime = event.getTimestamp().toLocalDateTime();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, dateTime.getYear());
		c.add(Calendar.MONTH, dateTime.getMonth().ordinal());
		c.add(Calendar.DAY_OF_MONTH, dateTime.getDayOfMonth());
		c.set(Calendar.HOUR_OF_DAY, dateTime.getHour());
		c.set(Calendar.MINUTE, dateTime.getMinute());
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Timestamp t = new Timestamp(c.getTime().getTime());
		return t;
	}
}
