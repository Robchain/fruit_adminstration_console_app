package org.example.vendor.model;


public enum DrinkFlavor {
    STRAWBERRY("Strawberry", Ingredient.STRAWBERRIES, 100),
    BANANA("Banana", Ingredient.BANANAS, 120),
    MANGO("Mango", Ingredient.MANGO, 140);

    private final String displayName;
    private final Ingredient fruitIngredient;
    private final int gramsPerBlendedFruit100ml;

    DrinkFlavor(String displayName, Ingredient fruitIngredient, int gramsPerBlendedFruit100ml) {
        this.displayName = displayName;
        this.fruitIngredient = fruitIngredient;
        this.gramsPerBlendedFruit100ml = gramsPerBlendedFruit100ml;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Ingredient getFruitIngredient() {
        return fruitIngredient;
    }

    public int getGramsPerBlendedFruit100ml() {
        return gramsPerBlendedFruit100ml;
    }

    @Override
    public String toString() {
        return displayName;
    }
}