package com.jonandvirginia.small.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

@SuppressWarnings("serial")
public abstract class DataServlet extends HttpServlet {
	
	public static final String DATA_DIR_PARAM = "data-dir";
	private static final String EVENT_FILE_PARAM = "event-file";
	
	private String eventPath;
	
	
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
	
	@Override
	public void init() {
		String dataDir = getServletContext().getInitParameter(DATA_DIR_PARAM);
		String eventFile = getServletContext().getInitParameter(EVENT_FILE_PARAM);
		eventPath = dataDir + "/" + eventFile;
		
		try {
			File file = new File(eventPath);
			if (!file.exists()) {
				writeEvents(Collections.<Event>emptyList());
			}
		} catch (Exception e) {
			System.out.println("Error checking file path: " + eventPath);
			throw e; // Not recoverable, shut down.
		}
	}
}
