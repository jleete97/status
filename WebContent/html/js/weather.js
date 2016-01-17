$(document).ready(function() {
	
	$("#refreshWeather").click(function() {
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
});
