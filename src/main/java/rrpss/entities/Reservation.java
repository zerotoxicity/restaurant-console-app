package rrpss.entities;

import rrpss.ui.tables.TableController;
import rrpss.store.Store;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Reservation entity
 */
public class Reservation implements Serializable, Comparable<Reservation> {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * specifies where Reservation data should be stored
     */
    private final static String DATA = "data/reservations.dat";
    /**
     * Customer who made the reservation
     */
    private Customer customer;
    /**
     * table being reserved
     */
    private Table reservedTable;
    /**
     * id to uniquely identify a reservation
     */
    private String ID;
    /** 
     * start time of reservation
     */
    private LocalDateTime from;
    /**
     * end time of reservation
     */
    private LocalDateTime to;
    /**
     * whether customer checked in to reservation
     */
    private boolean checkedIn;
    /**
     * pax of reservation
     */
    private int pax;

    /**
     * Class constructor
     * @param ID
     * @param customer
     * @param reservedTable
     * @param from reservation start time
     * @param to reservation release time
     * @param checkedIn
     * @param pax
     */
    public Reservation(String ID, Customer customer, Table reservedTable, LocalDateTime from, LocalDateTime to, boolean checkedIn, int pax) {
        this.customer = customer;
        this.reservedTable = reservedTable;
        this.ID = ID;
        this.from = from;
        this.to = to;
        this.checkedIn = checkedIn;
        this.pax = pax;
    }

    /**
     * Get reservation end time
     * @return reservation end time
     */
    public LocalDateTime getTo() {
        return to;
    }

    /**
     * Check if customer has checked in
     * @return true if customer has checked in, else false
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Check in only when reservation is not expired
     */
    public void checkIn(Table table) {
        if (this.checkedIn)
            return;
        if (this.isExpired(TableController.getInstance().getGracePeriod()))
            return;

        this.reservedTable = table;
        this.checkedIn = true;
        this.save();
    }

    /**
     *  Check if reservation has expired
     * @return true if expired
     */
    public boolean isExpired() {
        if (LocalDateTime.now().isAfter(this.getFrom()))
            return true;
        return false;
    }

    /**
     * Check if reservation has expired, even with grace period
     * @param minutes grace period
     * @return true if expired
     */
    public boolean isExpired(long minutes) {
        if (LocalDateTime.now().isAfter(this.getFrom().plusMinutes(minutes)))
            return true;
        return false;
    }

    /**
     * Get the  customer who has reserved this table
     * @return customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Get the tables that has been reserved
     * @return tables
     */
    public Table getReservedTable() {
        return reservedTable;
    }

    /**
     * Get reservation ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get reservation start time
     * @return start time
     */
    public LocalDateTime getFrom() {
        return from;
    }

    /**
     * Get number of pax in customers' party
     * @return number of pax
     */
    public int getPax() {
        return pax;
    }

    /**
     * Get all existing reservations
     * @return reservations
     */
    public static ArrayList<Reservation> getReservations() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Reservation) {
            return (getID().equalsIgnoreCase(((Reservation) o).getID()));
        }
        return false;
    }


    /**
     * Add/update reservation to the database
     */
    public void save() {
        ArrayList<Reservation> list = (ArrayList) Store.readSerializedObject(DATA);
        int found = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(this)) {
                found = i;
                break;
            }
        if (found == -1)
            list.add(this);
        else
            list.set(found, this);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * Remove reservation from the database
     */
    public void delete() {
        ArrayList<Reservation> list = (ArrayList) Store.readSerializedObject(DATA);
        int found = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).equals(this)) {
                found = i;
                break;
            }
        if (found == -1)
            return;
        list.remove(found);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Reservation o) {
        return getFrom().compareTo(o.getFrom());
    }
}