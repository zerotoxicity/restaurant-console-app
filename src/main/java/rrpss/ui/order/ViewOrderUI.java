package rrpss.ui.order;

import rrpss.entities.Order;
import rrpss.entities.OrderItem;
import rrpss.entities.Table;
import rrpss.ui.UI;

public class ViewOrderUI extends UI {

    final Table table;

    public ViewOrderUI(Table table) {
        this.table = table;
    }

    public void viewOrder() {
        final Order order = table.getCurrentOrder();
        println("");

        if (order.getOrderItems().isEmpty()) {
            subtitle("No orders at the moment!");
            return;
        }

        for (OrderItem item : order.getOrderItems()) {
            println();
            printf("[%s]\n", item.getMenuItem().getType().toString());
            println(String.format("%s \n%.2f (%.2f) \t\tx%d\n", item.getMenuItem().getName(), item.getPrice(), item.getNetPrice(), item.getQuantity()));
            println("Notes");
            println(item.getNotes());
            println();
        }
    }

    @Override
    public void display() {
        viewOrder();
    }
}
