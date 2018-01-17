package com.pukkaspice.web.common.model.recipe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pukkaspice.web.common.model.user.UserSummary;

/**
 * This structure is based on the format for recipes as defined by schema.org
 * 
 * When rendered we should use the JSON-LD format so that google can process and display this better in the search results.
 * 
 * @author Chris
 */
public class Recipe extends RecipeSummary {
    
    private String alternativeIdeas;
    
    private int prepTime;
    
    private int cookTime;
    
    private int totalTime;
    
    private List<Instruction> recipeInstructions;
    
    private String recipeYield;
    
    private List<Ingredient> ingredients;
    
    private UserSummary userSummary;
    
    public Recipe() {
        super();
    }

    public String getAlternativeIdeas() {
        return alternativeIdeas;
    }

    public void setAlternativeIdeas(String alternativeIdeas) {
        this.alternativeIdeas = alternativeIdeas;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public List<Instruction> getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(List<Instruction> recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeYield() {
        return recipeYield;
    }

    public void setRecipeYield(String recipeYield) {
        this.recipeYield = recipeYield;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public UserSummary getUserSummary() {
        return userSummary;
    }

    public void setUserSummary(UserSummary userSummary) {
        this.userSummary = userSummary;
    }
    
    @JsonIgnore
    public String getJsonLd() {
        
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd"); 
        
        
        StringBuilder sb = new StringBuilder();
        sb.append("<script type=\"application/ld+json\">");
        sb.append("{");
        sb.append("\"@context\": \"http://schema.org/\",");
        sb.append("\"@type\": \"Recipe\",");
        sb.append("\"name\": \"" + super.name + "\",");
        sb.append("\"image\": \"" + getImageUrl() + "\",");
        
        if (super.datePublished == null) {
            sb.append("\"datePublished\": \"" + df.format(new Date()) + "\",");
        } else {
            sb.append("\"datePublished\": \"" + df.format(super.datePublished) + "\",");
        }
        
        sb.append("\"description\": \"" + super.description +"\",");
        sb.append(getJsonLdAggregateRating());
        sb.append("\"prepTime\": \"" + getDuration(prepTime) + "\",");
        sb.append("\"cookTime\": \"" + getDuration(cookTime) + "\",");
        sb.append("\"totalTime\": \"" + getDuration(prepTime + cookTime) + "\",");
        sb.append("\"recipeYield\": \"" + recipeYield + "\",");
        sb.append(getJsonLdIngredients());
        sb.append(getJsonLdInstructions());
        sb.append("}");
        sb.append("</script>");
        
        return sb.toString();
    }
    
    private String getJsonLdAggregateRating() {
        StringBuilder sb = new StringBuilder();
        if (super.reviewCount > 0) {
            sb.append("\"aggregateRating\": {");
            sb.append("\"@type\": \"AggregateRating\",");
            sb.append("\"ratingValue\": \"" + super.ratingValue + "\",");
            sb.append("\"reviewCount\": \"" + super.reviewCount + "\"");
            sb.append("},");
        }
        return sb.toString();
    }
    
    private String getJsonLdIngredients() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"ingredients\": [");

        int cnt = 1;
        for (Ingredient i : ingredients) {
            sb.append("\"");
            sb.append(i.getQuantity() + " " + i.getMeasurement().getShortName() + " " + i.getDescription() + "");
            
            if (cnt < ingredients.size()) {
                sb.append("\",");
            } else {
                sb.append("\"");
            }
            cnt++;
        }
        
        sb.append("],");
        return sb.toString();
    }
    
    private String getJsonLdInstructions() {
        StringBuilder sb = new StringBuilder();
        sb.append("\"recipeInstructions\":");
        sb.append("\"");

        int cnt = 1;
        for (Instruction i : recipeInstructions) {
            sb.append(cnt + ". " + i.getDescription() + "\\n ");
            cnt++;
        }
        
        sb.append("\"");
        return sb.toString();
    }
    
    /*
     * ISO8601 Duration
     */
    private String getDuration(int mins) {
       StringBuilder sb = new StringBuilder();
       
       sb.append("PT");
       int hoursVal = mins / 60;  
       if (hoursVal > 0) {
           sb.append(hoursVal + "H");
       }
       
       int minsVal = mins % 60;
       sb.append(minsVal + "M");
       
       
       return sb.toString();
    }

}
