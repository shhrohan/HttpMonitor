package com.task.httpevent.producers;

import com.task.httpevent.producers.pubsub.HttpCodeConsumer;
import com.task.httpevent.producers.pubsub.HttpCodeProducer;
import com.task.httpevent.producers.ui.HealthChecker;

public class ProjectRunner {

	private static ProducerTask producerTask;
	private static ConsumerTask consumerTask;
	private static HealthCheckerTask healthChecker;
	
	static class ProducerTask extends Thread {
		
	    public void run() {
	       HttpCodeProducer.start();
	    }
	}
	
	static class ConsumerTask extends Thread {
		
	    public void run() {
	       HttpCodeConsumer.start();
	    }
	}
	
	static class HealthCheckerTask extends Thread {
		
	    public void run() {
	       HealthChecker.start();
	    }
	}
	
	
	public static void main(String[] args) {
		
		producerTask = new ProducerTask();
		consumerTask = new ConsumerTask();
		healthChecker =  new HealthCheckerTask();
		producerTask.start();
		
		consumerTask.start();
		healthChecker.start();
	}

}
