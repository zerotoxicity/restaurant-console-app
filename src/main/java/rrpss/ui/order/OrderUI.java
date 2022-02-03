package rrpss.ui.order;

import rrpss.ui.tables.TableController;
import rrpss.entities.*;
import rrpss.ui.Component;
import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.app.Back;
import rrpss.ui.menu.MenuController;
import rrpss.ui.menu.MenuUI;

import java.util.ArrayList;

/**
 * Boundary class for orders
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class OrderUI extends UI {
    private ArrayList<MenuItem> menuItems;
    private MenuController menuController = new MenuController();
    private OrderController orderController = new OrderController();

    /**
     * Class constructor
     */
    public OrderUI() {
        // It is assumed that menu items are sorted from promotions to alacarte items
        menuItems = menuController.getExistingMenuItem();
    }

    /**
     * prints out tables available
     */
    public void getTableUI() {
        // Get dinner's table
        ArrayList<Table> occupiedTables = TableController.getInstance().getOccupiedTables();

        // Empty tables imply customers not checked in
        if (occupiedTables.isEmpty()) {
            subtitle("No customers checked in at the moment!");
            return;
        }

        // Display tables
        println("Select table to take order");
        for (int i = 0; i < occupiedTables.size(); i++) {
            // Get reservation
            Reservation reservation = occupiedTables.get(i).getCurrentReservation();
            println(String.format("[%d] Table pax %d | Customer %s", i, reservation.getPax(), reservation.getCustomer().getName()));
        }
        final int in = promptInt(true, 0, occupiedTables.size());

        showOptions(occupiedTables.get(in));
    }

    /**
     * Sub-functions of order UI
     *
     * @param table table that requires a staff services
     */
    public void showOptions(Table table) {
        lifecycle:
        while (true) {
            for (int i = 0; i < orderController.routes.length; i++)
                printf("[%d] %s\n", i, orderController.routes[i].getTitle());
            int option = promptInt("Enter option: ", 0, orderController.routes.length - 1);
            RouteMap route = orderController.routes[option];

            if (route.getUi() instanceof Back)
                return;

            if (route.getUi() instanceof Component) {
                ((Component) route.getUi()).display(table, menuItems);
                if (option == 4) break;
            } else {
                route.getUi().display();

            }
            println("");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Create order");
        getTableUI();
    }
}
