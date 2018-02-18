package com.task.httpevent.producers.model;

import java.sql.Timestamp;

public class Event {

	public String region;
	public int nodeId;
	public Timestamp timestamp;
	public String attribute;
	public Integer attributeValue;

	public Event() {
		// TODO Auto-generated constructor stub
	}
	
	public Event(String region, Integer nodeId, String attribute, Integer attributeValue, Timestamp timestamp) {
		super();
		this.region = region;
		this.nodeId = nodeId;
		this.timestamp = timestamp;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}

	public String getRegion() {
		return region;
	}

	public int getNodeId() {
		return nodeId;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public String getAttribute() {
		return attribute;
	}

	public Integer getAttributeValue() {
		return attributeValue;
	}

	@Override
	public String toString() {
		return "Event [region=" + region + ", nodeId=" + nodeId + ", timestamp=" + timestamp + ", attribute="
				+ attribute + ", attributeValue=" + attributeValue + "]";
	}

}
