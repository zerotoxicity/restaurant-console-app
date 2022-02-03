package rrpss.entities;


import java.io.Serializable;

/**
 * Order Item entity
 */
/**
 Represents an OrderItem which stores a menu item
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class OrderItem implements Serializable {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * ID to uniquely identify Reservation.
     */
    private String ID;

    /**
     * quantity of OrderItem.
     */
    private int quantity;

    /**
     * netprice of a MenuItem.
     */
    private double netPrice;

    /**
     * menuItem from a menu.
     */
    private MenuItem menuItem;

    /**
     * order notes of a customer's request.
     */
    private String notes;

    /**
     * Class constructor with no variables initialized
     */
    OrderItem() {}

    /**
     * Class constructor
     * @param id
     * @param menuItem
     * @param quantity
     * @param notes
     */
    public OrderItem(String id, MenuItem menuItem, int quantity, String notes){
        this.ID = id;
        this.menuItem = menuItem;
        this.netPrice = menuItem.getPrice();
        this.quantity = quantity;
        this.notes = notes;
    }

    /**
     * Get ID
     * @return ID
     */
    public String getID() {
        return ID;
    }

    /**
     * Get Menu Items
     * @return menuItem
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * Get notes
     * @return notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Set notes
     * @param notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Set quantity of order items
     * @param quantity of order item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Get quantity of order item
     * @return quantity of order item
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Get total price of the order items
     * @return total price of the order items
     */
    public double getPrice() {
        return netPrice * quantity;
    }

    /**
     * Get price of a single order item
     * @return price of a single order item
     */
    public double getNetPrice() {
        return netPrice;
    }

    /**
     * Set price of a single order item
     * @param price new price of the single order item
     */
    public void setPrice(double price) {
        this.netPrice = price;
    }
}
