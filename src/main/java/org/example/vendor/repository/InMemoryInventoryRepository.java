package org.example.vendor.repository;


import org.example.vendor.model.Ingredient;

import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;


public class InMemoryInventoryRepository implements InventoryRepository {

    private final Map<Ingredient, Double> inventory;

    public InMemoryInventoryRepository() {
        this.inventory = new EnumMap<>(Ingredient.class);
        initializeInventory();
    }

    @Override
    public double getQuantity(Ingredient ingredient) {
        return inventory.getOrDefault(ingredient, 0.0);
    }

    @Override
    public void setQuantity(Ingredient ingredient, double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        inventory.put(ingredient, quantity);
    }

    @Override
    public void reduceQuantity(Ingredient ingredient, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to reduce cannot be negative");
        }

        double currentQuantity = getQuantity(ingredient);
        if (amount > currentQuantity) {
            throw new IllegalArgumentException(
                    String.format("Cannot reduce %s by %.2f %s: only %.2f %s available",
                            ingredient.getDisplayName(), amount, ingredient.getUnit(),
                            currentQuantity, ingredient.getUnit()));
        }

        setQuantity(ingredient, currentQuantity - amount);
    }

    @Override
    public boolean isAvailable(Ingredient ingredient, double amount) {
        return getQuantity(ingredient) >= amount;
    }

    @Override
    public Map<Ingredient, Double> getAllInventory() {
        return new HashMap<>(inventory);
    }

    @Override
    public void initializeInventory() {
        // Initialize with generous starting quantities
        inventory.put(Ingredient.STRAWBERRIES, 5000.0);  // 5kg
        inventory.put(Ingredient.BANANAS, 6000.0);       // 6kg
        inventory.put(Ingredient.MANGO, 4000.0);         // 4kg
        inventory.put(Ingredient.ICE, 10000.0);          // 10 liters
        inventory.put(Ingredient.CONDENSED_MILK, 3000.0); // 3 liters
        inventory.put(Ingredient.SUGAR, 2000.0);         // 2kg
    }
}
