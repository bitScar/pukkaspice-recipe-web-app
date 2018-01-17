angular.module("memberApp")
    .factory('ErrorService', [ function() {

    return {
        
        handleError: function(reason) {
            
            $('#alertModal').modal('show');
            
//            debugger;
            
            // need to find a animated modal that actually works
        }
        
    };
}]);