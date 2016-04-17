package com.jonandvirginia.small.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jonandvirginia.small.model.Event;

/**
 *  Remove (delete) an event. Remove is a logical delete only.
 */
@SuppressWarnings("serial")
public class RemoveServlet extends DataServlet {

	private static final Logger LOG = Logger.getLogger(RemoveServlet.class);
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		LOG.debug("Deleting event ID " + id);
		
		try {
			List<Event> events = this.readEvents();
			
			for (Event event : events) {
				if (event.getId().equals(id)) {
					event.setRemoved(true);
					LOG.debug("Found and removed event with ID " + id);
					break;
				}
			}
			
			this.writeEvents(events);
		} catch (Exception e) {
			LOG.error("Error deleting event ID " + id, e);
		}
	}
}
