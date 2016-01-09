package com.jonandvirginia.small.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

@SuppressWarnings("serial")
public abstract class DataServlet extends HttpServlet {
	
	private static final String EVENT_FILE_CONTEXT_PARAM = "data-file";
	
	private String eventPath;
	
	
	@Override
	public void init() {
		ServletContext context = this.getServletContext();
		eventPath = context.getInitParameter(EVENT_FILE_CONTEXT_PARAM);
	}
	
	protected List<Event> readEvents() {
		List<Event> events = null;
		InputStream in = null;
		
		try {
			File file = new File(eventPath);
			in = new FileInputStream(file);
			TypeReference<List<Event>> typeRef = new TypeReference<List<Event>>() { };
			events = MapperUtil.MAPPER.readValue(in, typeRef);
		} catch (Exception e) {
			events = new java.util.ArrayList<>();
		} finally {
			if (in != null) {
				try { in.close(); } catch (Exception e) { /* bury */ }
			}
		}
		
		return events;
	}

	protected void writeEvents(List<Event> events) {
		File file = null;
		
		try {
			file = new File(eventPath);
			MapperUtil.MAPPER.writeValue(file, events);
		} catch (Exception e) {
			events = Collections.<Event>emptyList();
		}
	}

}
