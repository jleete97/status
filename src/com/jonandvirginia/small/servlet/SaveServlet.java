package com.jonandvirginia.small.servlet;

import java.io.BufferedReader;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * Save a new event.
 */
@SuppressWarnings("serial")
public class SaveServlet extends DataServlet {
	
	private Random random = new Random();

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		List<Event> events = this.readEvents();

		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			System.out.println("Oops: " + e.getMessage()); 
		}
		
		String json = sb.toString();
		TypeReference<Event> typeRef = new TypeReference<Event>() { };
		try {
			Event event = MapperUtil.MAPPER.readValue(json, typeRef);
			event.setId(random.nextLong());
			events.add(event);
			this.writeEvents(events);
		} catch (Exception e) {
			System.out.println("Error translating JSON '" + json + "': " + e.getMessage());
		}
	}
}
