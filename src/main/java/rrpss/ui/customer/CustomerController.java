package rrpss.ui.customer;

import rrpss.entities.Customer;
import rrpss.entities.Invoice;
import rrpss.entities.Membership;
import rrpss.entities.Reservation;
import rrpss.ui.RouteMap;
import rrpss.ui.app.Back;
import rrpss.ui.order.OrderController;
import rrpss.ui.report.ReportController;
import rrpss.util.Crypto;

import java.util.ArrayList;

/**
 * Control class for customer management
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CustomerController {

    /**
     * reportController for accessing report methods
     */
    final private ReportController reportController = new ReportController();

    /**
     * Routes defined for navigation of customer pages
     */
    public RouteMap[] routes = {
            new RouteMap("View Customers", new ViewCustomers(this)),
            new RouteMap("Create Membership for Customers", new CustomerMembershipUI(this)),
            new RouteMap("Go Back", new Back())
    };

    /**
     * Get all customers
     * @return list of customers
     */
    public ArrayList<Customer> getCustomers() {
        return Customer.getCustomers();
    }

    /**
     * Returns customer by number
     * @param number customer's number
     * @return customer
     */
    public Customer getCustomerByNumber(int number) {
        ArrayList<Customer> customers =  Customer.getCustomers();
        for (Customer customer : customers) {
            if (customer.getContactDetails() == number)
                return customer;
        }
        return null;
    }

    /**
     * Add customer to the system
     * @param name
     * @param number
     * @return
     */
    public Customer createCustomer(String name, int number) {
        Customer customer = new Customer(
                Crypto.genUUID(),
                name,
                null,
                number
                );
        customer.save();
        return customer;
    }

    public boolean canCreateMembership(Customer customer, Membership membership) {
        for (Invoice invoice : reportController.getExistingInvoice()) {
            if (!invoice.getReservation().getCustomer().getID().equalsIgnoreCase(customer.getID())) continue;
            if (invoice.getPayment().getPayable() >= membership.getMinSpending())
                return true;
        }
        return false;
    }

    /**
     * Creates a membership for customer
     * @param customer
     */
    public void createMembership(Customer customer, Membership membership) {
        customer.setMembership(membership);
        customer.save();
    }
}
