package rrpss.ui.customer;

import rrpss.entities.Customer;
import rrpss.ui.UI;

import java.util.ArrayList;

/**
 * Boundary class for viewing customer details
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ViewCustomers extends UI {

    private CustomerController customerController;

    public ViewCustomers(CustomerController customerController) {
        this.customerController = customerController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("View all customers");
        ArrayList<Customer> customers = customerController.getCustomers();
        if (customers.isEmpty()) {
            println("No customers registered!");
            return;
        }
        for (Customer customer : customers) {
            printf("%-10s %-10d %-10s - %s\n", customer.getName(), customer.getContactDetails(), "Member",  customer.isMember() ? "Yes" : "No");
        }
    }
}
