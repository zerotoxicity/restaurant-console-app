package rrpss.ui.customer;

import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.app.Back;

/**
 * Boundary class for customers management
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CustomerUI extends UI {

    CustomerController customerController = new CustomerController();

    /**
     * Display customer management's sub-functions
     * @return index of selected sub-function
     */
    public int showOptions() {
        for (int i = 0; i < customerController.routes.length; i++)
            println(String.format("[%d] %s", i, customerController.routes[i].getTitle()));
        return promptInt(true, 0, customerController.routes.length - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        customerController.routes[showOptions()].getUi().display();
    }
}
