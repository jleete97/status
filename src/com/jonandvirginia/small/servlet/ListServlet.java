package com.jonandvirginia.small.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * List events.
 */
@SuppressWarnings("serial")
public class ListServlet extends DataServlet {

	private static final Logger LOG = Logger.getLogger(ListServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		List<Event> events = this.readEvents();
		LOG.debug("Listing " + events.size() + " events");
		
		try {
			String json = MapperUtil.MAPPER.writeValueAsString(events);
			response.getWriter().println(json);
			response.getWriter().flush();
		} catch (Exception e) {
			LOG.error("Error processing list", e);
		}
	}
}
