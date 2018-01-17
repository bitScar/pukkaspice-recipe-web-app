package com.pukkaspice.web.common.model.recipe;

public enum Measurement {
    GRAMS       ("Grams",       "g",        Type.METRIC),
    KILOS       ("Kilos",       "kg",       Type.METRIC),
        
    MILLILITER  ("Milliliter",  "ml",       Type.METRIC),
    LITER       ("liter",       "liter",        Type.METRIC),
    
    TEASPOONS   ("Teaspoon",    "tps",      Type.US),
    TABLESPOONS ("Tablespoon",  "tbls",     Type.US),
    CUP         ("Cups",        "Cup",      Type.US),
    POUND       ("Pound",       "Pound",    Type.US),
    
    FLUID_OUNCE ("Fluid Ounce", "fl oz",    Type.US),
    QUART       ("Quart",       "qt",       Type.US),
    GALLON      ("Gallon",      "gal",      Type.US),
    
    OUNCE       ("Ounce",       "oz",       Type.US),
    
    QUANTITY    ("",            "",         Type.UNIVERSAL),
    
    PINCH       ("Pinch",       "pinch",    Type.UNIVERSAL),
    DASH        ("Dash",        "dash",     Type.UNIVERSAL);

    private String fullName; 
    private String shortName;
    private Type type;
    
    Measurement(String fullName, String shortName, Type type) {
        this.fullName = fullName;
        this.shortName = shortName;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.shortName;
    }
    
    private static enum Type {
        METRIC,
        US,
        UNIVERSAL
    }
}
