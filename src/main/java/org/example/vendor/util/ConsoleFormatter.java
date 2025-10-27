package org.example.vendor.util;

import org.example.vendor.model.*;
import org.example.vendor.service.DailySalesReport;
import java.util.Map;
import java.util.List;


public class ConsoleFormatter {

    private static final String SEPARATOR = "================================";
    private static final String THIN_SEPARATOR = "--------------------------------";

    /**
     * Formats and displays the current inventory.
     */
    public static void displayInventory(Map<Ingredient, Double> inventory) {
        System.out.println(SEPARATOR);
        System.out.println("         CURRENT INVENTORY");
        System.out.println(SEPARATOR);

        if (inventory.isEmpty()) {
            System.out.println("No inventory available.");
            return;
        }

        System.out.printf("%-20s %15s%n", "Ingredient", "Quantity");
        System.out.println(THIN_SEPARATOR);

        for (Map.Entry<Ingredient, Double> entry : inventory.entrySet()) {
            Ingredient ingredient = entry.getKey();
            double quantity = entry.getValue();
            System.out.printf("%-20s %10.2f %s%n",
                    ingredient.getDisplayName(),
                    quantity,
                    ingredient.getUnit());
        }
        System.out.println();
    }

    /**
     * Formats and displays a successful sale.
     */
    public static void displaySaleSuccess(Sale sale) {
        System.out.println(SEPARATOR);
        System.out.println("         SALE COMPLETED");
        System.out.println(SEPARATOR);
        System.out.printf("Drink: %s%n", sale.getRecipe());
        System.out.printf("Price: $%.2f%n", sale.getPrice());
        System.out.printf("Time: %s%n", sale.getSaleTime());
        System.out.println("Thank you for your purchase!");
        System.out.println();
    }

    /**
     * Formats and displays low stock warnings.
     */
    public static void displayLowStockWarning(List<Ingredient> lowStockIngredients, int threshold) {
        if (lowStockIngredients.isEmpty()) {
            return;
        }

        System.out.println("⚠️  LOW STOCK WARNING ⚠️");
        System.out.println(THIN_SEPARATOR);
        System.out.printf("The following ingredients are below the level required for %d drinks:%n", threshold);

        for (Ingredient ingredient : lowStockIngredients) {
            System.out.printf("- %s%n", ingredient.getDisplayName());
        }
        System.out.println();
    }

    /**
     * Formats and displays a daily sales report.
     */
    public static void displayDailySalesReport(DailySalesReport report) {
        System.out.println(SEPARATOR);
        System.out.printf("    DAILY SALES REPORT - %s%n", report.getDate());
        System.out.println(SEPARATOR);
        System.out.printf("Total Sales: %d%n", report.getTotalSales());
        System.out.printf("Total Revenue: $%.2f%n", report.getTotalRevenue());
        System.out.printf("Average Order Value: $%.2f%n", report.getAverageOrderValue());

        if (!report.getSales().isEmpty()) {
            System.out.println(THIN_SEPARATOR);
            System.out.println("Individual Sales:");
            for (Sale sale : report.getSales()) {
                System.out.printf("- %s: $%.2f%n", sale.getRecipe(), sale.getPrice());
            }
        }
        System.out.println();
    }

    /**
     * Displays available drink flavors.
     */
    public static void displayAvailableFlavors() {
        System.out.println("Available Flavors:");
        for (int i = 0; i < DrinkFlavor.values().length; i++) {
            System.out.printf("%d. %s%n", i + 1, DrinkFlavor.values()[i].getDisplayName());
        }
        System.out.println();
    }

    /**
     * Displays available drink sizes.
     */
    public static void displayAvailableSizes() {
        System.out.println("Available Sizes:");
        for (int i = 0; i < DrinkSize.values().length; i++) {
            System.out.printf("%d. %s%n", i + 1, DrinkSize.values()[i]);
        }
        System.out.println();
    }

    /**
     * Displays the main menu.
     */
    public static void displayMainMenu() {
        System.out.println(SEPARATOR);
        System.out.println("    FRUIT VENDOR MANAGEMENT SYSTEM");
        System.out.println(SEPARATOR);
        System.out.println("1. View Current Inventory");
        System.out.println("2. Sell Single Flavor Drink");
        System.out.println("3. Sell Mixed Flavor Drink");
        System.out.println("4. Check Low Stock Ingredients");
        System.out.println("5. View Daily Sales Report");
        System.out.println("6. View All Sales History");
        System.out.println("0. Exit");
        System.out.println(SEPARATOR);
        System.out.print("Please select an option: ");
    }

    /**
     * Displays an error message.
     */
    public static void displayError(String message) {
        System.out.println("❌ ERROR: " + message);
        System.out.println();
    }

    /**
     * Displays an info message.
     */
    public static void displayInfo(String message) {
        System.out.println("ℹ️  " + message);
        System.out.println();
    }
}