angular.module("memberApp")
    .controller("addRecipeCtrl", ["$scope", "$timeout", "$log", "RecipeService", "DataShareService", function($scope, $timeout, $log, RecipeService, DataShareService) {
    
    $log.log('Entering addRecipeCtrl');
    
    // define variables
    $scope.editingShow = false;
    
    $scope.visibleImage = '/resources/assets/images/test-square.png';
    $scope.croppedRecipeImage = '';
    
    $scope.recipeModel = 
    {
        "recipeId": 0,
        "name": "",
        "datePublished": null,
        "description": "",
        "category": "",
        "imageLocation": "DYNAMIC",
        "imageName": "",
        "imageBase64": null,
        "imageUrl": "",
        "reviewCount": 0,
        "ratingValue": 0.0,
        "alternativeIdeas": "",
        "prepTime": null,
        "cookTime": null,
        "totalTime": null,
        "recipeInstructions":[],
        "ingredients": [],
        "publish":false
        
    };
    
    $scope.originalRecipeModel = angular.copy($scope.recipeModel);
    
    // define functions
    $scope.editPhoto = function() {
        $scope.editingShow = !$scope.editingShow;    
    };
    
    $scope.editPhotoOk = function() {
        debugger;
        
        $scope.visibleImage = $scope.croppedRecipeImage; 
        $scope.recipeModel.imageBase64 = $scope.croppedRecipeImage;
        $scope.recipeForm.$setDirty();
        $scope.editingShow = !$scope.editingShow; 
    };
    
    $scope.editPhotoCancel = function() {
        debugger;
        
        if ($scope.recipeModel.imageBase64 != null) {
            $scope.visibleImage = $scope.recipeModel.imageBase64; 
        } else {
            
            if ($scope.recipeModel.imageUrl == '') {
                $scope.visibleImage = '/resources/assets/images/test-square.png';
            } else {
                $scope.visibleImage = $scope.recipeModel.imageUrl;
            }
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
        angular.element(document.querySelector('#recipePhotofileInput')).on('change', handleFileSelect);
    }, 1000, false);
    
    
    $scope.addIngredient = function () {
        $scope.recipeModel.ingredients.push({ ingredientId: 0, quantity: null, measurement: 'GRAMS', description: "" });
    };
    
    $scope.addInstruction = function () {
        $scope.recipeModel.recipeInstructions.push({ instructionId: 0, name: '', description: '' });
    };

    $scope.deleteIngredient = function (index) {
        $scope.recipeModel.ingredients.splice(index, 1);
    };
    
    $scope.deleteInstruction = function (index) {
        $scope.recipeModel.recipeInstructions.splice(index, 1);
    };
    
    $scope.saveRecipe = function (recipe) {
        RecipeService.saveRecipe($scope.recipeModel);
    };
    
    $scope.cancelChanges = function() {
        debugger;
        
        if ($scope.recipeModel.imageUrl == '') {
            $scope.visibleImage = '/resources/assets/images/test-square.png';
        } else {
            $scope.visibleImage = $scope.recipeModel.imageUrl;
        }
        
        
        angular.copy($scope.originalRecipeModel, $scope.recipeModel);
        $scope.recipeForm.$setPristine();
    };
    
    
    var sharedParams = DataShareService.getSharedParamsThenDelete();
    if (sharedParams && sharedParams.length > 0) {
        RecipeService.getRecipe(sharedParams[0], $scope, $scope.recipeModel);
    }
    
}]);
