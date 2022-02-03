package rrpss.ui.order;

import rrpss.entities.*;
import rrpss.ui.UI;
import rrpss.util.Crypto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Boundary class for taking an order
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class TakeOrderUI extends UI {
    private OrderController orderController;
    private ArrayList<MenuItem> menuItems;

    private Table table;

    /**
     * Class constructor
     *
     * @param table     table that is ready to take order
     * @param menuItems menu items that can be ordered by the customers
     */
    public TakeOrderUI(Table table, ArrayList<MenuItem> menuItems, OrderController orderController) {
        this.table = table;
        this.menuItems = menuItems;
        this.orderController = orderController;
    }

    /**
     * Take customers' order
     */
    private void takeOrder() {
        // Retrieve table's current order
        Order order = table.getCurrentOrder();
        MenuItem menuItem = null;
        while (true) {
            int option = promptInt("Select menu item: ", 0, menuItems.size() - 1);
            menuItem = menuItems.get(option);
            if (menuItem instanceof AlaCarte) break;
            else if (menuItem instanceof Promotion) {
                Promotion p = (Promotion) menuItem;
                if(p.getEndDate().compareTo(LocalDate.now())<0) println("Unable to select expired promotion");
                else break;
            }
        }

        subtitle(String.format("Selected %s", menuItem.getName()));

        // Attempt to retrieve order item consisting of item to update quantity
        OrderItem orderItem = orderController.getOrderItem(menuItem, order);
        final int length = orderItem == null ? 300 : 300 - orderItem.getNotes().length();

        int quantity = promptInt("Enter quantity: ", 1, 50);
        String notes = promptStr("Enter notes: ", true, (String s) -> {
            if (s.length() > length) return "Notes is too long";
            return null;
        });

        if (orderItem != null) {
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            orderItem.setNotes(orderItem.getNotes() + "\n" + notes);
        } else {
            orderItem = new OrderItem(Crypto.genUUID(), menuItem, quantity, notes);
        }

        // set order item
        order.setOrderItem(orderItem);

        // order is referenced hence updated in table
        orderController.takeOrder(table, order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        takeOrder();
    }
}
