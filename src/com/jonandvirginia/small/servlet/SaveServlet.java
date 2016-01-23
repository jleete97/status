package com.jonandvirginia.small.servlet;

import java.io.BufferedReader;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * Save a new event.
 */
@SuppressWarnings("serial")
public class SaveServlet extends DataServlet {
	
	private static final Logger LOG = Logger.getLogger(DataServlet.class);
	
	private Random random = new Random();

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			LOG.debug("Reading JSON from request...");
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			LOG.error("Error getting request data", e);
		}
		
		String json = sb.toString();
		LOG.debug("Read event JSON: '" + json + "'");
		
		saveEvent(json);
	}

	private void saveEvent(String json) {
		try {
			// Read saved Event from request
			TypeReference<Event> typeRef = new TypeReference<Event>() { };
			Event savedEvent = MapperUtil.MAPPER.readValue(json, typeRef);
			
			// Read event list
			List<Event> events = this.readEvents();
			LOG.debug("Read " + events.size() + " from store");
			
			// Remove if previously saved
			if (savedEvent.getId() != null && savedEvent.getId().trim().length() > 0) {
				LOG.debug("Looking for event '" + savedEvent.getId() + "' to remove...");
				Iterator<Event> it = events.iterator();
				while (it.hasNext()) {
					Event event = it.next();
					
					if (savedEvent.getId().equals(event.getId())) {
						LOG.debug("Removing event ID '" + savedEvent.getId() + "'");
						it.remove();
					}
				}
			} else {
				String newId = Long.toHexString(random.nextLong());
				LOG.debug("Setting new event ID '" + newId + "'");
				savedEvent.setId(newId);
			}
			
			// Add to list (again, if previously saved, and removed above)
			events.add(savedEvent);
			LOG.debug("Saved event ID '" + savedEvent.getId() + "'");
			
			// Re-persist list
			this.writeEvents(events);
		} catch (Exception e) {
			LOG.error("Error saving event '" + json + "'", e);
		}
	}
}
