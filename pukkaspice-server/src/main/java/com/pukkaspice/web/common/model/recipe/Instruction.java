package com.pukkaspice.web.common.model.recipe;

public class Instruction {
    
    private int instructionId;
    
    private int recipeId;
    
    private String name;
    
    private String description;
    
    
    public Instruction() {
        super();
    }
    
    public Instruction(int instructionId, int recipeId, String name, String description) {
        super();
        this.instructionId = instructionId;
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
    }

    public int getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(int instructionId) {
        this.instructionId = instructionId;
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
    
}
