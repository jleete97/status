package com.jonandvirginia.small.model;

import java.util.List;

/**
 * Response to an event update query, with metadata and events.
 */
public class EventQueryResponse {

	private long updateTime;
	private List<Event> events;
	
	
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
