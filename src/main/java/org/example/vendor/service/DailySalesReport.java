package org.example.vendor.service;

import org.example.vendor.model.Sale;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


public class DailySalesReport {
    private final LocalDate date;
    private final List<Sale> sales;
    private final double totalRevenue;
    private final int totalSales;

    public DailySalesReport(LocalDate date, List<Sale> sales) {
        this.date = Objects.requireNonNull(date, "Date cannot be null");
        this.sales = List.copyOf(Objects.requireNonNull(sales, "Sales list cannot be null"));
        this.totalRevenue = sales.stream().mapToDouble(Sale::getPrice).sum();
        this.totalSales = sales.size();
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public double getAverageOrderValue() {
        return totalSales > 0 ? totalRevenue / totalSales : 0.0;
    }

    @Override
    public String toString() {
        return String.format("Daily Sales Report for %s: %d sales, $%.2f revenue (avg: $%.2f)",
                date, totalSales, totalRevenue, getAverageOrderValue());
    }
}