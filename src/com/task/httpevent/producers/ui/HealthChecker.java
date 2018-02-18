package com.task.httpevent.producers.ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.task.httpevent.producers.handlers.EventHandler;

public class HealthChecker {

	static Float threshold = 0f;
	static DecimalFormat numberFormat = new DecimalFormat("#.00");
	
	public static void start(){
		init();
		while(true) {
			try {
				Thread.sleep(2500l);
				Timestamp t = getTimeStampForCurrentMinute();
				Map<String,Map<Integer,Long>> healthMap = EventHandler.getHealthMap().get(t);
				if(healthMap != null) {
					StringBuilder status =  new StringBuilder();
					status.append("############### Region Status ##############\n");
					for(Entry<String,Map<Integer,Long>> e : healthMap.entrySet()) {
						String region = e.getKey();
						Map<Integer,Long> regionHealthMap = e.getValue();
						Long fives = regionHealthMap.get(500)  == null ? 0 : regionHealthMap.get(500) ;
						Long twos = regionHealthMap.get(200) == null ? 1 : regionHealthMap.get(200);
						
						Float ratio = ((float)fives)/((float)twos);
						status.append("["+region + "[500 -> " + fives +", 200 -> " + twos +"]:[Ratio=" + numberFormat.format(ratio)+"]");
						if(ratio > threshold) {
							status.append("[>"+ threshold +"][**** ALERT ****]");
						}
						status.append("\n");
					}
					status.append("############### ######## ##############\n");
					System.out.println(status.toString());
				}
			}
			catch(Exception ex) {
				System.err.println("Error in Health Checker. Retrying...");
			}
		}
	}

	private static void init() {
		InputStream input = null;
		Properties prop = new Properties();
		try {
			input = new FileInputStream("configuration.properties");
			prop.load(input);
			String value = prop.getProperty("threshold");
			threshold = Float.parseFloat(value);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
		
	}
	
	private static Timestamp getTimeStampForCurrentMinute() {
		LocalDate date = LocalDate.now();
	    LocalTime time = LocalTime.now();
	    LocalDateTime dateTime = LocalDateTime.of(date, time);
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
