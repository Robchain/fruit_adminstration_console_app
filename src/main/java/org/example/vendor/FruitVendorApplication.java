package org.example.vendor;

import org.example.vendor.model.*;
import org.example.vendor.service.*;
import org.example.vendor.repository.*;
import org.example.vendor.exception.InsufficientInventoryException;
import org.example.vendor.util.ConsoleFormatter;

import java.time.LocalDate;
import java.util.*;


public class FruitVendorApplication {

    private final VendorService vendorService;
    private final Scanner scanner;

    public FruitVendorApplication() {
        // Initialize repositories
        InventoryRepository inventoryRepository = new InMemoryInventoryRepository();
        SalesRepository salesRepository = new InMemorySalesRepository();

        // Initialize services
        PricingService pricingService = new StandardPricingService();
        this.vendorService = new StandardVendorService(inventoryRepository, salesRepository, pricingService);

        // Initialize scanner for user input
        this.scanner = new Scanner(System.in);
    }



    /**
     * Main application loop.
     */
    public void run() {
        ConsoleFormatter.displayInfo("Welcome to the Fruit Vendor Management System!");

        // Check initial low stock
        checkAndDisplayLowStock();

        boolean running = true;
        while (running) {
            try {
                ConsoleFormatter.displayMainMenu();
                int choice = getIntInput();

                switch (choice) {
                    case 1 -> viewCurrentInventory();
                    case 2 -> sellSingleFlavorDrink();
                    case 3 -> sellMixedFlavorDrink();
                    case 4 -> checkLowStockIngredients();
                    case 5 -> viewDailySalesReport();
                    case 6 -> viewAllSalesHistory();
                    case 0 -> {
                        running = false;
                        ConsoleFormatter.displayInfo("Thank you for using the Fruit Vendor Management System!");
                    }
                    default -> ConsoleFormatter.displayError("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                ConsoleFormatter.displayError("An unexpected error occurred: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private void viewCurrentInventory() {
        Map<Ingredient, Double> inventory = vendorService.getCurrentInventory();
        ConsoleFormatter.displayInventory(inventory);
    }

    private void sellSingleFlavorDrink() {
        try {
            // Select flavor
            ConsoleFormatter.displayAvailableFlavors();
            System.out.print("Select flavor (1-" + DrinkFlavor.values().length + "): ");
            int flavorChoice = getIntInput();

            if (flavorChoice < 1 || flavorChoice > DrinkFlavor.values().length) {
                ConsoleFormatter.displayError("Invalid flavor selection.");
                return;
            }
            DrinkFlavor flavor = DrinkFlavor.values()[flavorChoice - 1];

            // Select size
            ConsoleFormatter.displayAvailableSizes();
            System.out.print("Select size (1-" + DrinkSize.values().length + "): ");
            int sizeChoice = getIntInput();

            if (sizeChoice < 1 || sizeChoice > DrinkSize.values().length) {
                ConsoleFormatter.displayError("Invalid size selection.");
                return;
            }
            DrinkSize size = DrinkSize.values()[sizeChoice - 1];

            // Create recipe and attempt sale
            Recipe recipe = new Recipe(flavor, size);

            if (!vendorService.canMakeDrink(recipe)) {
                ConsoleFormatter.displayError("Cannot make this drink - insufficient ingredients.");
                return;
            }

            Sale sale = vendorService.sellDrink(recipe);
            ConsoleFormatter.displaySaleSuccess(sale);

            // Check for low stock after sale
            checkAndDisplayLowStock();

        } catch (InsufficientInventoryException e) {
            ConsoleFormatter.displayError(e.getMessage());
        } catch (Exception e) {
            ConsoleFormatter.displayError("Error processing sale: " + e.getMessage());
        }
    }

    private void sellMixedFlavorDrink() {
        try {
            List<DrinkFlavor> selectedFlavors = new ArrayList<>();

            System.out.println("Select flavors for mixed drink (enter 0 when done):");
            ConsoleFormatter.displayAvailableFlavors();

            while (true) {
                System.out.print("Select flavor (0 to finish): ");
                int flavorChoice = getIntInput();

                if (flavorChoice == 0) {
                    break;
                }

                if (flavorChoice < 1 || flavorChoice > DrinkFlavor.values().length) {
                    ConsoleFormatter.displayError("Invalid flavor selection.");
                    continue;
                }

                DrinkFlavor flavor = DrinkFlavor.values()[flavorChoice - 1];
                if (!selectedFlavors.contains(flavor)) {
                    selectedFlavors.add(flavor);
                    System.out.println("Added: " + flavor.getDisplayName());
                } else {
                    System.out.println("Flavor already selected.");
                }
            }

            if (selectedFlavors.isEmpty()) {
                ConsoleFormatter.displayError("At least one flavor must be selected.");
                return;
            }

            // Select size
            ConsoleFormatter.displayAvailableSizes();
            System.out.print("Select size (1-" + DrinkSize.values().length + "): ");
            int sizeChoice = getIntInput();

            if (sizeChoice < 1 || sizeChoice > DrinkSize.values().length) {
                ConsoleFormatter.displayError("Invalid size selection.");
                return;
            }
            DrinkSize size = DrinkSize.values()[sizeChoice - 1];

            // Attempt sale
            Sale sale = vendorService.sellMixedDrink(selectedFlavors, size);
            ConsoleFormatter.displaySaleSuccess(sale);

            // Check for low stock after sale
            checkAndDisplayLowStock();

        } catch (InsufficientInventoryException e) {
            ConsoleFormatter.displayError(e.getMessage());
        } catch (Exception e) {
            ConsoleFormatter.displayError("Error processing mixed drink sale: " + e.getMessage());
        }
    }

    private void checkLowStockIngredients() {
        System.out.print("Enter threshold number of drinks (default 4): ");
        String input = scanner.nextLine().trim();
        int threshold = 4;

        if (!input.isEmpty()) {
            try {
                threshold = Integer.parseInt(input);
                if (threshold < 1) {
                    ConsoleFormatter.displayError("Threshold must be positive.");
                    return;
                }
            } catch (NumberFormatException e) {
                ConsoleFormatter.displayError("Invalid number format.");
                return;
            }
        }

        List<Ingredient> lowStock = vendorService.getLowStockIngredients(threshold);
        ConsoleFormatter.displayLowStockWarning(lowStock, threshold);

        if (lowStock.isEmpty()) {
            ConsoleFormatter.displayInfo("All ingredients are sufficiently stocked.");
        }
    }

    private void viewDailySalesReport() {
        System.out.print("Enter date (YYYY-MM-DD) or press Enter for today: ");
        String dateInput = scanner.nextLine().trim();

        LocalDate date;
        if (dateInput.isEmpty()) {
            date = LocalDate.now();
        } else {
            try {
                date = LocalDate.parse(dateInput);
            } catch (Exception e) {
                ConsoleFormatter.displayError("Invalid date format. Use YYYY-MM-DD.");
                return;
            }
        }

        DailySalesReport report = vendorService.getDailySalesReport(date);
        ConsoleFormatter.displayDailySalesReport(report);
    }

    private void viewAllSalesHistory() {
        List<Sale> allSales = vendorService.getAllSales();

        if (allSales.isEmpty()) {
            ConsoleFormatter.displayInfo("No sales recorded yet.");
            return;
        }

        System.out.println("================================");
        System.out.println("         SALES HISTORY");
        System.out.println("================================");
        System.out.printf("%-25s %-10s %-15s%n", "Drink", "Price", "Date");
        System.out.println("--------------------------------");

        for (Sale sale : allSales) {
            System.out.printf("%-25s $%-9.2f %s%n",
                    sale.getRecipe().toString(),
                    sale.getPrice(),
                    sale.getSaleTime().toLocalDate());
        }

        double totalRevenue = allSales.stream().mapToDouble(Sale::getPrice).sum();
        System.out.println("--------------------------------");
        System.out.printf("Total Sales: %d%n", allSales.size());
        System.out.printf("Total Revenue: $%.2f%n", totalRevenue);
        System.out.println();
    }

    private void checkAndDisplayLowStock() {
        List<Ingredient> lowStock = vendorService.getLowStockIngredients(4);
        ConsoleFormatter.displayLowStockWarning(lowStock, 4);
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}