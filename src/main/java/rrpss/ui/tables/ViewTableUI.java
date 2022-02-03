package rrpss.ui.tables;

import rrpss.controller.AdminController;
import rrpss.entities.Reservation;
import rrpss.entities.Table;
import rrpss.ui.UI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Boundary class for viewing table availability
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ViewTableUI extends UI {

    final TableController tableController = TableController.getInstance();

    /**
     * Show available time slot for the table on the specified date
     * @param table table's time slot to be viewed
     * @param date
     */
    private void showDayAvailability(Table table, LocalDate date) {
        title(String.format("Table %d Seats: %d | Reservations: %d", table.getNumber(), table.getSeats(), table.getCurrentReservations().size()));
        // Retrieve opening hours
        LocalTime opens = AdminController.getInstance().getOpening(), close = AdminController.getInstance().getClosing().plusHours(AdminController.getInstance().DINING_HOURS);
        final ArrayList<Reservation> reservations = tableController.getReservationsByDate(table, date);

        // End function call if empty
        if (reservations.isEmpty()) {
            printf("Available from %s to %s\n", opens.format(timeFormat), close.minusHours(AdminController.getInstance().DINING_HOURS).format(timeFormat));
            return;
        }
        // reservation index to keep track of iterations
        int r = 0;
        LocalTime current = LocalTime.of(opens.getHour(), opens.getMinute());
        while (current.isBefore(close)) {
            // iterate reservations when index is less
            if (r < reservations.size()) {
                // Assuming time is sorted increasingly
                final Reservation reservation = reservations.get(r);
                final String from = reservation.getFrom().toLocalTime().format(timeFormat);
                final String to = reservation.getTo().toLocalTime().format(timeFormat);
                // Debug
//                System.out.printf("%s %s %s\n",reservation.getCustomer().getName(), reservation.getFrom(), reservation.getTo());
                // If a reservation slot is
                if (reservation.getFrom().toLocalTime().compareTo(current) == 0 ||
                        (reservation.getFrom().toLocalTime().isAfter(current) &&
                                reservation.getFrom().toLocalTime().isBefore(current.plusHours(AdminController.getInstance().DINING_HOURS)))
                ) {
                    // Check if it clashes with the next reservation
                    if (r + 1 < reservations.size() && reservations.get(r+1).getFrom().toLocalTime().isBefore(current.plusHours(AdminController.getInstance().DINING_HOURS))) {
                        continue;
                    }


                    printf("%s - %s: \t\t%s pax %d\n", from, to, reservation.getCustomer().getName(), reservation.getPax());
                    // Assign current time to the end time of the customer
                    current = reservation.getTo().toLocalTime();
                    r++;
                    continue;
                }
            }

            // Do not print available time if exceeds closing
            // Do not print available time when current exceeds
            if (current.plusHours(AdminController.getInstance().DINING_HOURS).isBefore(close))
                printf("%s - %s \t\tAvailable\n", current.format(timeFormat), current.plusHours(AdminController.getInstance().DINING_HOURS).format(timeFormat));
            current = current.plusHours(AdminController.getInstance().DINING_HOURS);
        }
        println();
    }

    public boolean  displayTables(LocalDate date) {
        final ArrayList<Table> tables = TableController.getInstance().getTables();
        if (tables.isEmpty()) {
            printf("No tables available on %s", date.format(dateFormat));
            return false;
        }
        printf("Tables available on %s\n", date.format(dateFormat));

        for (int i = 0; i < tables.size(); i++) {
            final Table table = tables.get(i);
            showDayAvailability(table, date);
        }
        return true;
    }

    /**
     * Show available tables
     */
    private void form() {
        LocalDate date = promptDateOnly();
        displayTables(date);
    }

    /**
     * Display tables by pax
     * @param pax
     * @param date
     * @return true if empty table
     */
    public boolean displayTablesByPax(int pax, LocalDate date) {
        ArrayList<Table> tables = TableController.getInstance().getAvailableTables(pax);
        if (tables.isEmpty()) {
            printf("No available tables with %d Pax on %s\n", pax, date.format(dateFormat));
            return true;
        }
        printf("%d Pax Tables available on %s\n", pax, date.format(dateFormat));

        for (int i = 0; i < tables.size(); i++) {
            final Table table = tables.get(i);
            showDayAvailability(table, date);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Table availability");
        form();
    }
}