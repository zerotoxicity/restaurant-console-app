package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Table entity
 */
public class Table implements Serializable {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * specifies where to store Table instances
     */
    private final static String DATA = "data/tables.dat";
    /**
     * id that uniquely identifies Table instance
     */
    final private String ID;
    /**
     * table number
     */
    final private int number;
    /**
     * number of seats Table has
     */
    final private int seats;
    /**
     * current order made at the table
     */
    private Order currentOrder;
    /**
     * list of reservations made for the table
     */
    private ArrayList<Reservation> currentReservations;
    /**
     * reservation of the table at the current dateTime
     */
    private Reservation currentReservation;

    /**
     * Class constructor
     * @param ID
     * @param number table number
     * @param seats
     */
    public Table(String ID, int number, int seats) {
        this.ID = ID;
        this.seats = seats;
        this.number = number;
        this.currentReservations = new ArrayList<>();
    }

    /**
     * Class constructor with reservations added
     * @param currentOrder
     * @param ID
     * @param seats
     * @param number
     * @param currentReservations
     */
    public Table(Order currentOrder, String ID, int seats, int number, ArrayList<Reservation> currentReservations) {
        this.currentOrder = currentOrder;
        this.ID = ID;
        this.number = number;
        this.seats = seats;
        this.currentReservations = currentReservations;
    }

    /**
     * Check if table is reserved from a period
     *
     * @param from time to check if table is reserved
     * @return
     */
    public boolean isReserved(LocalDateTime from, LocalDateTime to) {
        for (Reservation reservation : currentReservations) {
            // Check if timings are the same
            if (reservation.getTo().equals(to) && reservation.getFrom().equals(from))
                return true;
            // Check if timing falls between from or to
            if ((from.isAfter(reservation.getFrom()) && from.isBefore(reservation.getTo()) ) || (
                    to.isAfter(reservation.getFrom()) && to.isBefore(reservation.getTo())))
                return true;
        }
        return false;
    }

    /**
     * removes expired reservations given after minutes of the actual booking time
     * @param minutes
     */
    public void removeExpiredReservation(long minutes) {
        ArrayList<Reservation> toRemove = new ArrayList();
        for (Reservation reservation : currentReservations)
            if (!reservation.isCheckedIn() && reservation.isExpired(minutes)) {
                // Remove from customer
                reservation.getCustomer().removeReservation(reservation);
                toRemove.add(reservation);
            }

        // Remove and save
        currentReservations.removeAll(toRemove);
        this.save();
    }

    /**
     * Remove reservation from the database
     * @param toRemove reservation to be removed
     */
    public void removeReservation(Reservation toRemove) {
        int index = -1;
        for (int i = 0; i < currentReservations.size(); i++) {
            if (currentReservations.get(i).getID().equalsIgnoreCase(toRemove.getID())) {
                index = i;
                break;
            }
        }
        if (index == -1) return;
        // Remove and save
        currentReservations.remove(index);
        this.save();
    }


    /**
     * Get all non-expired reservation for the table
     * @return reservations for the table
     */
    public ArrayList<Reservation> getCurrentReservations() {
//        ArrayList<Reservation> reservations = Reservation.getReservations();
//        ArrayList<Reservation> currentReservations = new ArrayList();
//        // Add current reservation
//        for (Reservation reservation : reservations)
//            if (reservation.getReservedTable().getID().equalsIgnoreCase(this.ID))
//                currentReservations.add(reservation);
//
//        this.currentReservations = currentReservations;
        return currentReservations;
    }

    /**
     * Add reservation to the table
     * @param reservation
     */
    public void addReservation(Reservation reservation) {
        this.currentReservations.add(reservation);
        Collections.sort(this.currentReservations);
        this.save();
    }

    /**
     * Get table's current order
     * @return  order
     */
    public Order getCurrentOrder() {
        return currentOrder;
    }

    /**
     * Sets currentOrder
     *
     * @param currentOrder
     */
    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    /**
     * Get table's current reservation
     * @return current table's reservation
     */
    public Reservation getCurrentReservation() {
        return currentReservation;
    }

    /**
     * Set table's current reservation
     * @param currentReservation
     */
    public void setCurrentReservation(Reservation currentReservation) {
        this.currentReservation = currentReservation;
    }

    /**
     * Get table's ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get table number
     * @return table number
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get table's number of seats
     * @return number of seats available
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Get all existing tables
     * @return all existing tables
     */
    public static ArrayList<Table> getTables() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Table) {
            return (getID().equalsIgnoreCase(((Table) o).getID()));
        }
        return false;
    }

    /**
     * Overrides the tables in file db
     *
     * @param list
     */
    public static void set(ArrayList<Table> list) {
        Store.writeSerializedObject(DATA, list);
    }

    public void save() {
        ArrayList<Table> list = (ArrayList) Store.readSerializedObject(DATA);
        int found = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(this)) {
                found = i;
                break;
            }
        // If not found, we add it to the list
        if (found == -1)
            list.add(this);
        else
            list.set(found, this);
        Store.writeSerializedObject(DATA, list);
    }
}