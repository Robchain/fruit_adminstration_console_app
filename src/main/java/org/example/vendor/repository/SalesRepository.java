package org.example.vendor.repository;


import org.example.vendor.model.Sale;

import java.time.LocalDate;
import java.util.List;


public interface SalesRepository {

    /**
     * Records a sale.
     *
     * @param sale the sale to record
     */
    void recordSale(Sale sale);

    /**
     * Gets all sales for a specific date.
     *
     * @param date the date to filter by
     * @return list of sales for the specified date
     */
    List<Sale> getSalesByDate(LocalDate date);

    /**
     * Gets all recorded sales.
     *
     * @return list of all sales
     */
    List<Sale> getAllSales();

    /**
     * Gets the total revenue for a specific date.
     *
     * @param date the date to calculate revenue for
     * @return total revenue for the date
     */
    double getTotalRevenueByDate(LocalDate date);

    /**
     * Gets the total number of sales for a specific date.
     *
     * @param date the date to count sales for
     * @return number of sales for the date
     */
    int getSalesCountByDate(LocalDate date);
}
