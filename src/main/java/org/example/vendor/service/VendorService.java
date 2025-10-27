package org.example.vendor.service;

import org.example.vendor.model.*;
import org.example.vendor.exception.InsufficientInventoryException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


public interface VendorService {

    /**
     * Gets the current inventory levels.
     *
     * @return map of ingredients to their current quantities
     */
    Map<Ingredient, Double> getCurrentInventory();

    /**
     * Sells a drink with the specified recipe.
     *
     * @param recipe the recipe for the drink to sell
     * @return the sale transaction
     * @throws InsufficientInventoryException if there are not enough ingredients
     */
    Sale sellDrink(Recipe recipe) throws InsufficientInventoryException;

    /**
     * Checks if a drink can be made with current inventory.
     *
     * @param recipe the recipe to check
     * @return true if the drink can be made, false otherwise
     */
    boolean canMakeDrink(Recipe recipe);

    /**
     * Gets ingredients that are below the threshold for making a specified number of drinks.
     *
     * @param drinksThreshold the number of drinks threshold (e.g., 4)
     * @return list of ingredients below threshold
     */
    List<Ingredient> getLowStockIngredients(int drinksThreshold);

    /**
     * Sells a mixed fruit drink with multiple flavors.
     *
     * @param flavors the flavors to mix
     * @param size the size of the drink
     * @return the sale transaction
     * @throws InsufficientInventoryException if there are not enough ingredients
     */
    Sale sellMixedDrink(List<DrinkFlavor> flavors, DrinkSize size) throws InsufficientInventoryException;

    /**
     * Gets daily sales report for a specific date.
     *
     * @param date the date to generate report for
     * @return daily sales report
     */
    DailySalesReport getDailySalesReport(LocalDate date);

    /**
     * Gets all sales records.
     *
     * @return list of all sales
     */
    List<Sale> getAllSales();
}