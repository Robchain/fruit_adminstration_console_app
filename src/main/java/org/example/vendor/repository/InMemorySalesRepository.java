package org.example.vendor.repository;


import org.example.vendor.model.Sale;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class InMemorySalesRepository implements SalesRepository {

    private final List<Sale> sales;

    public InMemorySalesRepository() {
        this.sales = new ArrayList<>();
    }

    @Override
    public void recordSale(Sale sale) {
        sales.add(sale);
    }

    @Override
    public List<Sale> getSalesByDate(LocalDate date) {
        return sales.stream()
                .filter(sale -> sale.getSaleTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Sale> getAllSales() {
        return new ArrayList<>(sales);
    }

    @Override
    public double getTotalRevenueByDate(LocalDate date) {
        return getSalesByDate(date).stream()
                .mapToDouble(Sale::getPrice)
                .sum();
    }

    @Override
    public int getSalesCountByDate(LocalDate date) {
        return getSalesByDate(date).size();
    }
}