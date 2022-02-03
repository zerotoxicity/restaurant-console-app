package rrpss.ui.food;

import rrpss.entities.*;
import rrpss.ui.menu.MenuController;

import java.util.ArrayList;

/**
 * Control class for food management
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class FoodController {
    private ArrayList<Food> existingFood;
    private MenuController menuController = new MenuController();

    /**
     * Class constructor
     */
    FoodController() {
        sortFood();
    }

    /**
     * Check if database already contains a food object with the same name
     *
     * @param name name of food object to be added to the database
     * @return true if duplicate name, else false
     */
    public boolean nameExists(String name) {
        for (Food f : existingFood) {
            if (f.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }

    /**
     * @return all food object
     */
    public ArrayList<Food> getExistingFood() {
        existingFood = Food.retrieveAll();
        return existingFood;
    }

    /**
     * @param num position
     * @return food object at the specified position
     */
    public Food getFoodByIndex(int num) {
        return existingFood.get(num);
    }

    public CourseType getCourse(Food f) {
        return f.getCourse();
    }

    public String getName(Food f) {
        return f.getName();
    }

    public String getDesc(Food f) {
        return f.getDesc();
    }

    public double getPrice(Food f) {
        return f.getPrice();
    }

    /**
     * @param food food to be added to the database
     */
    public void addFood(Food food) {
        food.save();
        sortFood();
    }

    /**
     * Food objects according to their CourseType
     */
    public void sortFood() {
        this.existingFood = Food.retrieveAll();
        ArrayList<Food> starters = new ArrayList<>();
        ArrayList<Food> main = new ArrayList<>();
        ArrayList<Food> drinks = new ArrayList<>();
        ArrayList<Food> deserts = new ArrayList<>();
        for (Food f : this.existingFood) {
            CourseType c = f.getCourse();
            switch (c) {
                case DRINKS:
                    drinks.add(f);
                    break;
                case DESERTS:
                    deserts.add(f);
                    break;
                case STARTERS:
                    starters.add(f);
                    break;
                default:
                    main.add(f);
                    break;
            }
        }

        starters.addAll(main);
        starters.addAll(deserts);
        starters.addAll(drinks);
        this.existingFood = starters;
        Food.set(existingFood);
    }

    /**
     * Delete item at the specified position
     * <br>
     * Checks if any menu item contains the selected food
     *
     * @param index position
     * @return Names of promotion/menu item containing the selected food
     */
    public ArrayList<String> deleteItem(int index) {
        Food f = getExistingFood().get(index);
        ArrayList<Promotion> promotions = menuController.getExistingPromotions();
        ArrayList<AlaCarte> alaCartes = menuController.getExistingAlaCarteItems();
        /**
         * Names of promotion/menu item containing the food at the specified position
         */
        ArrayList<String> name = new ArrayList<>();
        if (promotions != null || !promotions.isEmpty()) {
            for (Promotion p : promotions) {
                //If a promotion contains the selected Food, add it an array list
                if (p.getBundle().contains(f)) {
                    name.add(p.getName());
                }
            }
        }
        if (alaCartes != null || !alaCartes.isEmpty()) {
            for (AlaCarte m : alaCartes) {
                if (m.getName().equalsIgnoreCase(f.getName())) {
                    name.add(m.getName());
                    break;
                }
            }
        }
        if (name.size() == 0) {
            Food.delete(index);
            return new ArrayList();
        }
        return name;
    }

    /**
     * Populate database with pre-defined food objects
     *
     * @param foodList list of pre-defined food objects
     */
    public void createBulkFood(ArrayList<Food> foodList) {
        Food.set(foodList);
    }
}
