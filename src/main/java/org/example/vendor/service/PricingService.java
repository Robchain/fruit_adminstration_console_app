package org.example.vendor.service;

import org.example.vendor.model.Ingredient;
import org.example.vendor.model.Recipe;
import org.example.vendor.model.DrinkFlavor;
import org.example.vendor.model.DrinkSize;
import java.util.List;


public interface PricingService {

    /**
     * Calculates the price for a single-flavor drink.
     *
     * @param recipe the recipe to price
     * @return the calculated price
     */
    double calculatePrice(Recipe recipe);

    /**
     * Calculates the price for a mixed-flavor drink.
     *
     * @param flavors the flavors in the mixed drink
     * @param size the size of the drink
     * @return the calculated price
     */
    double calculateMixedDrinkPrice(List<DrinkFlavor> flavors, DrinkSize size);

    /**
     * Gets the cost per unit for a specific ingredient.
     *
     * @param ingredient the ingredient
     * @return cost per unit
     */
    double getIngredientCost(Ingredient ingredient);

    /**
     * Calculates the profit margin for a drink.
     *
     * @param recipe the recipe
     * @return profit margin as a percentage
     */
    double calculateProfitMargin(Recipe recipe);
}