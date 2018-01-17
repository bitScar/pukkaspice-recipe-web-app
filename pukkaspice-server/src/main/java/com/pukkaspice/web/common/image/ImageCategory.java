package com.pukkaspice.web.common.image;

public enum ImageCategory {

    PROFILE("profiles"),
    RECIPE("recipes");
    
    private String location;

    private ImageCategory(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }
    
}
