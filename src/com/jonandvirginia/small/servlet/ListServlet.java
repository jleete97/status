package com.jonandvirginia.small.servlet;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.model.EventQueryResponse;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * List events.
 */
@SuppressWarnings("serial")
public class ListServlet extends DataServlet {

	private static final Logger LOG = Logger.getLogger(ListServlet.class);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String updateTimeStr = request.getParameter("time");
		long updateTime = 0L;
		try {
			if (updateTimeStr != null) {
				updateTime = Long.parseLong(updateTimeStr);
			}
		} catch (NumberFormatException e) {
			LOG.error("failed to parse update time '" + updateTimeStr + "'", e);
		}
		
		List<Event> events = this.readEvents();
		
		// Remove events before the specified update time, if any.
		if (updateTime > 0) {
			Iterator<Event> it = events.iterator();
			while (it.hasNext()) {
				Event ev = it.next();
				if (ev.getUpdated() <= updateTime) {
					it.remove();
				}
			}
		}
		
		LOG.debug("Listing " + events.size() + " events");
		
		// Assemble full response: metadata (time) plus event list
		EventQueryResponse queryResponse = new EventQueryResponse();
		queryResponse.setUpdateTime(updateTime);
		queryResponse.setEvents(events);
		
		try {
			String json = MapperUtil.MAPPER.writeValueAsString(queryResponse);
			response.getWriter().println(json);
			response.getWriter().flush();
		} catch (Exception e) {
			LOG.error("Error processing list", e);
		}
	}

//	private void readGoogleEvents() {
//	}
//	
//	private static Credential authorize() throws Exception {
//		  // load client secrets
//		  GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//		      new InputStreamReader(CalendarSample.class.getResourceAsStream("/client_secrets.json")));
//		  // set up authorization code flow
//		  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//		      httpTransport, JSON_FACTORY, clientSecrets,
//		      Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
//		      .build();
//		  // authorize
//		  return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//		} 
}
