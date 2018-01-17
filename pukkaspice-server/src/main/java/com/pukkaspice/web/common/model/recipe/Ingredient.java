package com.pukkaspice.web.common.model.recipe;

public class Ingredient {
    
    private int ingredientId;
    
    private int recipeId;
    
    private float quantity;
    
    private Measurement measurement;
    
    private String description;
    
    public Ingredient() {
        super();
    }
    
    public Ingredient(int ingredientId, int recipeId, String description, float quantity, Measurement measurement) {
        super();
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.description = description;
        this.quantity = quantity;
        this.measurement = measurement;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
    
    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public Measurement getMeasurement() {
        return measurement;
    }

    public void setMeasurement(Measurement measurement) {
        this.measurement = measurement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
