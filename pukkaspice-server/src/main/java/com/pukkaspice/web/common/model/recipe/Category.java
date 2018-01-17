package com.pukkaspice.web.common.model.recipe;

public enum Category {
    
    RECIPE_SEARCH_RESULTS ("recipe-search-results", "Recipe Search Results"),
    STARTER ("starters", "Starter Recipes"),
    MAIN ("main-meals", "Main Meal Recipes"),
    DESERTS ("deserts", "Desert Recipes"),
    BREADS ("breads", "Bread Recipes"),
    SAUCES ("sauces", "Sauce Recipes"),
    OTHER ("other", "Other Recipes"),
    EXAMPLE_RECIPES ("example-recipes", "Example Recipes"),;
    
    private String linkName;
    
    private String pageName;
    
    private Category(String linkName, String pageName) {
        this.linkName = linkName;
        this.pageName = pageName;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getPageName() {
        return pageName;
    }
    
    public static Category getCategoryForLinkName(String linkName) {
        for(Category c : Category.values()) {
            if (linkName.equals(c.getLinkName())) {
                return c;
            }
        }
        return Category.RECIPE_SEARCH_RESULTS; // default if not found
    }

}
