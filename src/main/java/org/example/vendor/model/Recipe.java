package org.example.vendor.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;


public class Recipe {

    // Base recipe constants for 100ml of drink
    private static final int BLENDED_FRUIT_ML_PER_100ML = 50;
    private static final int ICE_ML_PER_100ML = 30;
    private static final int CONDENSED_MILK_ML_PER_100ML = 20;
    private static final int SUGAR_G_PER_100ML = 8;

    private final DrinkFlavor flavor;
    private final DrinkSize size;

    public Recipe(DrinkFlavor flavor, DrinkSize size) {
        this.flavor = Objects.requireNonNull(flavor, "Flavor cannot be null");
        this.size = Objects.requireNonNull(size, "Size cannot be null");
    }

    /**
     * Calculates the required ingredients for this recipe.
     *
     * @return Map of ingredients to required amounts
     */
    public Map<Ingredient, Double> calculateRequiredIngredients() {
        Map<Ingredient, Double> ingredients = new HashMap<>();

        double volumeMultiplier = size.getVolumeInMl() / 100.0;

        // Calculate blended fruit needed
        double blendedFruitMl = BLENDED_FRUIT_ML_PER_100ML * volumeMultiplier;

        // Calculate actual fruit needed based on flavor
        double fruitGrams = (blendedFruitMl / 100.0) * flavor.getGramsPerBlendedFruit100ml();

        // Add fruit ingredient
        ingredients.put(flavor.getFruitIngredient(), fruitGrams);

        // Add other ingredients
        ingredients.put(Ingredient.ICE, ICE_ML_PER_100ML * volumeMultiplier);
        ingredients.put(Ingredient.CONDENSED_MILK, CONDENSED_MILK_ML_PER_100ML * volumeMultiplier);
        ingredients.put(Ingredient.SUGAR, SUGAR_G_PER_100ML * volumeMultiplier);

        return ingredients;
    }

    public DrinkFlavor getFlavor() {
        return flavor;
    }

    public DrinkSize getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Recipe recipe = (Recipe) obj;
        return flavor == recipe.flavor && size == recipe.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(flavor, size);
    }

    @Override
    public String toString() {
        return String.format("%s %s Drink", size.getDisplayName(), flavor.getDisplayName());
    }
}