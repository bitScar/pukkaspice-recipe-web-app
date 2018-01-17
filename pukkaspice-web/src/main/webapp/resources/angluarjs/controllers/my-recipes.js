/*
 * It is wrong to think of this a class like Java -  It isn't. What this does is set-up a scope for the UI under the "scope" of a controller
 * to work. This will be run by the framework on navigating to the linked template as defined in $routeProvider. so what we are trying to achieve 
 * here is to set-up options for the grid (our basic data structure), some methods to be called (our actions), then to query the server in an async
 * way to go and get the data we need
 */
angular.module("memberApp")
    .controller("myRecipesCtrl", [ "$scope", "$timeout", "$log", "$location", "RecipeService", "DataShareService", "blockUI",  function($scope, $timeout, $log, $location, RecipeService, DataShareService, blockUI) {

    /* To enable cell editing you must include "ui.grid.edit" feature in module and "ui-grid-edit" directive on template */
    $scope.gridOptionsStarters = {
        enableColumnResizing: false,
        enableColumnMenus: false,
        enableHorizontalScrollbar: 0,
        
        columnDefs: [
          { name: 'name', field: 'name', displayName: 'Recipe Name' },
          { name: 'datePublished', field: 'datePublished', type: 'date', cellFilter: 'date:\'dd-MMM-yyyy\'', width: 120 },
          { name: 'recipeState', field: 'recipeState', displayName: 'Publish State', width: 140 },
          { name: 'editRecipe', displayName: '', cellTemplate: '<div style="text-align: center"><button data-toggle="tooltip" data-placement="top" title="edit recipe" class="btn btn-small icon-edit" ng-click="grid.appScope.editRecipe(row.entity.recipeId)"></button></div>', width: 60 },
          { name: 'deleteRecipe', displayName: '', cellTemplate: '<div style="text-align: center"><button data-toggle="tooltip" data-placement="top" title="delete recipe" class="btn btn-small icon-trash" ng-click="grid.appScope.deleteRecipe(row.entity.recipeId)"></button></div>', width: 60 }
        ],
        
        data: []
    
    };
    $scope.gridOptionsMainMeals = {};
    $scope.gridOptionsDeserts = {};
    $scope.gridOptionsBreads = {};
    $scope.gridOptionsSauces = {};
    $scope.gridOptionsOther = {};
    
    angular.copy($scope.gridOptionsStarters, $scope.gridOptionsMainMeals);
    angular.copy($scope.gridOptionsStarters, $scope.gridOptionsDeserts);
    angular.copy($scope.gridOptionsStarters, $scope.gridOptionsBreads);
    angular.copy($scope.gridOptionsStarters, $scope.gridOptionsSauces);
    angular.copy($scope.gridOptionsStarters, $scope.gridOptionsOther);
    
    $scope.allGridOptions = [];
    $scope.allGridOptions.push($scope.gridOptionsStarters);
    $scope.allGridOptions.push($scope.gridOptionsMainMeals);
    $scope.allGridOptions.push($scope.gridOptionsDeserts);
    $scope.allGridOptions.push($scope.gridOptionsBreads);
    $scope.allGridOptions.push($scope.gridOptionsSauces);
    $scope.allGridOptions.push($scope.gridOptionsOther);
    

    /* Callback functions for our UI to call */
    
    $scope.editRecipe = function(recipeId) {
        DataShareService.shareParams(recipeId);
        $location.url("/add-recipe");
    };
    
    
    $scope.deleteRecipe = function(recipeId) {
        RecipeService.deleteRecipe(recipeId)
            .then(function successCallback(response) {
                // this callback will be called asynchronously
                // when the response is available
                
                var gridOptionToDeleteFrom = null;
                var recipeToDelete = null;
                var i, j;
                for (i = 0; i < $scope.allGridOptions.length; i++) {
                    for (j = 0; j < $scope.allGridOptions[i].data.length; j++) {
                        if ($scope.allGridOptions[i].data[j].recipeId == recipeId) {
                            gridOptionToDeleteFrom = i;
                            recipeToDelete = j;
                        }
                    }    
                }
                
                debugger;
                $scope.allGridOptions[gridOptionToDeleteFrom].data.splice(recipeToDelete, 1);
                
                blockUI.stop();
            }, function errorCallback(response) {
                blockUI.stop();
            });
    };
    
    /* Getting data to set-up the screen in an asyn way */
    
    RecipeService.getRecipes()
        .then(function(value) {
            var index = 0;
            for (index = 0; index < value.data.length; index++) {
                switch (value.data[index].category) {
                case 'STARTER':
                    $scope.gridOptionsStarters.data.push(value.data[index]);
                    break;
                case 'MAIN':
                    $scope.gridOptionsMainMeals.data.push(value.data[index]);
                    break;
                case 'DESERTS':
                    $scope.gridOptionsDeserts.data.push(value.data[index]);
                    break;
                case 'BREADS':
                    $scope.gridOptionsBreads.data.push(value.data[index]);
                    break;
                case 'SAUCES':
                    $scope.gridOptionsSauces.data.push(value.data[index]);
                    break;
                default:
                    $scope.gridOptionsOther.data.push(value.data[index]);
                    break;
                }
            }
            
            blockUI.stop();
        }, function(reason) {
            blockUI.stop();
            if (reason.status = 403) {
                window.location = "/secure/login";
            } 
        }, function(value) {
            blockUI.stop();
        });
    
} ]);
