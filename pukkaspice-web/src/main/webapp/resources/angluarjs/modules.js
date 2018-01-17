var memberApp = angular.module("memberApp", [ "ngRoute", 'ngImgCrop', 'blockUI', 'ui.grid', 'ui.grid.edit', 'ui.grid.autoResize', 'ui.mask'])
    .config(function($routeProvider) {
        $routeProvider.when("/profile", {
            templateUrl : "/resources/angluarjs/views/profile.html",
            controller: "profileCtrl"
        });
        $routeProvider.when("/add-recipe", {
            templateUrl : "/resources/angluarjs/views/add-recipe.html",
            controller: "addRecipeCtrl"
        });
        $routeProvider.otherwise({
            templateUrl : "/resources/angluarjs/views/my-recipes.html",
            controller: "myRecipesCtrl"
        });
});