angular.module("eventsApp", ["ngRoute"])

.controller("MainCtrl", ["$http", function($http) {
	var self = this;
	self.events = [ ];
	var list = function() {
		return $http.get("/status/events").then(function(response) {
			self.events = response.data;
		}, function(error) {
			alert("Error: " + error);
		});
	};
	list();

	self.add = function() {
		$http.post("save", self.event).then(list);
	};

	self.delete = function(event) {
		var deleteId = event.id;
		$http.delete("/status/delete",
		{
			params : { "id" : deleteId }
		}).then(list);
	};

	self.refresh = list;
}]);
