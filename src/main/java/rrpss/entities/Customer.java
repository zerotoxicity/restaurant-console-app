package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 Represents a Customer
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Customer implements Serializable {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Specifies where Customer instances should be stored.
     */
    private final static String DATA = "data/customers.dat";

    /**
     * ID to uniquely identify Reservation.
     */
    private String ID;

    /**
     * Stores a list of customers current reservation.
     * Reservations will be deleted by a control class when a user
     * has checked out or a reservation has expired.
     */
    private ArrayList<Reservation> reservations;

    /**
     * Stores a list of customers past reservations that have been checked out.
     * Adding of new reservation will be invoked by a control class.
     */
    private ArrayList<Reservation> pastReservations;

    /**
     * Customer's name.
     */
    private String name;

    /**
     * Customer's membership type. NULL membership implies no membership.
     */
    private Membership membership;

    /**
     * Customer's phone number
     */
    private int contactDetails;


    /**
     * Class constructor
     * @param ID
     * @param name
     * @param membership
     * @param contactDetails
     */
    public Customer(String ID, String name, Membership membership, int contactDetails) {
        this.ID = ID;
        this.name = name;
        this.membership = membership;
        this.contactDetails = contactDetails;
        this.reservations = new ArrayList();
        this.pastReservations = new ArrayList();
    }

    /**
     * Class constructor
     * @param reservations
     * @param ID
     * @param name
     * @param membership
     * @param contactDetails
     */
    public Customer(ArrayList<Reservation> reservations, String ID, String name, Membership membership, int contactDetails) {
        this.reservations = reservations;
        this.ID = ID;
        this.name = name;
        this.membership = membership;
        this.contactDetails = contactDetails;
    }

    /**
     * Get all reservation by this customer.
     * @return reservations.
     */
    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    /**
     *  Remove reservation at the specified index and saves the class instance.
     * @param index
     */
    public void removeReservationsByIndex(int index) {
        reservations.remove(index);
        this.save();
    }

    /**
     * Remove reservation and saves the class instance.
     * @param toRemove
     */
    public void removeReservation(Reservation toRemove) {
        reservations.remove(toRemove);
        this.save();
    }


    /**
     * Get customer's past reservations.
     * @return
     */
    public ArrayList<Reservation> getPastReservations() {
        return pastReservations;
    }

    /**
     * Get customer's ID.
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get customer name.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Check if customer has any membership.
     * @return true if yes
     */
    public Boolean isMember() {
        return membership != null;
    }

    /**
     * Get customer membership type.
     * @return membership type.
     */
    public Membership getMembership() {
        return membership;
    }

    /**
     * Get customer contact details.
     * @return contact details.
     */
    public int getContactDetails() {
        return contactDetails;
    }

    /**
     * Sets customer's membership.
     * @param membership
     */
    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    /**
     * Add upcoming reservation.
     * @param reservation
     */
    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
        this.save();
    }

    /**
     * Add expired reservation made by the customers.
     * @param reservation
     */
    public void addPastReservation(Reservation reservation) {
        this.pastReservations.add(reservation);
        this.save();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Customer) {
            return (getID().equalsIgnoreCase(((Customer) o).getID()));
        }
        return false;
    }

    public static void removeMembership(String name) {
        ArrayList<Customer> list = (ArrayList) Store.readSerializedObject(DATA);
        int found = -1;
        Customer customer = null;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getName().equals(name)) {
                found = i;
                customer = list.get(i);
                customer.membership = null;
                customer.save();
                break;
            }
        if (found == -1)
            return;
        else
            list.set(found, customer);
        Store.writeSerializedObject(DATA, list);
    }

    public static void deleteCustomer(String id) {
        ArrayList<Customer> list = (ArrayList) Store.readSerializedObject(DATA);
        int found = -1;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getID().equalsIgnoreCase(id)) {
                found = i;
                break;
            }
        if (found == -1)
            return;
        else
            list.remove(found);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * Saves customer entity.
     */
    public void save() {
        ArrayList<Customer> list = (ArrayList) Store.readSerializedObject(DATA);
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
     * Get all existing customers.
     * @return existing customers.
     */
    public static ArrayList<Customer> getCustomers() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

}