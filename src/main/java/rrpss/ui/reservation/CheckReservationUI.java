package rrpss.ui.reservation;

import rrpss.ui.tables.TableController;
import rrpss.entities.Reservation;
import rrpss.ui.Router;
import rrpss.ui.UI;
import rrpss.ui.app.App;
import rrpss.ui.app.Navigable;
import rrpss.ui.customer.CustomerController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Boundary class for checking and removing reservation
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class CheckReservationUI extends UI {
    ReservationController reservationController;
    CustomerController customerController;

    /**
     * Class constructor
     * @param reservationController reservation control class
     * @param customerController customer control class
     */
    CheckReservationUI(ReservationController reservationController, CustomerController customerController) {
        this.reservationController = reservationController;
        this.customerController = customerController;
    }


    /**
     * stdout interface to view reservations based on user's input
     */
    private void checkByDate() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/M/yyyy");
        LocalDate date = promptDateOnly();
        ArrayList<Reservation> reservations = reservationController.getReservationsByDate(date, false);
        for (Reservation reservation : reservations) {
            printf("%-10s Number: %-10s Pax: %-10s Table: %-10s %s %s\n",
                    reservation.getCustomer().getName(),
                    reservation.getCustomer().getContactDetails(),
                    reservation.getPax(),
                    reservation.getReservedTable().getNumber(),
                    reservation.getFrom().format(DateTimeFormatter.ofPattern("dd/M/yyyy kk:mm")),
                    reservation.isExpired(TableController.getInstance().getGracePeriod()) ? " | Expired" : ""
            );
        }
        println("");
    }


    /**
     * stdout interface to view reservations for LocalDate.now()
     */
    private void checkReservationsForToday(boolean filterCheckedin) {
        ArrayList<Reservation> reservations = reservationController.getReservationsByDate(LocalDate.now());
        if (reservations.isEmpty()) {
            subtitle(String.format("No reservations %s", LocalDateTime.now().format(dateTimeFormatter)));
            println("");
            return;
        }

        subtitle(String.format("Reservations for Today %s", LocalDateTime.now().format(dateTimeFormatter)));
        println("");
        for (Reservation reservation : reservations) {
            if (filterCheckedin && reservation.isCheckedIn()) continue;
            printf("%-10s Number: %-10s Pax: %-10d Table: %-10d From: %s To: %s %s\n",
                    reservation.getCustomer().getName(),
                    reservation.getCustomer().getContactDetails(),
                    reservation.getPax(),
                    reservation.getReservedTable().getNumber(),
                    reservation.getFrom().format(dateTimeFormatter),
                    reservation.getTo().format(dateTimeFormatter),
                    reservation.isExpired(TableController.getInstance().getGracePeriod()) ? " | Expired" : ""
            );
        }
        println("");
    }

    /**
     * checkIn stdout flow
     *
     * @throws Navigable allows invocation of try catch. Enables flow such as breaks
     */
    private void checkIn() throws Navigable {
        checkReservationsForToday(true);
        int number = promptInt("Enter customer number");
        Reservation reservation = reservationController.getReservationsByNumber(number, LocalDate.now());
        if (reservation == null) {
            println("Reservation not found.");
            return;
        }

        // Check if customer has checked in
        if (reservation.isCheckedIn()) {
            println("Customer already checked in!");
            return;
        }

        // Check if reservation has expired
        if (reservation.isExpired(TableController.getInstance().getGracePeriod())) {
            println("Reservation expired!\n");
            // Debate to delete
            return;
        }

        final LocalDateTime withGracePeriod = reservation.getFrom().minusMinutes(TableController.getInstance().getGracePeriod());

        // Check if reservation is GRACE_PERIOD before check in
        if (withGracePeriod.isAfter(LocalDateTime.now()) ||
                withGracePeriod.compareTo(LocalDateTime.now()) == 0) {
            println(String.format("You can only check in at %s", withGracePeriod.format(dateTimeFormatter)));
            println();
            return;
        }

        // Check customer in
        reservationController.checkInReservation(reservation);

        subtitle(String.format("%s successfully checked in!", reservation.getCustomer().getName()));

        // Navigate to orders
        App.getInstance().navigateTo(true, Router.RoutePath.ORDER);
        return;
    }


    /**
     * stdout interface showing CheckReservationUI options
     */
    private void form() {
        lifecycle:
        while (true) {
            println("[0] Check In Customer");
            println("[1] Show all reservations for today");
            println("[2] Check reservations by date");
            println("[3] Back");
            int option = promptInt();

            switch (option) {
                case 0:
                    try {
                        checkIn();
                    } catch (Navigable e) {
                        break lifecycle;
                    }
                    break;
                case 1:
                    checkReservationsForToday(false);
                    break;
                case 2:
                    checkByDate();
                case 3:
                    return;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Check Reservation");
        form();
    }
}
