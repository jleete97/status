package com.jonandvirginia.small.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jonandvirginia.small.model.Event;
import com.jonandvirginia.small.model.FullCalendarEvent;
import com.jonandvirginia.small.util.FullCalendarStyle;
import com.jonandvirginia.small.util.FullCalendarTranslator;
import com.jonandvirginia.small.util.MapperUtil;

/**
 * List events in "FullCalendar" format.
 */
@SuppressWarnings("serial")
public class FullCalendarServlet extends DataServlet {
	
	private static final String STYLE_FILE_CONTEXT_PARAM = "style-file";
	
	private FullCalendarTranslator translator;
	private String stylePath;
	
	@Override
	public void init() {
		super.init();
		ServletContext context = this.getServletContext();
		stylePath = context.getInitParameter(STYLE_FILE_CONTEXT_PARAM);
		
		Map<String, FullCalendarStyle> styles = null;
		InputStream in = null;

		try {
			File file = new File(stylePath);
			in = new FileInputStream(file);
			TypeReference<Map<String, FullCalendarStyle>> typeRef = new TypeReference<Map<String, FullCalendarStyle>>() { };
			styles = MapperUtil.MAPPER.readValue(in, typeRef);
		} catch (Exception e) {
			styles = new HashMap<>();
		} finally {
			if (in != null) {
				try { in.close(); } catch (Exception e) { /* bury */ }
			}
		}

		translator = new FullCalendarTranslator();
		translator.setStyles(styles);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		List<Event> events = this.readEvents();
		List<FullCalendarEvent> fces = new ArrayList<>();
		
		for (Event event : events) {
			FullCalendarEvent fce = translator.toFullCalendarEvent(event);
			fces.add(fce);
		}
		
		try {
			String json = MapperUtil.MAPPER.writeValueAsString(fces);
			response.getWriter().println(json);
			response.getWriter().flush();
		} catch (Exception e) {
			System.out.println("Error processing full calendar list: " + e.getMessage());
		}
	}

}
