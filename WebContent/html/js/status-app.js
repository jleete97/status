angular.module("statusApp", ["ngRoute"])

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

	self.remove = function(event) {
		var removeId = event.id;
		$http.delete("/status/remove",
		{
			params : { "id" : removeId }
		}).then(list);
	};

	self.refresh = list;
}])

// Tab functionality courtesy of https://jsfiddle.net/jccrosby/eRGT8/

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