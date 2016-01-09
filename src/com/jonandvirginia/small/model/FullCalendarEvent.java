package com.jonandvirginia.small.model;

import java.util.Date;

/**
 * FullCalendar version of an event. Similar to a regular {@link Event}, but
 * includes a few extra attributes (mostly style-related).
 */
public class FullCalendarEvent {

	private long id;
	private String title;
	private boolean allDay;
	private Date start;
	private Date end;
	private String color;
	private String backgroundColor;
	private String borderColor;
	private String textColor;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
	
	public String getTextColor() {
		return textColor;
	}
	public void setTextColor(String textColor) {
		this.textColor = textColor;
	}
	
	@Override
	public String toString() {
		return "'" + title
				+ (end == null ? " at " + start : " from " + start + " to " + end)
				+ " [" + id + "]";
	}
}
