package rrpss.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Promotion entity
 */
public class Promotion extends MenuItem implements Serializable  {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6049881091480607663L;
    /**
     * name of promotion item
     */
    private String name;
    /**
     * course type of promotion item
     */
    private CourseType type;
    /**
     * description of promotion item
     */
    private String desc;
    /**
     * discount rate of promotion item
     */
    private double discount;
    /**
     * start date effective period of promotion
     */
    private LocalDate startDate;
    /**
     * end date of effective period of promotion
     */
    private LocalDate endDate;
    /**
     * price of promotion item after discount
     */
    private double finalPrice = 0.0;
    /**
     * price of promotion item before discount
     */
    private double OGPrice;
    /**
     * arrayList of Food items bundle in the promotion item
     */
    private ArrayList<Food> bundle;

    /**
     * Class constructor
     *
     * @param id          promotion ID
     * @param name        promotion name
     * @param type        promotion CourseType
     * @param startDate   promotion start date
     * @param endDate     promotion end date
     * @param bundle      promotion bundle containing the foods
     */
    public Promotion(String id, String name, CourseType type, String desc, double discount, LocalDate startDate, LocalDate endDate, ArrayList<Food> bundle) {
        super(id);
        double price = 0.0;
        for (Food f : bundle) {
            price += f.getPrice();
        }
        this.finalPrice = price - price * discount / 100;
        this.finalPrice = Math.round(this.finalPrice * 100.0) / 100.0;

        this.name = name;
        this.type = type;
        this.desc = desc;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bundle = bundle;
        this.OGPrice = price;
    }



    /**
     * Get promotion name
     * @return promotion name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Set name
     * @param name updated name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * Get promotion CourseType
     * @return promotion CourseType
     */
    public CourseType getCourse() {
        return this.type;
    }

    /**
     * Get final price after discount
     * @return final price after discount
     */
    @Override
    public double getPrice() {
        return this.finalPrice;
    }


    /**
     * Updates the discount percentage and final price of the promotion
     *
     * @param price updated final price
     */
    @Override
    public void setPrice(double price) {
        this.finalPrice = Math.round(price * 100.0) / 100.0;
        double temp = (1 - this.finalPrice / this.OGPrice) * 100;
        this.discount = Math.round(temp * 100.0) / 100.0;
    }

    /**
     * Get price of the promotion before the discount
     * @return price
     */
    public double getOGPrice() {
        return this.OGPrice;
    }


    /**
     * Get promotion start date
     * @return start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     *Get course type
     * @return course type
     */
    @Override
    public CourseType getType() {
        return type;
    }

    /**
     * Set start date
     * @param startDate updated start date
     */
    public boolean setStartDate(LocalDate startDate) {
        if (startDate.compareTo(this.endDate) < 0) return true;
        this.startDate = startDate;
        return false;
    }

    /**
     * Get promotion end date
     * @return promotion end date
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Set end date
     * @param endDate updated end date
     */
    public boolean setEndDate(LocalDate endDate) {
        if (this.startDate.compareTo(endDate) > 0) return true;
        this.endDate = endDate;
        return false;
    }

    /**
     * Get promotion discount in percentage
     * @return promotion discount
     */
    public double getDiscount() {
        return this.discount;
    }

    /**
     * Updates the discount and the final price of the promotion after applying the updated discount
     *
     * @param discount updated discount in percentage
     */
    public void setDiscount(double discount) {
        this.discount = discount;
        double temp = getOGPrice() - getOGPrice() * discount / 100;
        this.finalPrice = Math.round(temp * 100.0) / 100.0;
    }

    /**
     * Get promotion description
     * @return description
     */
    @Override
    public String getDesc() {
        return this.desc;
    }

    /**
     * Set description
     * @param desc updated description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Get promotion bundle of food
     * @return promotion bundle of food
     */
    public ArrayList<Food> getBundle() {
        return this.bundle;
    }

    /**
     * Set promotion bundle
     * @param bundle updated promotion bundle
     */
    public void setBundle(ArrayList<Food> bundle) {
        this.bundle = bundle;
    }





}