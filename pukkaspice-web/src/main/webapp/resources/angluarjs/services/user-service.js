/*
 * Factories are a way of splitting out stuff that is common throughout the 
 * application. Typically we can use these for REST calls, etc...
 */
angular.module("memberApp")
    .factory('UserService', ["$http", "blockUI", "ErrorService", function($http, blockUI, ErrorService) {

    return {
        
        getCurrentUser: function(userSummary, scope) {
            blockUI.start("Getting profile...");
            
            $http.get('/rest/user-service/current-user')
                .then(function(value) {
                    angular.copy(value.data, userSummary);
                    scope.visibleImage = userSummary.imageUrl;
                    blockUI.stop();
                }, function(reason) {
                    ErrorService.handleError(reason);
                    blockUI.stop();
                }, function(value) {
                    blockUI.stop();
                });
        },
    
        saveCurrentUser: function(userSummary, scope) {
            blockUI.start("Saving profile...");
            
            $http.post('/rest/user-service/current-user', userSummary)
                .then(function(value) {
                    angular.copy(value.data, userSummary);
                    scope.profileForm.$setPristine();
                    blockUI.stop();
                }, function(reason) {
                    ErrorService.handleError(reason);
                    blockUI.stop();
                }, function(value) {
                    blockUI.stop();
                });
        }
        
    };
}]);