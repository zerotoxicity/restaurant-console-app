package rrpss.entities;

import rrpss.store.Store;
import rrpss.util.Crypto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 Represents a Order
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Order implements Serializable {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;

    /**
     * Specifies where Order instances should be stored.
     */
    private static final String DATA = "data/orders.dat";

    /**
     * staff in-charge of current order
     */
    private Staff server;

    /**
     * table that order resides at made by customer
     */
    private Table table;

    /**
     * ID to uniquely identify Reservation.
     */
    private String ID;

    /**
     * list of orderItems made by customers
     */
    private ArrayList<OrderItem> orderItems;

    /**
     * datetime of when order was created
     */
    private LocalDateTime datetime;

    /**
     * tax constant
     */
    private final double GST = 0.07;

    /**
     * service charge constant
     */
    private final double svcCharge = 0.1;

    /**
     * get the total tax percentage
     */
    private double tax = GST + svcCharge;

    /**
     * Class constructor, if no order is taken yet
     * @param ID
     * @param server
     * @param table
     */
    public Order(String ID, Staff server, Table table) {
        this.ID = ID;
        this.server = server;
        this.table = table;
        this.orderItems = new ArrayList();
        this.datetime = LocalDateTime.now();
    }

    /**
     * Class constructor if order is taken
     */
    public Order(Staff server, ArrayList<OrderItem> orderItems, Table table) {
        this.ID = Crypto.genUUID();
        this.table = table;
        this.orderItems = orderItems;
        this.server = server;
        this.datetime = LocalDateTime.now();
    }

    /**
     * Get table
     * @return table
     */
    public Table getTable() {
        return this.table;
    }

    /**
     * Set table
     * @param table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Return list of order items with quantity
     *
     * @return ArrayList of Menu objects
     */
    public ArrayList<OrderItem> getGenericItems() {
        return this.orderItems;
    }

    /**
     * Get datetime of the order
     * @return datetime of the order
     */
    public LocalDateTime getDatetime() {
        return this.datetime;
    }

    /**
     * Get order ID
     * @return id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get all ordered items
     * @return items that have been ordered
     */
    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Set order datetime
     * @param datetime
     */
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    /**
     * Get current tax rate
     *
     * @return tax rate
     */
    public double getTax() {
        return this.tax;
    }

    /**
     *  Get current GST in decimals
     * @return GST
     */
    public double getGST() {
        return GST;
    }

    /**
     * Get current GST in percentage
     * @return GST in percentage
     */
    public double getGSTPercentage() {
        return GST * 100;
    }

    /**
     * Get current service charge in decimals
     * @return service charge
     */
    public double getSvcCharge() {
        return svcCharge;
    }

    /**
     * Get current service charge in percentage
     * @return service charge in percentage
     */
    public double getSvcChargePercent() {
        return svcCharge * 100;
    }


    /**
     * Set tax
     * @param tax
     */
    public void setGST(double tax) {
        this.tax = tax;
    }


    /**
     * Returns staff in charge of the order
     *
     * @return Staff
     */
    public Staff getStaff() {
        return this.server;
    }

    /**
     * Add/update order items
     * @param orderItem
     */
    public void setOrderItem(OrderItem orderItem) {
        int found = -1;
        for (int i = 0; i < orderItems.size(); i++)
            if (orderItems.get(i).getID().equalsIgnoreCase(orderItem.getID())) {
                found = i;
                break;
            }
        if (found == -1)
            orderItems.add(orderItem);
        else
            orderItems.set(found, orderItem);
    }

    /**
     * Set Order items
     * @param orderItems
     */
    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
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
     * Get all existing orders
     * @return existing orders
     */
    public static ArrayList<Order> getOrders() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * Add/update this order to the database
     */
    public void save() {
        ArrayList<Order> list = (ArrayList) Store.readSerializedObject(DATA);
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
     * Remove this order from the database
     */
    public void delete() {
        ArrayList<Order> list = (ArrayList) Store.readSerializedObject(DATA);
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

}