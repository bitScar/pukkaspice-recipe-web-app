package com.pukkaspice.web.service;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pukkaspice.web.common.image.ImageCategory;
import com.pukkaspice.web.common.image.ImageInfo;
import com.pukkaspice.web.common.image.ImageUtil;
import com.pukkaspice.web.common.jersey.InsuficientPermissionsException;
import com.pukkaspice.web.common.model.recipe.Category;
import com.pukkaspice.web.common.model.recipe.ImageLocation;
import com.pukkaspice.web.common.model.recipe.Ingredient;
import com.pukkaspice.web.common.model.recipe.Instruction;
import com.pukkaspice.web.common.model.recipe.Recipe;
import com.pukkaspice.web.common.model.recipe.RecipeSearch;
import com.pukkaspice.web.common.model.recipe.RecipeState;
import com.pukkaspice.web.common.model.recipe.RecipeSummary;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.SecurityController;
import com.pukkaspice.web.dao.mybatis.mappers.RecipeMapper;

@Service("RecipeService") // @Service annotation is to identify that it's a Spring component that provides business services to another layer 
@Repository // @Repository annotation indicates that the class contains data access logic and instructs Spring to translate the vendor-specific exceptions to Spring's DataAccessException hierarchy
public class RecipeService {
    
    private static final int PAGE_SIZE = 6;
    
    @Autowired
    private RecipeMapper recipeMapper;
    
    @Autowired
    private SecurityController securityController;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private ImageUtil imageUtil;
    
    
    public int getPageCount(Category category) {
        int categoryCount = recipeMapper.getCategoryCount(category);
        int pageCount = categoryCount / PAGE_SIZE;
        if (categoryCount % PAGE_SIZE > 0) {
            pageCount++;
        }
        
        return pageCount;
    }
    
    private int calculateSkipRows(int page) {
        return PAGE_SIZE * (page - 1);
    }
    
    public List<RecipeSummary> getRecipeSummariesInCategory(Category category, int page) {
        List<RecipeSummary> recipeSummariesInCategory = recipeMapper.getRecipeSummariesInCategory(category, calculateSkipRows(page), PAGE_SIZE);
        return recipeSummariesInCategory;
    }
    
    public List<RecipeSummary> getRandomRecipes() {
        List<RecipeSummary> recipeSummariesInCategory = recipeMapper.getRandomRecipeSummaries();
        return recipeSummariesInCategory;
    }
    
    public Recipe getRecipe(int recipeId) {
        Recipe recipe = recipeMapper.getRecipe(recipeId);
        recipe.getUserSummary().buildImageUrl();
        
        return recipe;
    }

    @Transactional
    public Recipe saveRecipe(Recipe recipe) {
        boolean imageHasBeenUploaded = recipe.getImageBase64() != null && !recipe.getImageBase64().trim().equals(""); 
        
        if (imageHasBeenUploaded == true) {  
            
            ImageInfo imageInfo = new ImageInfo(RandomStringUtils.randomAlphanumeric(8) + ".png", ImageCategory.RECIPE);
            String newImageUrl = imageUtil.writeBase64Image(recipe.getImageBase64(), imageInfo);
            
            ImageInfo imageInfoToDelete = new ImageInfo(recipe.getImageName(), ImageCategory.RECIPE);
            imageUtil.deleteImage(imageInfoToDelete);
            
            recipe.setImageName(newImageUrl);
            recipe.setImageLocation(ImageLocation.CLOUDINARY);
        }
        
        if (recipe.isPublish() == true && recipe.getRecipeId() > 0) {
            recipe.setRecipeState(RecipeState.AWAITING_APPROVAL);
        } else if (recipe.isPublish() == false && recipe.getRecipeId() > 0) {
            recipe.setRecipeState(RecipeState.UNPUBLISHED);
        }
        
        if (recipe.getRecipeId() == 0) { 
            UserSummary currentUser = securityController.getCurrentUser();
            recipe.setUserSummary(currentUser);
            recipeMapper.insertRecipe(recipe);
        } else {
            if (securityService.canSave(recipe)) {
                recipeMapper.updateRecipe(recipe);
            } else {
                throw new InsuficientPermissionsException();
            }
        }
        
        saveIngredients(recipe);
        saveInstructions(recipe);
        
        return recipe;
    }
    
    private void saveIngredients(Recipe recipe) {
        recipeMapper.deleteRemovedIngredients(recipe);
        
        for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.getIngredientId() == 0) {
                ingredient.setRecipeId(recipe.getRecipeId());
                recipeMapper.insertIngredient(ingredient);
            } else {
                recipeMapper.updateIngredient(ingredient);
            }
        }
    }
    
    private void saveInstructions(Recipe recipe) {
        recipeMapper.deleteRemovedInstructions(recipe);
        
        for (Instruction instruction : recipe.getRecipeInstructions()) {
            if (instruction.getInstructionId() == 0) {
                instruction.setRecipeId(recipe.getRecipeId());
                recipeMapper.insertInstruction(instruction);
            } else {
                recipeMapper.updateInstruction(instruction);
            }
        }
    }

    public List<RecipeSummary> getRecipeSummariesForUser(UserSummary currentUser) {
        return recipeMapper.getRecipeSummariesForUser(currentUser);
    }

    public void deleteRecipe(int recipeId) {
        if (securityService.canDeleteRecipe(recipeId)) {
            recipeMapper.deleteRecipe(recipeId);
        } else {
            throw new InsuficientPermissionsException();
        }
    }

    public List<RecipeSummary> searchRecipeSummaries(RecipeSearch recipeSearch) {
        return recipeMapper.searchRecipeSummaries(recipeSearch);
    }

}
