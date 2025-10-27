package org.example.vendor.repository;


import org.example.vendor.model.Ingredient;

import java.util.Map;


public interface InventoryRepository {

    /**
     * Gets the current quantity of a specific ingredient.
     *
     * @param ingredient the ingredient to check
     * @return the current quantity, or 0 if not found
     */
    double getQuantity(Ingredient ingredient);

    /**
     * Sets the quantity of a specific ingredient.
     *
     * @param ingredient the ingredient to update
     * @param quantity the new quantity (must be non-negative)
     * @throws IllegalArgumentException if quantity is negative
     */
    void setQuantity(Ingredient ingredient, double quantity);

    /**
     * Reduces the quantity of a specific ingredient.
     *
     * @param ingredient the ingredient to reduce
     * @param amount the amount to reduce (must be positive)
     * @throws IllegalArgumentException if amount is negative or exceeds available quantity
     */
    void reduceQuantity(Ingredient ingredient, double amount);

    /**
     * Checks if the specified amount of ingredient is available.
     *
     * @param ingredient the ingredient to check
     * @param amount the required amount
     * @return true if available, false otherwise
     */
    boolean isAvailable(Ingredient ingredient, double amount);

    /**
     * Gets all current inventory levels.
     *
     * @return a map of ingredients to their current quantities
     */
    Map<Ingredient, Double> getAllInventory();

    /**
     * Initializes the inventory with default starting quantities.
     */
    void initializeInventory();
}