angular.module("memberApp")
    .factory('DataShareService', [ "$rootScope", function($rootScope) {

    return {
        
        shareParams: function() {
            $rootScope.sharedData = arguments;
        },
        
        getSharedParamsThenDelete: function() {
            var copiedData = new Array();
            
            angular.copy($rootScope.sharedData, copiedData);
            $rootScope.sharedData = undefined;
            
            return copiedData;
        }
        
    };
}]);