package com.jonandvirginia.small.util;

import java.util.HashMap;
import java.util.Map;

import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.model.FullCalendarEvent;

public class FullCalendarTranslator {

	private Map<String, FullCalendarStyle> styles = new HashMap<>();
	
	
	public FullCalendarEvent toFullCalendarEvent(Event event) {
		FullCalendarEvent fce = new FullCalendarEvent();
		
		fce.setId(Long.parseLong(event.getId(), 16));
		fce.setTitle(event.getWhat());
		fce.setStart(event.getStart());
		fce.setEnd(event.getEnd());
		fce.setAllDay(event.isAllDay());

		// Look up style based on event type.
		FullCalendarStyle style = styles.get(event.getType());
		if (style != null) {
			fce.setColor(style.getColor());
			fce.setBackgroundColor(style.getBackgroundColor());
			fce.setBorderColor(style.getBorderColor());
			fce.setTextColor(style.getTextColor());
		}
		
		return fce;
	}
	
	public Event toEvent(FullCalendarEvent fce) {
		Event event = new Event();
		
		event.setId(Long.toHexString(fce.getId()));
		event.setWhat(fce.getTitle());
		event.setStart(fce.getStart());
		event.setEnd(fce.getEnd());
		event.setAllDay(fce.isAllDay());
		
		return event;
	}

	public Map<String, FullCalendarStyle> getStyles() {
		return styles;
	}
	public void setStyles(Map<String, FullCalendarStyle> styles) {
		this.styles = styles;
	}
}
