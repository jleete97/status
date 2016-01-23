package com.jonandvirginia.small.model;

import java.util.Date;

public class Event {

	private String id;
	private long updated; // timestamp
	private boolean deleted;
	private String what;
	private Date start;
	private Date end;
	private boolean allDay;
	private String type;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public long getUpdated() {
		return updated;
	}
	public void setUpdated(long updated) {
		this.updated = updated;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
		if ((start != null)
				&& ((this.end == null) || this.end.compareTo(start) < 0)) {
			this.end = start;
		}
	}
	
	public Date getEnd() {
		return (end == null) ? start : end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
