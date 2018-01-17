package com.pukkaspice.web.rest.members;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pukkaspice.web.common.model.recipe.Recipe;
import com.pukkaspice.web.common.model.recipe.RecipeSummary;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.SecurityController;
import com.pukkaspice.web.service.RecipeService;

@Component("RecipeEndPoint")
@Path("/recipe-service")
public class RecipeEndPoint {
    
    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private SecurityController securityController;

    @GET
    @Path("/recipe")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RecipeSummary> getRecipe() {
        UserSummary currentUser = securityController.getCurrentUser();
        return recipeService.getRecipeSummariesForUser(currentUser);
    }
    
    @GET
    @Path("/recipe/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Recipe getRecipe(@PathParam("recipeId") int recipeId) {
        return recipeService.getRecipe(recipeId);
    }
    
    @POST
    @Path("/recipe")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Recipe saveRecipe(Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }
    
    @DELETE
    @Path("/recipe/{recipeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteRecipe(@PathParam("recipeId") int recipeId) {
        recipeService.deleteRecipe(recipeId);
    }
}
