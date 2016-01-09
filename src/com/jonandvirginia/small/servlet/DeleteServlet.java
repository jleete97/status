package com.jonandvirginia.small.servlet;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonandvirginia.small.model.Event;

/**
 * Delete an event.
 */
@SuppressWarnings("serial")
public class DeleteServlet extends DataServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String idStr = request.getParameter("id");
		
		try {
			long id = Long.valueOf(idStr);

			List<Event> events = this.readEvents();
			Iterator<Event> it = events.iterator();
			
			while (it.hasNext()) {
				if (it.next().getId() == id) {
					it.remove();
					break;
				}
			}
			
			this.writeEvents(events);
		} catch (Exception e) {
			
		}
	}
}
