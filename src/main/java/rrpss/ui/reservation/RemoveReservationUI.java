package rrpss.ui.reservation;

import rrpss.ui.tables.TableController;
import rrpss.entities.Customer;
import rrpss.entities.Reservation;
import rrpss.entities.Table;
import rrpss.ui.UI;
import rrpss.ui.customer.CustomerController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Boundary class for removing reservation
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class RemoveReservationUI extends UI {

    ReservationController reservationController;
    CustomerController customerController;

    /**
     * Class constructor
     * @param reservationController reservation control class
     * @param customerController customer control class
     */
    public RemoveReservationUI(ReservationController reservationController, CustomerController customerController) {
        this.reservationController = reservationController;
        this.customerController = customerController;
    }

    /**
     * Remove customer's reservation
     */
    public void form() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/M/yyyy kk:mm:ss");

        ArrayList<Customer> customers = customerController.getCustomers();
        if (customers.isEmpty()) {
            subtitle("There are no customers");
            return;
        }

        for (Customer customer : customers)
            printf("%-10s Number: %-10d\n", customer.getName(), customer.getContactDetails());

        lifecycle: while(true) {
            int number = promptInt("Enter customer contact number: ");
            Customer customer = customerController.getCustomerByNumber(number);
            if (customer == null) {
                println("Customer does not exist");
                String cont =  promptStr("Continue [Y]/[n]: ");
                if (cont.equalsIgnoreCase("Y")) continue;
                break lifecycle;
            }

            ArrayList<Reservation> reservations = customer.getReservations();
            if (reservations.isEmpty()) {
                subtitle("Customer has no reservations.");
                break lifecycle;
            }

            for (int i = 0; i < reservations.size(); i++)
                printf("[%d] %s \n", i, reservations.get(i).getFrom().format(pattern));

            int index;
            delete: while (true) {
                String in = promptStr("Select reservation to delete. [q] to cancel:");
                if (in.equalsIgnoreCase("q")) break lifecycle;
                try {
                    index = Integer.parseInt(in);
                    if (reservations.get(index) != null) break delete;
                } catch (NumberFormatException e) {

                } catch (IndexOutOfBoundsException e) {

                }
                println("Invalid input");
            }
            Reservation reservation = reservations.get(index);
            // Remove reservations from table if any
            Table table = TableController.getInstance().getTable(reservation.getReservedTable().getID());
            table.removeReservation(reservation);

            // Delete reservation
            reservation.delete();

            // Delete reservation from customer
            customer.removeReservationsByIndex(index);

            subtitle("Reservation removed");
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Remove reservation");
        form();
    }
}
