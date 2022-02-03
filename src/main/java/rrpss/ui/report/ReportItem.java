package rrpss.ui.report;

import rrpss.entities.OrderItem;

/**
 * Represents a report item
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ReportItem {
    private OrderItem item;
    private int totalQuantity;

    /**
     * Class constructor
     * @param item menu item ordered by customers
     * @param totalQuantity total quantity of menu item ordered by customers in a month
     */
    public ReportItem(OrderItem item, int totalQuantity) {
        this.item = item;
        this.totalQuantity = totalQuantity;
    }

    /**
     * Get item ordered by customers
     * @return Order Item
     */
    public OrderItem getItem() {
        return item;
    }

    /**
     * Set menu item ordered by customers
     * @param item menu item ordered by customers
     */
    public void setItem(OrderItem item) {
        this.item = item;
    }

    /**
     * Get total quantity of menu item ordered by customers in a month
     * @return total quantity
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * Increase total quantity
     * @param quantity amount ordered by customers in a day
     */
    public void incrementQuantity(int quantity) {
        this.totalQuantity += quantity;
    }
}
