package rrpss.ui.report;

import rrpss.entities.CourseType;
import rrpss.entities.Invoice;
import rrpss.entities.OrderItem;
import rrpss.ui.UI;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Boundary class for sales revenue report
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ReportUI extends UI {
    ReportController reportController = new ReportController();

    /**
     * Print details of the order item
     * @param item Order Item to be printed
     */
    private void printOrderItem(OrderItem item) {
        int quantity = item.getQuantity();
        double netPrice = item.getNetPrice();
        CourseType type = item.getMenuItem().getType();
        String name = item.getMenuItem().getName();
        printf("[%s]\n%s  \n", type.toString(), name);
        printf("%.2f \t\t\t\tx%d \t\t\t\t\t%.2f\n", netPrice, quantity, quantity * netPrice);
        println();
    }

    /**
     * Print details of the report item
     * @param reportItem Report item to be printed
     */
    private void printReportItem(ReportItem reportItem) {
        OrderItem item = reportItem.getItem();
        int quantity = reportItem.getTotalQuantity();
        double netPrice = item.getNetPrice();
        CourseType type = item.getMenuItem().getType();
        String name = item.getMenuItem().getName();
        printf("[%s]\n%s  \n", type.toString(), name);
        printf("%.2f \t\t\t\tx%d \t\t\t\t\t%.2f\n", netPrice, quantity, quantity * netPrice);
        println();
    }

    /**
     * Print sales revenue report for one day
     * @param invoiceArrayList Array list containing one day worth of invoices
     * @param day Flag for one day or one month
     */
    public void reportDetailsByDay(ArrayList<Invoice> invoiceArrayList, boolean day) {
        int i = 0;
        HashMap<String, Integer> allOrderItems = new HashMap<>();
        double total = 0;
        double subtotal = 0;
        double svcCharge = 0;

        //Period, individual sale item, total revenue
        for (Invoice invoice : invoiceArrayList) {
            //Print Date
            if (i == 0) {
                title("Sales revenue report for " + invoice.getDatetime().toLocalDate());
            }
            //Print Items
            for (OrderItem item : invoice.getOrder().getOrderItems()) {
                printOrderItem(item);
            }

            allOrderItems = reportController.getOrderItemForTheDay(invoice, allOrderItems);
            total += invoice.getPayment().getAmount();
            subtotal += invoice.getPayment().getSubtotal();
            svcCharge += invoice.getPayment().getSvcCharge();
            i++;
        }
        if (total == 0) {
            if (day) println("No sales have been made today.");
            return;
        }
        println("------------------------------------------------------------------");
        printf("Total revenue for the day: \t\t\t\t$%.2f\n", subtotal);
        printf("Total service charge for the day: \t\t$%.2f\n", svcCharge);
        printf("Total for the day: \t\t\t\t\t\t$%.2f\n", subtotal + svcCharge);
        println("------------------------------------------------------------------");
        reportController.updateTotalPrice(total);

    }

    /**
     * Get the invoices for the specified date and print their details
     * @param date Date that requires printing
     * @param day Flag for one day or one month
     */
    public void printReportDate(LocalDate date, boolean day) {
        ArrayList<Invoice> filteredInvoice = reportController.filterInvoiceByDate(date);
        reportDetailsByDay(filteredInvoice, day);
    }

    /**
     * Print the sales revenue report for the whole month
     */
    public void printReportMonth() {
        reportController.clear();
        println("Enter the month and year: e.g. 10/2021");
        YearMonth yearMonth = promptMonthYear();
        int daysInMonth = yearMonth.lengthOfMonth();
        title("Sales revenue report for " + yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        for (int i = 1; i <= daysInMonth; i++) {
            printReportDate(yearMonth.atDay(i), false);
        }
        title("Sales revenue report for the month");
        // printOrderItem(reportController.getTotalOrderItem());
        // Refactored
        ArrayList<ReportItem>  reportItems = reportController.getReportItemsByMonth(yearMonth);
        for (ReportItem item : reportItems) {
            printReportItem(item);
        }

        double[] revenue = reportController.getRevenue(yearMonth);
        println("------------------------------------------------------------------");
        printf("Total revenue for the month: \t\t\t$%.2f\n", revenue[0]);
        printf("Total service charge for the month: \t$%.2f\n", revenue[1]);
        printf("Total for the month: \t\t\t\t\t$%.2f\n", revenue[0] + revenue[1]);
        println("------------------------------------------------------------------");

        reportController.clear();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Print sales revenue report");
        println("[0] By date");
        println("[1] By month");
        println("[2] Exit");
        int num = promptInt(true, 0, 2);
        switch (num) {
            case 0:
                LocalDate date = promptDate(String.format("Enter the date: e.g. %s", LocalDate.now().format(dateFormat)));
                printReportDate(date, true);
                break;
            case 1:
                printReportMonth();
                break;
            default:
                break;
        }
    }
}
