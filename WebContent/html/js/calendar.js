function setupCalendar() {
	$('#refreshCalendar').click(function() {
		$.ajax({
			type : "GET",
			url  : "/status/events",
			success : function(data) {
				
			}
		})
	});
    // page is now ready, initialize the calendar...

    $('#calendar').fullCalendar({
    	events : "/status/fullCalendarEvents"
    });
    
    window.setInterval(function() {
    	$('#calendar').fullCalendar("refetchEvents");
    }, 10000);
}