angular.module("memberApp").filter('true_false', function() {
    return function(text, length, end) {
        if (text) {
            return 'Yes';
        }
        return 'No';
    };
});