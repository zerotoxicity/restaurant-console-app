package rrpss.ui.report;

import rrpss.entities.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Control class for sales revenue report
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ReportController {
    private ArrayList<Invoice> existingInvoice;
    private double total = 0;
    private HashMap<String, Integer> totalOrderItem = new HashMap<>();

    /**
     * Constructor class
     */
    public ReportController() {
    }

    /**
     *
     * @return all existing invoices
     */
    public ArrayList<Invoice> getExistingInvoice() {
        this.existingInvoice = Invoice.retrieveAll();
        return this.existingInvoice;
    }

    /**
     *
     * @param date requested date
     * @return all invoices on the requested date
     */
    public ArrayList<Invoice> filterInvoiceByDate(LocalDate date) {
        ArrayList<Invoice> filteredInvoice = new ArrayList<>();
        for (Invoice i : getExistingInvoice()) {
            LocalDate invoiceDate = i.getDatetime().toLocalDate();
            if (invoiceDate.isEqual(date)) {
                filteredInvoice.add(i);
            }
        }
        return filteredInvoice;
    }

    /**
     *
     * @param date requested month
     * @return Array list containing the requested month of ordered items
     */
    public ArrayList<ReportItem> getReportItemsByMonth(YearMonth date) {
        HashMap<String, ReportItem> items = new HashMap();
        for (Invoice invoice : getExistingInvoice()) {
            LocalDate invoiceDate = invoice.getDatetime().toLocalDate();
            if (invoiceDate.getMonth().compareTo(date.getMonth()) != 0 ||
                    invoiceDate.getYear() != date.getYear()) continue;
            for (OrderItem item : invoice.getOrder().getOrderItems()) {
                String name = item.getMenuItem().getName();
                // update report item
                if (items.containsKey(name)) {
                    ReportItem reportItem = items.get(name);
                    reportItem.incrementQuantity(item.getQuantity());
                    items.replace(name, reportItem);
                } else { // Add new report item
                    items.put(name, new ReportItem(item, item.getQuantity()));
                }
            }
        }
        return new ArrayList(items.values());
    }

    /**
     *
     * @param invoice
     * @param allOrderItems
     * @return
     */
    public HashMap<String, Integer> getOrderItemForTheDay(Invoice invoice, HashMap<String, Integer> allOrderItems) {
        int totalQuantity;
        int quantity;
        for (OrderItem oi : invoice.getOrder().getOrderItems()) {
            String itemName = oi.getMenuItem().getName();
            quantity = oi.getQuantity();
            if (allOrderItems.containsKey(itemName)) {
                totalQuantity = allOrderItems.get(itemName) + quantity;
                allOrderItems.replace(itemName, totalQuantity);
            } else {
                allOrderItems.put(itemName, quantity);
            }

            if (this.totalOrderItem.containsKey(itemName)) {
                totalQuantity = this.totalOrderItem.get(itemName) + quantity;
                this.totalOrderItem.replace(itemName, totalQuantity);
            } else {
                this.totalOrderItem.put(itemName, quantity);
            }
        }
        return allOrderItems;
    }

    /**
     * Reset instance variable
     */
    public void clear() {
        this.total = 0;
        this.totalOrderItem.clear();
    }

    /**
     * Update total price for a month sales revenue report
     * @param total total revenue for a day
     */
    public void updateTotalPrice(double total) {
        this.total += total;
    }


    /**
     * Get total revenue for the specified month
     * @param date specified month
     * @return [subtotal, svccharge] List containing the month's subtotal and service charge
     */
    public double[] getRevenue(YearMonth date) {
        double subtotal = 0;
        double svccharge = 0;
        for (Invoice invoice : getExistingInvoice()) {
            LocalDate invoiceDate = invoice.getDatetime().toLocalDate();
            if (invoiceDate.getMonth().compareTo(date.getMonth()) != 0 ||
                    invoiceDate.getYear() != date.getYear()) continue;
            subtotal += invoice.getPayment().getSubtotal();
            svccharge += invoice.getPayment().getSvcCharge();
        }
        return new double[]{subtotal, svccharge};
    }

}
