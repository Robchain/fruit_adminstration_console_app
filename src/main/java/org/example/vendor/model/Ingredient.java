package org.example.vendor.model;


public enum Ingredient {
    // Fruits (measured in grams)
    STRAWBERRIES("strawberries", "g"),
    BANANAS("bananas", "g"),
    MANGO("mango", "g"),

    // Liquids (measured in milliliters)
    ICE("ice", "ml"),
    CONDENSED_MILK("condensed milk", "ml"),

    // Solids (measured in grams)
    SUGAR("sugar", "g");

    private final String displayName;
    private final String unit;

    Ingredient(String displayName, String unit) {
        this.displayName = displayName;
        this.unit = unit;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
