angular.module("memberApp").controller("sideBarCtrl", function($scope) {
        $scope.menuItem = [
            {
                name: 'Profile Information',
                url: '#/profile'
            },
            {
                name: 'Add New Recipe',
                url: '#/add-recipe'
            },
            {
                name: 'My Recipes',
                url: '#/my-recipes'
            }
        ];
});