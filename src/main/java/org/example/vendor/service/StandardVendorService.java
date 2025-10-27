package org.example.vendor.service;

import org.example.vendor.model.*;
import org.example.vendor.exception.InsufficientInventoryException;
import org.example.vendor.repository.InventoryRepository;
import org.example.vendor.repository.SalesRepository;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class StandardVendorService implements VendorService {

    private final InventoryRepository inventoryRepository;
    private final SalesRepository salesRepository;
    private final PricingService pricingService;

    public StandardVendorService(InventoryRepository inventoryRepository,
                                 SalesRepository salesRepository,
                                 PricingService pricingService) {
        this.inventoryRepository = Objects.requireNonNull(inventoryRepository, "InventoryRepository cannot be null");
        this.salesRepository = Objects.requireNonNull(salesRepository, "SalesRepository cannot be null");
        this.pricingService = Objects.requireNonNull(pricingService, "PricingService cannot be null");
    }

    @Override
    public Map<Ingredient, Double> getCurrentInventory() {
        return inventoryRepository.getAllInventory();
    }

    @Override
    public Sale sellDrink(Recipe recipe) throws InsufficientInventoryException {
        Objects.requireNonNull(recipe, "Recipe cannot be null");

        // Check if we can make the drink
        Map<Ingredient, Double> required = recipe.calculateRequiredIngredients();
        validateInventoryAvailability(required);

        // Reduce inventory
        for (Map.Entry<Ingredient, Double> entry : required.entrySet()) {
            inventoryRepository.reduceQuantity(entry.getKey(), entry.getValue());
        }

        // Calculate price and create sale
        double price = pricingService.calculatePrice(recipe);
        Sale sale = new Sale(recipe, price);

        // Record the sale
        salesRepository.recordSale(sale);

        return sale;
    }

    @Override
    public boolean canMakeDrink(Recipe recipe) {
        Map<Ingredient, Double> required = recipe.calculateRequiredIngredients();
        return required.entrySet().stream()
                .allMatch(entry -> inventoryRepository.isAvailable(entry.getKey(), entry.getValue()));
    }

    @Override
    public List<Ingredient> getLowStockIngredients(int drinksThreshold) {
        List<Ingredient> lowStock = new ArrayList<>();

        for (DrinkFlavor flavor : DrinkFlavor.values()) {
            Recipe recipe = new Recipe(flavor, DrinkSize.MEDIUM); // Use medium as standard
            Map<Ingredient, Double> required = recipe.calculateRequiredIngredients();

            for (Map.Entry<Ingredient, Double> entry : required.entrySet()) {
                Ingredient ingredient = entry.getKey();
                double requiredForThreshold = entry.getValue() * drinksThreshold;

                if (!inventoryRepository.isAvailable(ingredient, requiredForThreshold)
                        && !lowStock.contains(ingredient)) {
                    lowStock.add(ingredient);
                }
            }
        }

        return lowStock;
    }

    @Override
    public Sale sellMixedDrink(List<DrinkFlavor> flavors, DrinkSize size) throws InsufficientInventoryException {
        if (flavors == null || flavors.isEmpty()) {
            throw new IllegalArgumentException("At least one flavor must be specified");
        }

        Objects.requireNonNull(size, "Size cannot be null");

        // Calculate combined ingredients needed
        Map<Ingredient, Double> totalRequired = new EnumMap<>(Ingredient.class);

        for (DrinkFlavor flavor : flavors) {
            Recipe recipe = new Recipe(flavor, size);
            Map<Ingredient, Double> required = recipe.calculateRequiredIngredients();

            for (Map.Entry<Ingredient, Double> entry : required.entrySet()) {
                totalRequired.merge(entry.getKey(), entry.getValue() / flavors.size(), Double::sum);
            }
        }

        // Validate availability
        validateInventoryAvailability(totalRequired);

        // Reduce inventory
        for (Map.Entry<Ingredient, Double> entry : totalRequired.entrySet()) {
            inventoryRepository.reduceQuantity(entry.getKey(), entry.getValue());
        }

        // Calculate price and create sale
        double price = pricingService.calculateMixedDrinkPrice(flavors, size);

        // Create a representative recipe for the mixed drink (using first flavor)
        Recipe representativeRecipe = new Recipe(flavors.get(0), size);
        Sale sale = new Sale(representativeRecipe, price);

        salesRepository.recordSale(sale);

        return sale;
    }

    @Override
    public DailySalesReport getDailySalesReport(LocalDate date) {
        List<Sale> dailySales = salesRepository.getSalesByDate(date);
        return new DailySalesReport(date, dailySales);
    }

    @Override
    public List<Sale> getAllSales() {
        return salesRepository.getAllSales();
    }

    private void validateInventoryAvailability(Map<Ingredient, Double> required) throws InsufficientInventoryException {
        for (Map.Entry<Ingredient, Double> entry : required.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double requiredAmount = entry.getValue();
            double available = inventoryRepository.getQuantity(ingredient);

            if (!inventoryRepository.isAvailable(ingredient, requiredAmount)) {
                throw new InsufficientInventoryException(ingredient, requiredAmount, available);
            }
        }
    }
}
