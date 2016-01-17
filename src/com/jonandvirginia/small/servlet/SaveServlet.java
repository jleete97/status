package com.jonandvirginia.small.servlet;

import java.io.BufferedReader;
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
		List<Event> events = this.readEvents();
		LOG.debug("Read " + events.size() + " from store");

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
		
		TypeReference<Event> typeRef = new TypeReference<Event>() { };
		try {
			Event event = MapperUtil.MAPPER.readValue(json, typeRef);
			event.setId(Long.toHexString(random.nextLong()));
			events.add(event);
			this.writeEvents(events);
		} catch (Exception e) {
			LOG.error("Error translating JSON '" + json + "'", e);
		}
	}
}
