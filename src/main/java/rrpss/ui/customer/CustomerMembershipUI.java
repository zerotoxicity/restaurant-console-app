package rrpss.ui.customer;

import rrpss.controller.AdminController;
import rrpss.entities.Customer;
import rrpss.entities.Membership;
import rrpss.ui.UI;

import java.util.ArrayList;

/**
 * Boundary class for assigning membership to user
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CustomerMembershipUI extends UI {

    private CustomerController customerController;

    /**
     * Takes in a customer controller
     * @param customerController customerController DI
     */
    public CustomerMembershipUI(CustomerController customerController) {
        this.customerController = customerController;
    }

    /**
     * Form for assigning membership to customers
     */
    private void form() {
        final ArrayList<Membership> memberships = AdminController.getInstance().getMemberships();
        if (memberships.isEmpty()) {
            println("No membership available at this time.");
            return;
        }

        int number = promptInt("Enter customer's number to create membership");
        Customer customer = customerController.getCustomerByNumber(number);
        if (customer == null) {
            printf("No customer registered with %d\n", number);
            return;
        }

        // Check if customer has dined in
        if (customer.getPastReservations().isEmpty()) {
            printf("Cannot create membership for %s. %s has not dined in yet!\n", customer.getName(), customer.getName());
            return;
        }

        if (customer.isMember()) {
            printf("%s is already a member\n", customer.getName());
            return;
        }

        for (int i = 0; i < memberships.size(); i++) {
            final Membership membership = memberships.get(i);
            printf("[%d] %s benefits: %.1f %% discount capped at %d | Min Spending: %s \n", i, membership.getType(), membership.getDiscount(), membership.getCap(), membership.getMinSpending());
        }

        final int option = promptInt("Select membership type: ", 0, memberships.size() - 1, (Integer choice) -> {
            if (!customerController.canCreateMembership(customer, memberships.get(choice)))
                return "Customer does not meet spending requirements!";
            return "";
        });

        // Creates membership for customer
        customerController.createMembership(customer, memberships.get(option));
        printf("Registered %s for %s\n",  memberships.get(option).getType(), customer.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Customer Membership");
        form();
    }
}
