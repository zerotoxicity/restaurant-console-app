package rrpss.entities;

/**
 * Interface for menu items
 */

import java.io.Serializable;

/**
 Generic Interface for storing a MenuItem
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public abstract class MenuItem implements Serializable,Comparable<MenuItem> {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6529685098267757690L;


    /**
     * ID to uniquely identify MenuItems.
     */
    private String ID;

    public MenuItem(String id) {
        this.ID=id;
    }

    /**
     * Get ID
     * @return ID
     */
    public String getID(){
        return this.ID;
    }

    /**
     * Get name
     * @return name
     */
    public abstract String getName();

    /**
     * Get net price
     * @return net price
     */
    public abstract double getPrice();

    /**
     * Get course type
     * @return course type
     */
    public abstract CourseType getType();

    /**
     * Get description
     */
    public abstract String getDesc();

    /**
     * Set menu item name
     * @param name
     */
    public abstract void setName(String name);
    /**
     * Set menu item price
     * @param price
     */
    public abstract void setPrice(double price);
    /**
     * Set menu item desc
     * @param desc
     */
    public abstract void setDesc(String desc);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MenuItem) {
            return (getID().equalsIgnoreCase(((MenuItem) o).getID()));
        }
        return false;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public int compareTo(MenuItem o) {
        return Integer.compare(this.getType().ordinal(), o.getType().ordinal());
    }

}
