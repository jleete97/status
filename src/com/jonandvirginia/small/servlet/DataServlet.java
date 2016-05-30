package com.jonandvirginia.small.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

@SuppressWarnings("serial")
public abstract class DataServlet extends HttpServlet {
	
	public static final String DATA_DIR_PARAM = "data-dir";

	private static final Logger LOG = Logger.getLogger(DataServlet.class.getName());
	private static final String EVENT_FILE_PARAM = "event-file";
	
	private static final Predicate<Event> REMOVED = new Predicate<Event>() {
		public boolean test(Event event) {
			return ((Event) event).isRemoved();
		}
	};
	
	private String eventPath;
	
	
	protected List<Event> readEvents() {
		List<Event> events = null;
		InputStream in = null;
		LOG.debug("Reading events from " + eventPath);
		
		try {
			File file = new File(eventPath);
			in = new FileInputStream(file);
			TypeReference<List<Event>> typeRef = new TypeReference<List<Event>>() { };
			events = MapperUtil.MAPPER.readValue(in, typeRef);
			
			events.removeIf(REMOVED);
		} catch (Exception e) {
			LOG.error("Error reading events from " + eventPath, e);
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
		LOG.debug("Writing events to " + eventPath);
		
		try {
			file = new File(eventPath);
			MapperUtil.MAPPER.writeValue(file, events);
		} catch (Exception e) {
			LOG.error("Error writing events to " + eventPath, e);
			events = Collections.<Event>emptyList();
		}
	}
	
	@Override
	public void init() {
		String dataDir = getServletContext().getInitParameter(DATA_DIR_PARAM);
		String eventFile = getServletContext().getInitParameter(EVENT_FILE_PARAM);
		eventPath = dataDir + "/" + eventFile;
		LOG.info("Initialized event path from dir " + dataDir + " and " + eventFile + ":'" + eventPath + "'");
		
		try {
			File file = new File(eventPath);
			if (!file.exists()) {
				LOG.info("Event file did not exist, creating...");
				writeEvents(Collections.<Event>emptyList());
			}
		} catch (Exception e) {
			LOG.error("Error checking file path: " + eventPath, e);
			throw e; // Not recoverable, shut down.
		}
	}
}
