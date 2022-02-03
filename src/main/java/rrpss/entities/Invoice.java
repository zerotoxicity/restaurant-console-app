package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 Represents an Invoice of a checked out order
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Invoice implements Serializable {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Specifies where Invoice instances should be stored.
     */
    private final static String DATA = "data/invoices.dat";

    /**
     * checked out Order for customer.
     */
    private Order order;

    /**
     * Reservation made for order.
     */
    private Reservation reservation;

    /**
     * datetime of when invoice was created.
     */
    private LocalDateTime datetime;

    /**
     * ID to uniquely identify Invoice
     */
    private String ID;

    /**
     * payment made for order
     */
    private Payment payment;

    /**
     * Class constructor
     * @param ID
     * @param order Order object tied to the invoice
     * @param payment Payment object
     * @param datetime datetime of the transaction occurrence
     * @param reservation Reservation object
     */
    public Invoice(String ID, Order order, Payment payment, LocalDateTime datetime, Reservation reservation) {
        this.ID = ID;
        this.datetime = datetime;
        this.order = order;
        this.payment = payment;
        this.reservation = reservation;
    }

    /**
     * Get order.
     * @return order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Set order.
     * @param order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Get transaction datetime.
     * @return datetime.
     */
    public LocalDateTime getDatetime() {
        return datetime;
    }

    /**
     * Set transaction datetime.
     * @param datetime
     */
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * Get ID.
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Set ID.
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Get payment.
     * @return payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * Get reservation.
     * @return reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * Set payment.
     * @param payment
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * Get all existing invoices
     * @return all existing invoices
     */
    public static ArrayList<Invoice> retrieveAll() {
        return (ArrayList<Invoice>) Store.readSerializedObject(DATA);
    }

    /**
     * Saves current instance of Invoice
     */
    public void save() {
        ArrayList<Invoice> list = (ArrayList) Store.readSerializedObject(DATA);
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