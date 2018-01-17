package com.pukkaspice.web.dao.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pukkaspice.web.common.model.recipe.Category;
import com.pukkaspice.web.common.model.recipe.Ingredient;
import com.pukkaspice.web.common.model.recipe.Instruction;
import com.pukkaspice.web.common.model.recipe.Recipe;
import com.pukkaspice.web.common.model.recipe.RecipeSearch;
import com.pukkaspice.web.common.model.recipe.RecipeSummary;
import com.pukkaspice.web.common.model.user.UserSummary;

public interface RecipeMapper {

    Recipe getRecipe(@Param("recipeId") int recipeId);
    
    List<RecipeSummary> getRecipeSummariesInCategory(@Param("category") Category category, @Param("skipRows") int skipRows, @Param("pageSize") int pageSize);
    
    List<RecipeSummary>getRandomRecipeSummaries();
    
    int getCategoryCount(@Param("category") Category category);
    
    void insertRecipe(Recipe recipe);
    
    void updateRecipe(Recipe recipe);
    
    void insertIngredient(Ingredient ingredient);
    
    void deleteRemovedIngredients(Recipe recipe);
    
    void updateIngredient(Ingredient ingredient);
    
    void insertInstruction(Instruction instruction);
    
    void deleteRemovedInstructions(Recipe recipe);
    
    void updateInstruction(Instruction instruction);

    List<RecipeSummary> getRecipeSummariesForUser(UserSummary userSummary);

    void deleteRecipe(@Param("recipeId") int recipeId);

    List<RecipeSummary> searchRecipeSummaries(RecipeSearch recipeSearch);
    
}
