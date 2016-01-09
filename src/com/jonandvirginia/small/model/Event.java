package com.jonandvirginia.small.model;

import java.util.Date;

public class Event {

	private long id;
	private Date when;
	private String what;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getWhen() {
		return when;
	}
	public void setWhen(Date when) {
		this.when = when;
	}
	
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
}
