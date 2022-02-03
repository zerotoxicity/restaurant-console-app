package rrpss.entities;

import java.io.Serializable;

/**
 Payment
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Payment implements Serializable {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6049881091480607663L;
    /**
     * unique id for a payment object
     */
    private String ID;
    /**
     * total amount to be paid
     */
    private double amount;
    /**
     * amount paid 
     */
    private double payable;
    /**
     * amount left to be paid
     */
    private double subtotal;
    /**
     * service charge
     */
    private double svcCharge;

    /**
     * Class constructor
     * @param ID
     * @param payable
     * @param amount
     * @param subtotal
     * @param svcCharge
     */
    public Payment(String ID, double payable, double amount, double subtotal, double svcCharge) {
        this.ID = ID;
        this.amount = amount;
        this.payable = payable;
        this.subtotal = subtotal;
        this.svcCharge = svcCharge;
    }

    /**
     * Get ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get total amount paid by customers
     * @return total amount paid by customer
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get total price of the order
     * @return total price of the order
     */
    public double getPayable() {
        return payable;
    }

    /**
     * Get customer's change
     * @return customer's change
     */
    public double getChange() {
        return amount - payable;
    }

    /**
     * Get total price before tax
     * @return total price before tax
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Get total service charge to be paid
     * @return total service charge to be paid
     */
    public double getSvcCharge() {
        return svcCharge;
    }
}
