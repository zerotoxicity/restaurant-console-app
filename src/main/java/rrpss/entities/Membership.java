package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 Represents a Customer's Membership
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Membership implements Serializable {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Specifies where Membership instances should be stored.
     */
    private static final String DATA = "data/memberships.dat";

    /**
     * ID to uniquely identify Membership.
     */
    private String ID;

    /**
     * discount benefit of current instance of Membership
     */
    private double discount;

    /**
     * Max cap of a discount. I.E cap = 10 when 10000 * 0.1
     */
    private int cap;

    /**
     * stereotype of membership type
     */
    private MembershipType type;

    /**
     * minSpending to create Membership
     */
    private double minSpending;

    /**
     * Class constructor
     * @param ID
     * @param discount
     * @param cap
     * @param type
     */
    public Membership(String ID, double discount, int cap, MembershipType type, double minSpending) {
        this.ID = ID;
        this.discount = discount;
        this.cap = cap;
        this.type = type;
        this.minSpending = minSpending;
    }

    /**
     * Get membership discount
     * @return discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Get cap of membership discount
     * @return discount cap
     */
    public int getCap() {
        return cap;
    }

    /**
     * Get membership type
     * @return membership type
     */
    public MembershipType getType() {
        return type;
    }

    /**
     * Get membership ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * get Min Spending
     * @return
     */
    public double getMinSpending() {
        return minSpending;
    }

    /**
     * If discount exceeds cap, return the cap.
     *
     * @param amount
     * @return
     */
    public double calculateDiscount(double amount) {
        if (amount * (discount / 100) > cap) return cap;
        return amount * (discount / 100);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Order) {
            return (getID().equalsIgnoreCase(((Order) o).getID()));
        }
        return false;
    }

    /**
     * Get all existing membership types
     * @return all existing membership types
     */
    public static ArrayList<Membership> getMemberships() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * Overrides the membership in file db
     *
     * @param list
     */
    public static void set(ArrayList<Membership> list) {
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * Save instances of Membership into db
     */
    public void save() {
        ArrayList<Membership> list = (ArrayList) Store.readSerializedObject(DATA);
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
}
