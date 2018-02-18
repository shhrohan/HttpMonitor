package com.task.httpevent.producers.pubsub;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import com.task.httpevent.producers.threads.ProducerThread;

public class HttpCodeProducer {

	static Set<String> regions = new HashSet<>();
	static Set<Integer> nodes = new HashSet<>();
	static Map<String, Set<Integer>> regionMap = new HashMap<>();
	static Properties prop = new Properties();

	private static void init() {
		for (int i = 0; i < 1000; i++) {
			if (i < 4) {
				regions.add("region_" + i);
			}
			nodes.add(i);
		}
		/*
		 * Allocate nodes to regions. save this assignment in a map
		 */
		for (int i = 0; i < 1000; i++) {
			Set<Integer> set = new HashSet<>();
			if (regionMap.containsKey("region_" + (i % 4))) {
				set = regionMap.get("region_" + (i % 4));
			}
			set.add(i);
			regionMap.put("region_" + (i % 4), set);
		}
	}

	public static void start() {
		init();
		Integer[] values =  new Integer[] {500,200};
		System.out.println("[Producer Started...]");
		Random r = new Random();
		try {
			while(true) {
				Thread.sleep(50l);
				int selectedRegion = r.nextInt(4);
				String region =  "region_"+ selectedRegion;
				Iterator<Integer> nodeIterator = regionMap.get(region).iterator();
				Integer selectedNode =  r.nextInt(250);
				Integer nodeId = -1;
				while(selectedNode > 0) {
					nodeId = nodeIterator.next();
					selectedNode--;
				}
				ProducerThread t = new ProducerThread(region, nodeId, prop.getProperty("attribute"),
						values[r.nextInt(2)]);
				t.start();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		start();
	}

}
