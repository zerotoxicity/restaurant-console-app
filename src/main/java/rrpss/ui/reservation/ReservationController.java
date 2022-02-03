package rrpss.ui.reservation;

import rrpss.ui.RouteMap;
import rrpss.ui.app.Back;
import rrpss.ui.customer.CustomerController;
import rrpss.ui.tables.TableController;
import rrpss.controller.AuthController;
import rrpss.entities.Customer;
import rrpss.entities.Order;
import rrpss.entities.Reservation;
import rrpss.entities.Table;
import rrpss.ui.tables.ViewTableUI;
import rrpss.util.Crypto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Control class for reservation
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ReservationController {

    private CustomerController customerController = new CustomerController();


    /**
     * Available route for user
     */
    public RouteMap[] routes = {
            new RouteMap("Create reservation", new CreateReservationUI(this, customerController)),
            new RouteMap("Check reservation", new CheckReservationUI(this, customerController)),
            new RouteMap("View Table availability", new ViewTableUI()),
            new RouteMap("Remove reservation", new RemoveReservationUI(this, customerController)),
            new RouteMap("Go Back", new Back()),
    };

    /**
     * TODO ensure check in 10 minutes before booking
     * Checks in a reservation
     *
     * @param reservation
     */
    public void checkInReservation(Reservation reservation) {
        // Retrieve reserved table
        Table table = TableController.getInstance().getTable(reservation.getReservedTable().getID());
        // Check in reservation
        reservation.checkIn(table);
        // Assign table's current reservation
        table.setCurrentReservation(reservation);
        // Create a new Order for reservation
        table.setCurrentOrder(new Order(
                Crypto.genUUID(),
                AuthController.getInstance().getStaff(),
                table
        ));
        table.save();
    }

    /**
     * Checks if customer has reservation
     * @param customer
     * @param date
     * @return
     */
    public boolean hasReservation(Customer customer, LocalDate date) {
        // Check if customer has booking on the same day
        for (Reservation reservation : customer.getReservations()) {
            if (reservation.getFrom().toLocalDate().isEqual(date)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a reservation based on the specified parameters. Created reservation will be saved
     * with list of reservations. Table and customer will be mutated by adding the created reservation
     *
     * @param customer
     * @param reservedTable
     * @param from
     * @param to
     * @param pax
     * @return returns created reservation
     */
    public Reservation createReservation(Customer customer, Table reservedTable, LocalDateTime from, LocalDateTime to, int pax) {
        // Sanity check if table is reserved
        reservedTable.isReserved(from, to);

        Reservation reservation = new Reservation(
                Crypto.genUUID(),
                customer,
                reservedTable,
                from,
                to,
                false,
                pax
        );
        reservation.save();
        reservedTable.addReservation(reservation);
        customer.addReservation(reservation);
        return reservation;
    }


    /**
     * Get reservations by date
     *
     * @param date
     * @param filterExpired if expired resevations should be shown
     * @return
     */
    public ArrayList<Reservation> getReservationsByDate(LocalDate date, boolean filterExpired) {
        return _getReservationsByDate(date, filterExpired);
    }

    /**
     * Get reservations by date.
     *
     * @param date
     * @return reservations specified by date
     */
    public ArrayList<Reservation> getReservationsByDate(LocalDate date) {
        return _getReservationsByDate(date, true);
    }

    /**
     * Get reservations by date
     *
     * @param date
     * @param filterExpired depends if expired resevations should be shown
     * @return
     */
    private ArrayList<Reservation> _getReservationsByDate(LocalDate date, boolean filterExpired) {
        ArrayList<Reservation> reservations = Reservation.getReservations();
        ArrayList<Reservation> byDate = new ArrayList();
        for (Reservation reservation : reservations) {
            if (reservation.getFrom().toLocalDate().isEqual(date) && (filterExpired ? !reservation.isExpired(TableController.getInstance().getGracePeriod()) : true))
                byDate.add(reservation);
        }
        return byDate;
    }

    /**
     * Get reservations by number, date.
     *
     * @param date
     * @return
     */
    public Reservation getReservationsByNumber(int number, LocalDate date) {
        for (Reservation reservation : getReservationsByDate(date)) {
            if (reservation.getCustomer().getContactDetails() == number)
                return reservation;
        }
        return null;
    }
}
