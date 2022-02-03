package rrpss.entities;

import java.io.Serializable;

/**
 Represents an AlaCarte
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class AlaCarte extends MenuItem implements Serializable  {
    /**
     * unique identifier for JVM to identify class.
     */
    private static final long serialVersionUID = 6498810914804124663L;



    /**
     * stores an instance of food.
     */
    private Food food;

    /**
     * Class constructor
     * @param ID alaCarte ID.
     * @param food alaCarte food.
     */
    public AlaCarte(String ID,Food food) {
        super(ID);
        this.food = food;
    }

    /**
     * Get entity's ID.
     * @return alaCarte.
     */


    /**
     * Get entity's food.
     * @return alaCarte food.
     */
    public Food getFood() {
        return this.food;
    }

    /**
     * Get entity's name.
     * @return alaCarte name.
     */
    @Override
    public String getName() {
        return this.food.getName();
    }

    /**
     * Set name.
     * @param name updated alaCarte name.
     */
    @Override
    public void setName(String name) {
        this.food.setName(name);
    }

    /**
     * Get alaCarte description
     * @return description
     */
    @Override
    public String getDesc() {
        return this.food.getDesc();
    }



    /**
     * Set description.
     * @param desc updated description.
     */
    @Override
    public void setDesc(String desc) {
        this.food.setDesc(desc);
    }


    /**
     * Set CourseType.
     * @param type updated CourseType.
     */
    public void setCourse(CourseType type) {
        this.food.setCourse(type);
    }

    /**
     * Get price of the ala carte item.
     * @return alaCarte price.
     */
    @Override
    public double getPrice() {
        return this.food.getPrice();
    }

    /**
     * Get ala carte item course type.
     * @return course type.
     */
    @Override
    public CourseType getType() {
        return food.getCourse();
    }

    /**
     * Set price.
     * @param price updated price.
     */
    @Override
    public void setPrice(double price) {
        this.food.setPrice(price);
    }
}