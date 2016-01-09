package com.jonandvirginia.small.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * List events.
 */
@SuppressWarnings("serial")
public class ListServlet extends DataServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		List<Event> events = this.readEvents();
		try {
			String json = MapperUtil.MAPPER.writeValueAsString(events);
			response.getWriter().println(json);
			response.getWriter().flush();
		} catch (Exception e) {
			System.out.println("Error processing list: " + e.getMessage());
		}
	}
}
