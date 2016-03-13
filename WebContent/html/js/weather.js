$(document).ready(function() {
	
	$("#refreshWeather").click(function() {
		return;
		alert("Clicked!");
		var zip = "22101";
		var start = "2016-01-10T00:00:00";
		var end = "2016-01-11T00:00:00";
		
		var url = "http://graphical.weather.gov/xml/sample_products/browser_interface/ndfdXMLclient.php?"
				+ "zipCodeList=" + zip + "&product=time-series"
				+ "&begin=" + start
				+ "&end=" + end + "&maxt=maxt&mint=mint";
		alert("Getting " + url);
		
		$.get(url,
				function(responseXml) {
					alert("Got response:\n" + responseXml);
					
					var min = 0, max = 100;
					var units = "";
					var xml = $.parseXml(responseXml);
					
					$(xml).find("temperature").each(function(temp) {
						var value = $(this).find("value").text();
						var type = $(this).attr("type");
						units = $(this).find("units").text(); // might repeat
						
						if (type == "minimum") {
							min = value;
						} else if (type == "maximum") {
							max = value;
						}
						
						// output
						$("#hi-temp-value").html(max);
						$("#lo-temp-value").html(min);
						$("#temp-units").html(units);
					});
		});
	});
	
	var updateConditions = function(data) {
		var obs = data.current_observation;
		
		$("#weatherWhere").text(obs.full);
		$("#currentConditionsIcon").attr("src", obs.icon_url);
		$("#currentTemp").text(obs.temp_f + " F");
		$("#currentConditions").text(obs.weather);
		$("#currentFeelsLike").text("Feels like " + obs.feelslike_f + " F");
//		$("#currentGeneral").text("Wind: " + obs.wind_string);
	};
	
	var updateForecast = function(data) {
		var day = data.forecast.simpleforecast.forecastday[0];
		
		$("#forecastHigh").text("" + day.high.fahrenheit + " F");
		$("#forecastLow").text("" + day.low.fahrenheit + " F");
		$("#forecastIcon").attr("src", day.icon_url);
		$("#forecastConditions").text(day.conditions);
	}
	
	var getWundergroundWeather = function() {
		var zip = "22101";
		var key = "110eef78ce81b428";
		var urlBase = "http://api.wunderground.com/api";
		
		var currentUrl = buildUrl(urlBase, key, zip, "conditions");
		$.getJSON(currentUrl, updateConditions);
		
		var forecastUrl = buildUrl(urlBase, key, zip, "forecast");
		$.getJSON(forecastUrl, updateForecast);
	};
	
	var buildUrl = function(base, key, zip, type) {
		return base + "/" + key + "/" + type + "/q/" + zip + ".json";
	}
	
	$("#refreshNow").click(getWundergroundWeather);
	$("#refreshForecast").click(getWundergroundWeather);
	
	// Get weather every ten minutes, and start now.
	var weatherRefreshInterval = 10 * 60 * 1000;
    window.setInterval(getWundergroundWeather, weatherRefreshInterval);

    getWundergroundWeather();
});
