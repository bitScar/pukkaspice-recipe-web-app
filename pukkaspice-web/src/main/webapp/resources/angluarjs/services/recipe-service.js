/*
 * Factories are a way of splitting out stuff that is common throughout the 
 * application. Typically we can use these for REST calls, etc...
 */
angular.module("memberApp")
    .factory('RecipeService', ["$http", "blockUI", "ErrorService", function($http, blockUI, ErrorService) {

    return {
        
        getRecipe: function(recipeId, scope, recipe) {
            blockUI.start("Getting recipe data...");
            
            $http.get('/rest/recipe-service/recipe/' + recipeId)
                .then(function(value) {
                    debugger;
                    
                    angular.copy(value.data, recipe);
                    scope.visibleImage = recipe.imageUrl;
                    angular.copy(scope.recipeModel, scope.originalRecipeModel);
                    
                    blockUI.stop();
                }, function(reason) {
                    debugger;
                    blockUI.stop();
                    
                    
                    if (reason.status = 403) {
                        window.location = "/secure/login";
                    }
                    
                }, function(value) {
                    debugger;
                    blockUI.stop();
                });
        },
        
        saveRecipe: function(recipeModel) {
            
            //TODO handle failures properly
            
            blockUI.start("Saving recipe...");
            $http.post('/rest/recipe-service/recipe', recipeModel)
                .then(function(value) {
                	debugger;
                	angular.copy(value.data, recipeModel);
                	blockUI.stop();
                }, function(reason) {
                    debugger;
                    blockUI.stop();
                }, function(value) {
                    debugger;
                    blockUI.stop();
                });
        },
        
        getRecipes: function() {
            blockUI.start("Getting recipe data...");
            
            return $http.get('/rest/recipe-service/recipe');
        },
        
        deleteRecipe: function(recipeId) {
            blockUI.start("Deleting recipe...");
            
            debugger;
            
            return $http({
                method: 'DELETE',
                url:'/rest/recipe-service/recipe/' + recipeId
              });
        },
        
    };
}]);