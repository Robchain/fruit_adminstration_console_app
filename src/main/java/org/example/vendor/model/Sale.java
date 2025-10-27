package org.example.vendor.model;

import java.time.LocalDateTime;
import java.util.Objects;


public class Sale {
    private final Recipe recipe;
    private final LocalDateTime saleTime;
    private final double price;

    public Sale(Recipe recipe, double price) {
        this(recipe, price, LocalDateTime.now());
    }

    public Sale(Recipe recipe, double price, LocalDateTime saleTime) {
        this.recipe = Objects.requireNonNull(recipe, "Recipe cannot be null");
        this.price = price;
        this.saleTime = Objects.requireNonNull(saleTime, "Sale time cannot be null");

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Sale sale = (Sale) obj;
        return Double.compare(sale.price, price) == 0 &&
                Objects.equals(recipe, sale.recipe) &&
                Objects.equals(saleTime, sale.saleTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe, saleTime, price);
    }

    @Override
    public String toString() {
        return String.format("Sale{%s, $%.2f, %s}",
                recipe, price, saleTime.toLocalDate());
    }
}