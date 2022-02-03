package rrpss.ui.order;

import rrpss.entities.*;
import rrpss.ui.UI;
import rrpss.util.Crypto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Boundary class to check out order
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CheckoutOrderUI extends UI {
    private Table table;
    private OrderController orderController;

    /**
     * Class constructor
     * @param table table to be checked out
     */
    public CheckoutOrderUI(Table table, OrderController orderController) {
        this.table = table;
        this.orderController = orderController;
    }

    /**
     * Prints invoice for the table
     * @param table table to be checked out
     */
    public void checkoutTable(Table table) {
        ArrayList<OrderItem> orderItems = table.getCurrentOrder().getOrderItems();

        if (orderItems.isEmpty()) {
            println("No order has been placed yet.");
            return;
        }

        Reservation reservation = table.getCurrentReservation();
        Customer customer = orderController.getCustomerByNumber(reservation.getCustomer().getContactDetails());
        Order order = table.getCurrentOrder();
        String invoiceID = Crypto.genUUID();

        double subtotal = orderController.getTotalPreTax(table);
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        title("RRPS");
        printf("ID: \t\t\t\t\t%s\n", invoiceID);
        println("Date and time: \t\t\t" + dateTime.format(formatter));
        println("Handled by: \t\t\t" + order.getStaff().getName());
        println(String.format("Customer: \t\t\t\t%s", customer.getName()));
        if (customer.isMember())
            println(String.format("%s", customer.getMembership().getType()));

        println("------------------------------------------------------------------");
        for (OrderItem item : orderItems) {
            println(String.format("%s \n%.2f (%.2f) \t\t\tx%d\n", item.getMenuItem().getName(), item.getPrice(), item.getNetPrice(), item.getQuantity()));
        }
        printf("Subtotal: \t\t\t\t$%.2f\n", subtotal);
        println("------------------------------------------------------------------");
        if (customer.isMember()) {
            double discount = customer.getMembership().calculateDiscount(subtotal);
            subtotal -= discount;
            printf("Member discount %.1f%%: \t($%.2f)\n", customer.getMembership().getDiscount(), discount);
        }
        printf("GST %.1f%%: \t\t\t\t$%.2f\n", order.getGSTPercentage(),(subtotal * order.getGST()));
        printf("Service Charge %.1f%%: \t$%.2f\n", order.getSvcChargePercent(), (subtotal * order.getSvcCharge()));

        println("------------------------------------------------------------------");
        double totalPrice = orderController.getTotal(table, customer);
        totalPrice = Math.round(totalPrice * 100.0) / 100.0;
        printf("Total: \t\t\t\t\t$%.2f\n", totalPrice);
        print("Amount received:\t\t$");
        double amount = promptDouble(totalPrice, 9999999);
        amount = Math.round(amount * 100.0) / 100.0;
        Invoice invoice = orderController.checkout(invoiceID, totalPrice, amount, order, dateTime, reservation, subtotal);
        printf("Change: \t\t\t\t$%.2f\n", invoice.getPayment().getChange());
        println("Thank you! Have a nice day.\n");

        orderController.removeReservation(table);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Checkout order");
        println("");
        checkoutTable(table);
    }
}
