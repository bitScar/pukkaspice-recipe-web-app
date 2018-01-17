package com.pukkaspice.web.common.model.recipe;

import java.util.Date;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Based on http://schema.org/Recipe
 * 
 * @author Chris
 */
public class RecipeSummary {

    protected int recipeId;
    
    protected String name;
    
    protected Date datePublished;
    
    protected boolean publish;
    
    protected RecipeState recipeState = RecipeState.UNPUBLISHED;
    
    protected String description;

    protected Category category;
    
    protected ImageLocation imageLocation;
    
    protected String imageName;
    
    protected String imageBase64;
    
    protected String imageUrl;
    
    protected String recipeCuisine;
    
    protected int reviewCount;
    
    protected float ratingValue;
    
    public RecipeSummary() {
        super();
    }
    
    public RecipeSummary(int recipeId, String name, Date datePublished, String description, Category category, ImageLocation imageLocation, String imageName) {
        super();
        this.recipeId = recipeId;
        this.name = name;
        this.datePublished = datePublished;
        this.description = description;
        this.category = category;
        this.imageLocation = imageLocation;
        this.imageName = imageName;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }
    
    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }
    
    public RecipeState getRecipeState() {
        return recipeState;
    }

    public void setRecipeState(RecipeState recipeState) {
        this.recipeState = recipeState;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public ImageLocation getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(ImageLocation imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
    
    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public float getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(float ratingValue) {
        this.ratingValue = ratingValue;
    }

    /**
     * Gets a short description of the recipe that can be used as a summary in the recipe listings.
     */
    @JsonIgnore
    public String getShortDescription() {
        if (this.description != null && this.description.length() > 200) {
            return this.description.substring(0, 200) + "...";
        } else if (this.description != null ) { 
            return this.description;
        } else {
            return "";
        }
    }
    
    /**
     * Gets the image URL.
     */
    public String getImageUrl() {
        StringBuilder imageUrl = new StringBuilder();
        
        switch (imageLocation) {
        case STATIC:
            imageUrl.append("/resources/assets/images/recipes/");
            imageUrl.append(imageName);
            break;
        case DYNAMIC:
            imageUrl.append("/image/recipes/");
            imageUrl.append(imageName);
            break;
        case CLOUDINARY:
            imageUrl.append(imageName);
            break;
        default:
            break;
        }
        
        return imageUrl.toString();
    }
    
    @JsonIgnore
    public String getSeoFriendlyRecipeUrl() {
        StringBuilder recipeUrl = new StringBuilder();
        recipeUrl.append("/recipe");
        recipeUrl.append("/" + category.getLinkName());
        recipeUrl.append("/" + getSeoFriendyString(name));
        recipeUrl.append("/" + recipeId);
        
        return recipeUrl.toString();
    }
    
    @JsonIgnore
    public static String getSeoFriendyString(String string) {
        PolicyFactory policy = new HtmlPolicyBuilder().toFactory();
        String safeHTML = policy.sanitize(string);
        String safeHTMLWhitespaceRemoved = safeHTML.replaceAll("\\s", "-");
        String safeHTMLWhitespaceRemovedLetterNumbersOnly = safeHTMLWhitespaceRemoved.replaceAll("[^A-Za-z0-9\\-]", "");
    
        try {
            return URIUtil.encodeAll(safeHTMLWhitespaceRemovedLetterNumbersOnly.toLowerCase());
        } catch (URIException e) {
            e.printStackTrace();
        }
        
        return "";
    }

}
