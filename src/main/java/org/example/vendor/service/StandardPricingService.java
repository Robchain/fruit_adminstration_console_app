package org.example.vendor.service;

import org.example.vendor.model.*;
import java.util.Map;
import java.util.EnumMap;
import java.util.List;


public class StandardPricingService implements PricingService {

    private static final double PROFIT_MARGIN = 0.60; // 60% profit margin
    private final Map<Ingredient, Double> ingredientCosts;

    public StandardPricingService() {
        this.ingredientCosts = new EnumMap<>(Ingredient.class);
        initializeIngredientCosts();
    }

    private void initializeIngredientCosts() {
        // Costs per unit (in cents for precision)
        ingredientCosts.put(Ingredient.STRAWBERRIES, 0.008);  // $0.008 per gram
        ingredientCosts.put(Ingredient.BANANAS, 0.003);       // $0.003 per gram
        ingredientCosts.put(Ingredient.MANGO, 0.010);         // $0.010 per gram
        ingredientCosts.put(Ingredient.ICE, 0.001);           // $0.001 per ml
        ingredientCosts.put(Ingredient.CONDENSED_MILK, 0.005); // $0.005 per ml
        ingredientCosts.put(Ingredient.SUGAR, 0.001);         // $0.001 per gram
    }

    @Override
    public double calculatePrice(Recipe recipe) {
        double totalCost = recipe.calculateRequiredIngredients().entrySet().stream()
                .mapToDouble(entry -> {
                    Ingredient ingredient = entry.getKey();
                    double amount = entry.getValue();
                    return getIngredientCost(ingredient) * amount;
                })
                .sum();

        return Math.ceil((totalCost * (1 + PROFIT_MARGIN)) * 20) / 20.0; // Round to nearest nickel
    }

    @Override
    public double calculateMixedDrinkPrice(List<DrinkFlavor> flavors, DrinkSize size) {
        if (flavors.isEmpty()) {
            throw new IllegalArgumentException("At least one flavor must be specified");
        }

        // Calculate average cost by creating recipes for each flavor and averaging
        double averageFlavorCost = flavors.stream()
                .mapToDouble(flavor -> {
                    Recipe recipe = new Recipe(flavor, size);
                    return calculatePrice(recipe);
                })
                .average()
                .orElse(0.0);

        // Add 10% premium for mixed drinks complexity
        return Math.ceil((averageFlavorCost * 1.10) * 20) / 20.0;
    }

    @Override
    public double getIngredientCost(Ingredient ingredient) {
        return ingredientCosts.getOrDefault(ingredient, 0.0);
    }

    @Override
    public double calculateProfitMargin(Recipe recipe) {
        double price = calculatePrice(recipe);
        double cost = recipe.calculateRequiredIngredients().entrySet().stream()
                .mapToDouble(entry -> getIngredientCost(entry.getKey()) * entry.getValue())
                .sum();

        return cost > 0 ? ((price - cost) / price) * 100 : 0.0;
    }
}