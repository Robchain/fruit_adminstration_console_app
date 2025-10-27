package org.example.vendor.exception;


import org.example.vendor.model.Ingredient;


public class InsufficientInventoryException extends Exception {
    private final Ingredient ingredient;
    private final double required;
    private final double available;

    public InsufficientInventoryException(Ingredient ingredient, double required, double available) {
        super(String.format("Insufficient %s: required %.2f %s, but only %.2f %s available",
                ingredient.getDisplayName(), required, ingredient.getUnit(),
                available, ingredient.getUnit()));
        this.ingredient = ingredient;
        this.required = required;
        this.available = available;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getRequired() {
        return required;
    }

    public double getAvailable() {
        return available;
    }
}
