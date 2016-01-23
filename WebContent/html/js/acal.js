// Calendar in AngularJS

angular.module("acalApp", ["ngRoute"])

.controller("MainCtrl", ["$http", "$window", function($http, $window) {
	var self = this;
	
	self.lastUpdateTime = 0; // Timestamp of last update
	self.events = [ ]; // Events
	self.weeks = [ ]; // Calendar data
	self.event = { }; // Current event for editing
	
	initializeWeeks = function() {
		var today = $window.today();
		var thisWeek = $window.weekStart(today);
		var startWeek = $window.offsetInDays(thisWeek, -7);
		var endWeek = $window.offsetInDays(thisWeek, 104 * 7);
		for (var w = startWeek; w <= endWeek; w = $window.nextWeek(w)) {
			var b = (w == endWeek);
			var l = (w < endWeek);
//			alert("w = " + w + ", end = " + endWeek + ", b = " + b + ", l = " + l + ".");
			self.weeks.push($window.buildWeek(w));
		}
	};
	initializeWeeks();
	
	var removeEventFromArrayById = function(eventList, id) {
		for (var i = 0; i < eventList.length; i++) {
			if (eventList[i].id == event.id) {
				eventList.splice(i, 1);
				break;
			}
		}
	}
	
	var addEvent = function(event) {
		// Add to list of events
		self.events.push(event);
		
		// Add to calendar
		for (var d = event.start; d <= event.end; d = $window.offsetInDays(d, 1)) {
			d.events.push(event);
		}
	};
	
	var removeEvent = function(event) {
		// Remove from list of events
		removeEventFromArrayById(self.events, event.id);
		
		// Remove from calendar
		for (var d = event.start; d <= event.end; d = $window.offsetInDays(d, 1)) {
			removeEventFromArrayById(d.events, event.id);
		}
	};
	
	// Update an event: change the title, dates, etc.
	var updateEvent = function(oldEvent, newEvent) {
		if (oldEvent.start == newEvent.start
				&& oldEvent.end == newEvent.end) {
			oldEvent.what = newEvent.what;
			oldEvent.type = newEvent.type;
		} else {
			removeEvent(oldEvent);
			addEvent(newEvent);
		}
	}
	
	// Callback function for querying server for update since a given time
	var receiveUpdate = function(data) {
		for (var event in data) {
			var oldEvent = findEvent(event.id);
			if (oldEvent != null) {
				updateEvent(oldEvent, event);
			}
		}
	};
	
	// Query the server for an update
	self.update = function() {
		$http.get("/status/events?time=" + self.lastUpdateTime)
			.then(function(response) {
				self.lastUpdateTime = response.data.updateTime;
				receiveUpdate(response.data.events);
			}, function(error) {
				alert("Error getting update: " + error);
			});
	};
	
	var list = function() {
		return $http.get("/status/events")
			.then(function(response) {
				self.lastUpdateTime = response.data.updateTime;
				receiveUpdate(response.data.events);
			}, function(error) {
				alert("Error: " + error);
			});
	};
	list();

	self.save = function() {
		$http.post("save", self.event).then(update);
	};

	self.remove = function(event) {
		var deleteId = event.id;
		$http.remove("/status/delete",
		{
			params : { "id" : deleteId }
		}).then(list);
	};

	self.refresh = list;
}])

.controller('TabsCtrl', ['$scope', function ($scope) {
    $scope.tabs = [{
            title: 'Today',
            url: 'today.html'
	    }, {
	        title: 'Add',
	        url: 'add.html'
	    }, {
	        title: 'Edit',
	        url: 'edit.html'
        }, {
            title: 'Find',
            url: 'find.html'
    }];

    $scope.currentTab = 'today.html';

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
    }
    
    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab;
    }
}]);

// Date helper functions
// Standard format = ISO 8601 = YYYY-MM-DD

var DAY = 24 * 60 * 60 * 1000; // Day in msec
var MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
var DOW = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

// Return today in standard format.
function today() {
	return new Date().toISOString().substring(0, 10);
}

// Get standard-format date at beginning of week specified by ds.
function weekStart(ds) {
	var ts = Date.parse(ds); // long
	var tsd = new Date(ts); // Date
	var dow = tsd.getDay(); // 0-6
	return offsetInDays(ds, -dow);
}

// Get previous week.
function previousWeek(s) {
	return offsetInDays(s, -7);
}

// Get next week.
function nextWeek(s) {
	return offsetInDays(s, 7);
}

// Return date offset by some number of days.
function offsetInDays(s, offset) {
	var timestamp = Date.parse(s); // long
	var offsetTimestamp = timestamp + offset * DAY; // long
	var offsetDate = new Date(offsetTimestamp); // Date
	return offsetDate.toISOString().substring(0, 10);
}

// Build the data structure for a week:
// id, days[]
function buildWeek(s) {
	var week = { };
	
	week.id = s;
	week.days = [];
	
	for (var i = 0; i < 7; i++) {
		var d = offsetInDays(s, i);
		var month = parseInt(d.substring(5, 7));
		var dom = parseInt(d.substring(8, 10));
		
		var day = { };
		day.id = d;
		day.month = MONTHS[month - 1];
		day.dom = "" + dom;
		day.dow = DOW[i];
		day.events = [];
		
		week.days.push(day);
	}
	
	return week;
}

/*
self.weeks = [ // Calendar data
  {
	id : "20160117",
	days : [
    	    { id : "20160117", month : "Jan", dom : "17", dow : "Sun", events : [] },
    	    { id : "20160118", month : "Jan", dom : "18", dow : "Mon", events : [] },
    	    { id : "20160119", month : "Jan", dom : "19", dow : "Tue", events : [] },
    	    { id : "20160120", month : "Jan", dom : "20", dow : "Wed", events : [] },
    	    { id : "20160121", month : "Jan", dom : "21", dow : "Thu", events : [] },
    	    { id : "20160122", month : "Jan", dom : "22", dow : "Fri", events : [] },
    	    { id : "20160123", month : "Jan", dom : "23", dow : "Sat" }
	]
},
{
	id : "20160124",
	days : [
    	    { id : "20160124", month : "Jan", dom : "24", dow : "Sun", events : [] },
    	    { id : "20160125", month : "Jan", dom : "25", dow : "Mon", events : [] },
    	    { id : "20160126", month : "Jan", dom : "26", dow : "Tue", events : [] },
    	    { id : "20160127", month : "Jan", dom : "27", dow : "Wed", events : [] },
    	    { id : "20160128", month : "Jan", dom : "28", dow : "Thu", events : [] },
    	    { id : "20160129", month : "Jan", dom : "29", dow : "Fri", events : [] },
    	    { id : "20160130", month : "Jan", dom : "30", dow : "Sat" }
	]
},
{
	id : "20160131",
	days : [
    	    { id : "20160131", month : "Jan", dom : "31", dow : "Sun", events : [] },
    	    { id : "20160201", month : "Feb", dom : "1", dow : "Mon", events : [] },
    	    { id : "20160202", month : "Feb", dom : "2", dow : "Tue", events : [] },
    	    { id : "20160203", month : "Feb", dom : "3", dow : "Wed", events : [] },
    	    { id : "20160204", month : "Feb", dom : "4", dow : "Thu", events : [] },
    	    { id : "20160205", month : "Feb", dom : "5", dow : "Fri", events : [] },
    	    { id : "20160206", month : "Feb", dom : "6", dow : "Sat" }
	]
}
];
*/