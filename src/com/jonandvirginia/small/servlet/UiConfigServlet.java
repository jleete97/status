package com.jonandvirginia.small.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonandvirginia.small.util.MapperUtil;

/**
 * Return UI configuration parameters, such as ZIP code, that the UI needs for
 * its own purposes.
 */
@SuppressWarnings("serial")
public class UiConfigServlet extends HttpServlet {

	private static final String UI_CONFIG_FILE_PARAM = "ui-config-file";
	private static final String REQUEST_PARAM = "param";
	
	private Map<String, String> uiConfig = new HashMap<>();
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		String uiConfigParam = request.getParameter(REQUEST_PARAM);
		System.out.println("GOT: " + uiConfigParam);
		String value = uiConfig.get(uiConfigParam);
		System.out.println("VALUE = '" + value + "'");
		
		try {
			response.getWriter().print(value);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		String dataDir = getServletContext().getInitParameter(DataServlet.DATA_DIR_PARAM);
		String uiConfigFilename = getServletContext().getInitParameter(UI_CONFIG_FILE_PARAM);
		String uiConfigPath = dataDir + "/" + uiConfigFilename;

		try {
			File file = new File(uiConfigPath);
			InputStream in = new FileInputStream(file);
			uiConfig = MapperUtil.MAPPER.readValue(in, HashMap.class);
		} catch (Exception e) {
			System.out.println("Error reading config file: " + uiConfigPath);
			uiConfig = new HashMap<>();
		}
	}
}
