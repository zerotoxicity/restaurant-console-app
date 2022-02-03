package rrpss.ui.reservation;

import rrpss.controller.AdminController;
import rrpss.entities.Customer;
import rrpss.entities.MenuItem;
import rrpss.entities.Reservation;
import rrpss.entities.Table;
import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.customer.CustomerController;
import rrpss.ui.tables.TableController;
import rrpss.ui.tables.ViewTableUI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Boundary class for creating a reservation
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CreateReservationUI extends UI {

    private ReservationController reservationController;
    private CustomerController customerController;
    private ViewTableUI tableUI;

    /**
     * Class constructor
     * @param reservationController reservation control class
     * @param customerController customer control class
     */
    CreateReservationUI(ReservationController reservationController, CustomerController customerController) {
        this.reservationController = reservationController;
        this.customerController = customerController;
        this.tableUI = new ViewTableUI();
    }

    /**
     * stdout interface showing CreateReservationUI options of creating a reservation
     */
    private void form() {

        lifecycle:
        while (true) {
            // Get reservation date
            LocalDate dateOnly = promptDateOnly((LocalDate _date) -> {
                // Ensures that booking is made in advanced
                if (_date.isBefore(LocalDate.now()))
                    return "Bookings can only be made in advance!";

                // Check if restaurant is opened now
                if (_date.isEqual(LocalDate.now()) && !AdminController.getInstance().isOpened(LocalTime.now().plusSeconds(1)))
                    return String.format("Restaurant is closed from this period %s\n", LocalTime.now().plusSeconds(1));

                return null;
            });

            // Get number of pax
            int pax = promptInt("Enter number of pax: ", 1, 10);
            if(tableUI.displayTablesByPax(pax, dateOnly)) return;

//            // Check if its full house
//            ArrayList<Table> _fullHouse = TableController.getInstance().getAvailableTables(pax, LocalDateTime.of(dateOnly,AdminController.getInstance().getOpening() ) , LocalDateTime.of(dateOnly,  AdminController.getInstance().getClosing()));
//            if (_fullHouse.isEmpty()) {
//                println("There are no more tables available");
//                String cont = promptStr("Continue [Y]/[N]: ");
//                if (cont.equalsIgnoreCase("Y")) continue;
//                break lifecycle;
//            }


            // Get time
            LocalTime timeOnly = promptTimeOnly((LocalTime _time) -> {
                // Ensure that booking is not before current time
                if (!AdminController.getInstance().isOpened(_time))
                    return String.format("Restaurant is closed from this period %s\n", _time);
                return null;
            });

            LocalDateTime date = LocalDateTime.of(dateOnly, timeOnly);

            // Get available tables
            ArrayList<Table> availableTables = TableController.getInstance().getAvailableTables(pax, date, date.plusHours(AdminController.getInstance().DINING_HOURS));

            // If no tables, renter or exit
            if (availableTables.isEmpty()) {
                println("There are no more tables available");
                String cont = promptStr("Continue [Y]/[N]: ");
                if (cont.equalsIgnoreCase("Y")) continue;
                break lifecycle;
            }

            // Display tables
            subtitle("Available Tables");
            for (int i = 0; i < availableTables.size(); i++)
                printf("[%d] Table %d Seats %d\n", i, availableTables.get(i).getNumber(), availableTables.get(i).getSeats());

            // Select table
            int index = promptInt("Select Table: ", 0, availableTables.size() - 1);
            Table table = availableTables.get(index);

            // Get contact
            int contact = promptInt("Enter contact number: ", (Integer number) -> {
                if (number < 0) return "Invalid number";
                if (String.valueOf(number).length() != 8)
                    return "Number must be 8 characters";
                return "";
            });
            Customer customer = customerController.getCustomerByNumber(contact);

            // Create customer if not exists
            if (customer == null) {
                String name = promptStr("Enter name: ", (String s) -> {
                    if (s.isEmpty()) return "Please enter a valid name";
                    if (!s.matches("[a-zA-Z]+")) return "Only alpha characters are accepted";
                    return "";
                });
                customer = customerController.createCustomer(name, contact);
            }


            // Check if customer has booking on the same day
            if (reservationController.hasReservation(customer, date.toLocalDate())) {
                printf("Booking is for one customer per day!\n");
                break lifecycle;
            }
            printf("Creating reservation for %s\n", customer.getName());

            // Create Reservation
            reservationController.createReservation(customer, table, date, date.plusHours(AdminController.getInstance().DINING_HOURS), pax);

            printf("Reservation successfully booked! \n");
            break lifecycle;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Create Reservation");
        // Show available tables
        subtitle(String.format("Restaurant is opened from %s to %s", AdminController.getInstance().getOpening().format(timeFormat), AdminController.getInstance().getClosing().format(timeFormat)));
        form();
    }
}