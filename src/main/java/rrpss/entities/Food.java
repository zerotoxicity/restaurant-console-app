package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 Represents a Food Entity
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Food implements Serializable {
    /**
     * Specifies where Food instances should be stored.
     */
    private final static String DATA = "data/food.dat";

    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6049881091480607663L;

    /**
     * Name to identify food.
     */
    private String name;

    /**
     * Description of food.
     */
    private String description;

    /**
     * Price of food
     */
    private double price;

    /**
     * ID to uniquely identify food
     */
    private String foodID;

    /**
     * Course which stereotypes food on menu.
     */
    private CourseType course;

    /**
     * Class constructor
     * @param name
     * @param description
     * @param price
     */
    public Food(String foodID, String name, CourseType course, String description, double price) {
        this.foodID = foodID;
        this.name = name;
        this.course = course;
        this.description = description;
        this.price = price;
    }

    /**
     * Get food name.
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get food ID.
     * @return foodID
     */

    public String getFoodID() {
        return this.foodID;
    }

    /**
     * Get food course type.
     * @return CourseType
     */
    public CourseType getCourse() {
        return this.course;
    }

    /**
     * Set course type.
     * @param course
     */
    public void setCourse(CourseType course) {
        delete();
        this.course = course;
        save();
    }

    /**
     * Set name.
     * @param name
     */
    public void setName(String name) {
        delete();
        this.name = name;
        save();
    }

    /**
     * Get food description.
     * @return description
     */
    public String getDesc() {
        return this.description;
    }

    /**
     * Set description.
     * @param description
     */
    public void setDesc(String description) {
        delete();
        this.description = description;
        save();
    }

    /**
     * Get food's price.
     * @return price
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param price
     */
    public void setPrice(double price) {
        delete();
        this.price = price;
        save();
    }

    /**
     * Get all existing food in the database.
     * @return All food created.
     */
    public static ArrayList<Food> retrieveAll() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * Add this food object to the database.
     */
    public void save() {
        ArrayList<Food> list = (ArrayList<Food>) Store.readSerializedObject(DATA);
        list.add(this);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * Overrides the existing food in food.dat
     * @param foodArray ArrayList of Food to be written to food.dat
     */
    public static void set(ArrayList<Food> foodArray) {
        Store.writeSerializedObject(DATA, foodArray);
    }

    /**
     * Remove this food object from the database
     */
    public void delete() {
        ArrayList<Food> list = (ArrayList<Food>) Store.readSerializedObject(DATA);
        list.remove(this);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * Remove object at the specified index from the database
     * @param index
     */
    public static void delete(int index) {
        ArrayList<Food> list = (ArrayList<Food>) Store.readSerializedObject(DATA);
        list.remove(index);
        Store.writeSerializedObject(DATA, list);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Food) {
            Food f = (Food) obj;
            if (f != null && this.name.equals(f.name)) {
                return true;
            }
        }
        return false;
    }
}