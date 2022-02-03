package rrpss.ui.order;

import rrpss.entities.Order;
import rrpss.entities.OrderItem;
import rrpss.entities.Table;
import rrpss.ui.UI;

import java.util.ArrayList;

/**
 * Boundary class to update order item quantity
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class EditOrderUI extends UI {
    private OrderController orderController;
    private Table table;
    private Order order;

    /**
     * Class constructor
     *
     * @param table table that requires updating of order item quantity
     */
    public EditOrderUI(Table table, OrderController orderController) {
        this.table = table;
        this.order = table.getCurrentOrder();
        this.orderController = orderController;
    }

    /**
     * Remove/increase order item quantity
     */
    private void editOrder() {
        final ArrayList<OrderItem> orderItems = order.getOrderItems();
        if (orderItems.isEmpty()) {
            println(String.format("No orders yet for table %d", table.getNumber()));
            return;
        }
        subtitle("Current order items");

        for (int i = 0; i < orderItems.size(); i++) {
            printf("[%d] %s\n", i, orderItems.get(i).getMenuItem().getName());
        }
        final int option = promptInt("Select item to edit: ", 0, order.getOrderItems().size() - 1);
        subtitle(String.format("Selected %s, current quantity: %d", orderItems.get(option).getMenuItem().getName(), orderItems.get(option).getQuantity()));
        final OrderItem selectedOrderItem = orderItems.get(option);

        println("Enter new quantity: ");
        int quantity = promptInt(true, 0, 50);
        orderController.updateOrderItemQuantity(table, option, quantity);

        if (quantity == 0) println(selectedOrderItem.getMenuItem().getName() + " has been removed");
        else println(selectedOrderItem.getMenuItem().getName() + "'s quantity has been updated");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        editOrder();
    }

}
