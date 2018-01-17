angular.module("memberApp").controller("profileCtrl", [ "$scope", "$timeout", "UserService", function($scope, $timeout, UserService) {

    // define variables
    $scope.editingShow = false;
    
    $scope.visibleImage = '/resources/assets/images/test-square.png';
    $scope.croppedProfileImage = '';
    $scope.userSummary = {};
    
    // define functions
    $scope.editPhoto = function() {
        $scope.editingShow = !$scope.editingShow;    
    };
    
    $scope.editPhotoOk = function() {
        $scope.visibleImage = $scope.croppedProfileImage; 
        $scope.userSummary.imageBase64 = $scope.croppedProfileImage;
        $scope.profileForm.$setDirty();
        $scope.editingShow = !$scope.editingShow; 
    };
    
    $scope.editPhotoCancel = function() {
        if ($scope.userSummary.imageBase64 != null) {
            $scope.visibleImage = $scope.userSummary.imageBase64; 
        } else {
            $scope.visibleImage = $scope.userSummary.imageUrl;
        }
        
        $scope.editingShow = !$scope.editingShow;    
    };

    var handleFileSelect = function(evt) {
        var file = evt.currentTarget.files[0];
        var reader = new FileReader();
        reader.onload = function(evt) {
            $scope.$apply(function($scope) {
                $scope.visibleImage = evt.target.result;
            });
        };
        reader.readAsDataURL(file);
    };

    $timeout(function() {
        angular.element(document.querySelector('#profilePhotofileInput')).on('change', handleFileSelect);
    }, 1000, false);

    $scope.saveUserSummary = function(profileForm) {
        if (profileForm.$valid === true) {
            UserService.saveCurrentUser($scope.userSummary, $scope);
        }
    };

    $scope.cancelChanges = function() {
        $scope.visibleImage = $scope.userSummary.imageUrl;
        $scope.userSummary.imageBase64 = null;
        $scope.profileForm.$setPristine();
    };
    
    
    // do set-up
    UserService.getCurrentUser($scope.userSummary, $scope);
    
} ]);