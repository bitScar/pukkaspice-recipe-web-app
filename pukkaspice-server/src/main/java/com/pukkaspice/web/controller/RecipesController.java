package com.pukkaspice.web.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pukkaspice.web.common.model.recipe.Category;
import com.pukkaspice.web.common.model.recipe.Recipe;
import com.pukkaspice.web.common.model.recipe.RecipeSearch;
import com.pukkaspice.web.common.model.recipe.RecipeSummary;
import com.pukkaspice.web.service.RecipeService;

@Controller
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    private static final Logger logger = LoggerFactory.getLogger(RecipesController.class);

    //TODO get this to return top rated or random recipes or ones based on time of year (festivals etc...)
    @RequestMapping(value = "/recipes", method = RequestMethod.GET)
    public String home(Locale locale, Model model) {
        model.addAttribute("active", "recipes");
        model.addAttribute("recipeSearchForm", new RecipeSearch());
        
        List<RecipeSummary> recipeSummaries = recipeService.getRandomRecipes();
        
        model.addAttribute("pageCount", 0);
        model.addAttribute("currentPage", 0);
        model.addAttribute("category", Category.EXAMPLE_RECIPES);
        model.addAttribute("recipeSummaries", recipeSummaries);
        model.addAttribute("title", "Pukka Spice Recipes");
        model.addAttribute("metaDescription", "Recipes and have been tried and tasted by cooks that know their spice from their herbs. We have various different categories to choose from - I'm sure you will find somthing you like.");

        return "recipes";
    }
    
    /**
     * Gets a list of recipes for a given category.
     */
    @RequestMapping(value = "/recipes/{categoryLinkName}/{page}")
    public String getRecipesByCategory(
        @PathVariable(value = "categoryLinkName") String categoryLinkName, @PathVariable(value = "page") int page,
        Locale locale, Model model) {
        
        model.addAttribute("active", "recipes");
        model.addAttribute("recipeSearchForm", new RecipeSearch());
        
        Category category = Category.getCategoryForLinkName(categoryLinkName);

        int pageCount = recipeService.getPageCount(category);
        List<RecipeSummary> recipeSummaries = recipeService.getRecipeSummariesInCategory(category, page);
        
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("category", category);
        model.addAttribute("recipeSummaries", recipeSummaries);
        model.addAttribute("title", "Pukka Spice" + category.getPageName());
        model.addAttribute("metaDescription", "Find here " + category.getPageName() + ", and lots more. If you are looking for something with spice then this might just be the place for you.");
        
        return "recipes";
    }
    
    /**
     * Gets a single recipe.
     */
    @RequestMapping(value = "/recipe/{category}/{name}/{recipeId}")
    public String getRecipe(
        @PathVariable(value = "category") String category,
        @PathVariable(value = "name") String name, 
        @PathVariable(value = "recipeId") int recipeId,
        Locale locale, Model model) {
        
        model.addAttribute("active", "recipes");
        model.addAttribute("recipeSearchForm", new RecipeSearch());

        Recipe recipe = recipeService.getRecipe(recipeId);
        model.addAttribute("recipe", recipe);
        
        model.addAttribute("title", recipe.getName());
        model.addAttribute("metaDescription", "Ingredients and cooking instructions for " + recipe.getName() + ". We will try to provide you with all the knowedge and techniques for you to succeed with this recipe.");        
        
        return "recipe";
    }
    
    /**
     * Gets a list of recipes for given search parameters.
     */
    @RequestMapping(value = "/searchRecipes", method = RequestMethod.POST)
    public String searchRecipes(@ModelAttribute("recipeSearchForm") RecipeSearch recipeSearch, BindingResult result, Model model, RedirectAttributes redir) {
        logger.debug("Searching for recipes with the name: " + recipeSearch.getName());
        
        model.addAttribute("active", "recipes");
        model.addAttribute("recipeSearchForm", recipeSearch);
        
        List<RecipeSummary> recipeSummaries  = recipeService.searchRecipeSummaries(recipeSearch);
        
        
        if (recipeSummaries.size() > 0) {
            model.addAttribute("pageCount", 1);
            model.addAttribute("currentPage", 1);
        } else {
            model.addAttribute("pageCount", 0);
            model.addAttribute("currentPage", 0);
        }
        
        
        model.addAttribute("category", Category.RECIPE_SEARCH_RESULTS);
        model.addAttribute("recipeSummaries", recipeSummaries);
        
        return "recipes";
    }
}
