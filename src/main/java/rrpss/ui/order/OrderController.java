package rrpss.ui.order;

import rrpss.entities.*;
import rrpss.ui.RouteMap;
import rrpss.ui.app.Back;
import rrpss.ui.customer.CustomerController;
import rrpss.ui.menu.MenuUI;
import rrpss.util.Crypto;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Control class for order
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class OrderController {
    /**
     * Create a new customer controller instance to access customer control class functions
     */
    private CustomerController customerController = new CustomerController();

    private MenuUI menuUI = new MenuUI();

    public RouteMap[] routes = {
            new RouteMap("View Menu", (e) ->
                    menuUI.viewMenuItems()
            ),
            new RouteMap("View Order", (o) -> new ViewOrderUI((Table) o[0]).display()),
            new RouteMap("Take Order", (o) -> new TakeOrderUI((Table) o[0], (ArrayList<MenuItem>) o[1], this).display()),
            new RouteMap("Edit Order", (o) -> new EditOrderUI((Table) o[0], this).display()),
            new RouteMap("Checkout Order", (o) -> new CheckoutOrderUI((Table) o[0], this).display()),
            new RouteMap("Back", new Back()),
    };

    /**
     * Get what the customer has ordered
     *
     * @param menuItem item to be ordered
     * @param order    customer's order
     * @return order item
     */
    public OrderItem getOrderItem(MenuItem menuItem, Order order) {
        ArrayList<OrderItem> items = order.getOrderItems();
        for (OrderItem item : items) {
            if (item.getMenuItem().getID().equalsIgnoreCase(menuItem.getID()))
                return item;
        }
        return null;
    }

    /**
     * Takes order and saves it
     */
    public void takeOrder(Table table, Order order) {
        table.setCurrentOrder(order);
        table.save();
    }

    /**
     * Get the final price of the order
     *
     * @param table table that is being checked out
     * @return total price after tax
     */
    public double getTotalPreTax(Table table) {
        double totalPrice = 0;
        Order order = table.getCurrentOrder();
        for (OrderItem o : order.getOrderItems()) {
            //Total before tax
            totalPrice += o.getPrice();
        }
        return totalPrice;
    }

    /**
     * returns a instance of customer from db
     *
     * @param number
     * @return
     */
    public Customer getCustomerByNumber(int number) {
        return customerController.getCustomerByNumber(number);
    }

    /**
     * Get total price for the table's order
     *
     * @param table    Table to get price for
     * @param customer Customer who made the reservation
     * @return total price after discount and taxes
     */
    public double getTotal(Table table, Customer customer) {
        Order order = table.getCurrentOrder();
        double totalPrice = getTotalPreTax(table);
        // Calculate membership
        if (customer.isMember())
            totalPrice -= customer.getMembership().calculateDiscount(totalPrice);
        //Post tax
        double gst = totalPrice * order.getGST();
        double svcChg = totalPrice * order.getSvcCharge();

        totalPrice += gst + svcChg;
        totalPrice = Math.floor(totalPrice * 100.0) / 100.0;

        return totalPrice;
    }

    /**
     * Complete customer's transaction
     *
     * @param ID          invoice ID
     * @param payable     total price of the order
     * @param amount      amount received by the staff
     * @param order       customer's order
     * @param dateTime    datetime of the transaction occurs
     * @param reservation reservation made by the customer
     * @param subtotal    total price before tax
     * @return invoice
     */
    public Invoice checkout(String ID, double payable, double amount, Order order, LocalDateTime dateTime, Reservation reservation, double subtotal) {
        amount = Math.round(amount * 100.0) / 100.0;
        Payment payment = new Payment(Crypto.genUUID(), payable, amount, subtotal, subtotal * order.getSvcCharge());
        Invoice invoice = new Invoice(ID, order, payment, dateTime, reservation);
        invoice.save();
        return invoice;
    }

    /**
     * Remove reservation
     *
     * @param table table to be freed up
     */
    public void removeReservation(Table table) {
        Reservation reservation = table.getCurrentReservation();
        Customer customer = getCustomerByNumber(reservation.getCustomer().getContactDetails());
        customer.removeReservation(reservation);
        customer.addPastReservation(reservation);

        // Remove reservations from table
        table.setCurrentReservation(null);
        table.removeReservation(reservation);

        customer.save();
        table.save();
        reservation.delete();
    }

    /**
     * update order item
     *
     * @param table    table who need their order item updated
     * @param index    position of order item to be updated
     * @param quantity new quantity
     */
    public void updateOrderItemQuantity(Table table, int index, int quantity) {
        ArrayList<OrderItem> orderItems = table.getCurrentOrder().getOrderItems();
        if (quantity == 0) orderItems.remove(index);
        else orderItems.get(index).setQuantity(quantity);
        table.save();
    }


}
