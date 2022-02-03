package rrpss.ui.menu;

import rrpss.entities.*;
import rrpss.util.Crypto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Control class for managing menu items
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class MenuController {
    /**
     * ArrayList containing all the Food objects in the database
     */
    private ArrayList<Food> existingFood;
    /**
     * Menu object
     */
    private Menu menu;
    /**
     * ArrayList containing all the menu item in the menu
     */
    private ArrayList<MenuItem> existingMenuItem;

    /**
     * Class constructor
     */
    public MenuController() {

    }

    //GETTERS------------------------------------------------------------------------

    /**
     * Get all food in the database
     * @return an ArrayList containing all Food objects from food.dat
     */
    public ArrayList<Food> getExistingFood() {
        this.existingFood = Food.retrieveAll();
        return existingFood;
    }

    /**
     Get all menu items
     * @return
     */
    public ArrayList<MenuItem> getExistingMenuItem() {
        try {
            menu = getWholeMenu();
            this.existingMenuItem = menu.getMenuItems();
            return this.existingMenuItem;
        } catch (NullPointerException e) {
            return null;
        }
    }

    /**
     * @return an ArrayList containing all Menu objects from menu.dat
     */
    public Menu getWholeMenu() {
        try {
            menu = Menu.retrieveAll().get(0);
            return menu;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Utility method returning the index of first instance of Promotions
     *
     * @param menuItems
     * @return -1 if instance not found.
     */
    public int indexOfPromotions(ArrayList<MenuItem> menuItems) {
        for (int i = 0; i < menuItems.size(); i++)
            if (menuItems.get(i) instanceof Promotion)
                return i;
        return -1;
    }

    /**
     * Utility method returning the index of first instance of AlaCarte
     *
     * @param menuItems
     * @return -1 if instance not found.
     */
    public int indexOfAlaCarte(ArrayList<MenuItem> menuItems) {
        for (int i = 0; i < menuItems.size(); i++)
            if (menuItems.get(i) instanceof AlaCarte)
                return i;
        return -1;
    }


    /**
     * Get all menu item objects that are instance of AlaCarte
     * @return all ala carte items
     */
    public ArrayList<AlaCarte> getExistingAlaCarteItems() {
        ArrayList<AlaCarte> alaCartes = new ArrayList<>();
        try {
            this.existingMenuItem = getExistingMenuItem();
            for (MenuItem mi : existingMenuItem) {
                if (mi instanceof AlaCarte) {
                    alaCartes.add((AlaCarte) mi);
                }
            }

        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return alaCartes;
    }

    /**
     Get all menu item objects that are instance of Promotion
     * @return all promotions
     */
    public ArrayList<Promotion> getExistingPromotions() {
        ArrayList<Promotion> promotions = new ArrayList<>();
        try {
            this.existingMenuItem = getExistingMenuItem();
            for (MenuItem mi : existingMenuItem) {
                if (mi instanceof Promotion) {
                    promotions.add((Promotion) mi);
                }
            }

        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return promotions;
    }



    /**
     * Get name of the menu item
     *
     * @param menuItem
     * @return name of the menu item
     */
    public String getName(MenuItem menuItem) {
        return menuItem.getName();
    }

    /**
     * Get course type
     *
     * @param menuItem
     * @return CourseType of the menu item
     */
    public CourseType getType(MenuItem menuItem) {
        return menuItem.getType();
    }

    /**
     * Get description of the menu item
     *
     * @param menuItem
     * @return description of the menu item
     */
    public String getDesc(MenuItem menuItem) {
        return menuItem.getDesc();
    }


    /**
     * Get price of menu item
     *
     * @param menuItem
     * @return price of menu item
     */
    public double getPrice(MenuItem menuItem) {
        return menuItem.getPrice();
    }


    /**
     * Get original price of the promotion
     *
     * @param promotion
     * @return original price of the promotion
     */
    public double getOGPrice(Promotion promotion) {
        return promotion.getOGPrice();
    }

    /**
     * Get discount percentage of the promotion
     *
     * @param promotion
     * @return discount percentage of the promotion
     */
    public double getDiscount(Promotion promotion) {
        return promotion.getDiscount();
    }

    /**
     * Get start date of the promotion
     *
     * @param promotion
     * @return start date of the promotion
     */
    public LocalDate getStartDate(Promotion promotion) {
        return promotion.getStartDate();
    }

    /**
     * Get end date of the promotion
     *
     * @param promotion
     * @return end date of the promotion
     */
    public LocalDate getEndDate(Promotion promotion) {
        return promotion.getEndDate();
    }


  //SETTERS for Menu Item---------------------------------------------------------------

    /**
     * Updates the name of the menu item at the specified position<br>
     * if the selected menu item is an ala carte item, update all Promotions containing the outdated ala carte item
     *
     * @param name updated name
     * @param i    position
     */
    public void editName(String name, int i) {
        AlaCarte alaCarte = null;
        existingMenuItem = getExistingMenuItem();
        MenuItem menuItem = existingMenuItem.get(i);
        if (menuItem instanceof AlaCarte) {
            alaCarte = (AlaCarte) menuItem;
            Food oldF = copyFood(alaCarte.getFood());
            existingMenuItem.get(i).setName(name);
            sort();
            menu.setMenuItems(existingMenuItem);
            menu.save();
            Food newF = alaCarte.getFood();
            editMenuItemUpdatePromotion(oldF, newF);
        } else {
            existingMenuItem.get(i).setName(name);
            sort();
            menu.setMenuItems(existingMenuItem);
            menu.save();
        }

    }

    /**
     * Updates the CourseType of the AlaCarte at the specified position
     *
     * @param type new AlaCarte CourseType
     * @param i    position
     */
    public void editCourse(CourseType type, int i) {
        existingMenuItem = getExistingMenuItem();
        MenuItem menuItem = existingMenuItem.get(i);
        AlaCarte alaCarte = (AlaCarte) menuItem;
        alaCarte.setCourse(type);
        sort();
        menu.setMenuItems(existingMenuItem);

        menu.save();
    }

    /**
     * Updates the description of the AlaCarte at the specified position
     * @param desc new description
     * @param i    position
     */
    public void editDesc(String desc, int i) {
        existingMenuItem = getExistingMenuItem();
        MenuItem menuItem = existingMenuItem.get(i);
        AlaCarte alaCarte = (AlaCarte) menuItem;
        alaCarte.setDesc(desc);
        sort();
        menu.setMenuItems(existingMenuItem);

        menu.save();
    }


    /**
     Updates the name of the menu item at the specified position<br>
     * if the selected menu item is an ala carte item, update all Promotions containing the outdated ala carte item     *
     * @param price new price
     * @param i     position
     */
    public void editPrice(double price, int i) {
        AlaCarte alaCarte = null;
        MenuItem menuItem = existingMenuItem.get(i);
        if (menuItem instanceof AlaCarte) {
            alaCarte = (AlaCarte) menuItem;
            Food oldF = copyFood(alaCarte.getFood());
            menuItem.setPrice(price);
            sort();
            menu.setMenuItems(existingMenuItem);
            menu.save();
            Food newF = alaCarte.getFood();
            editMenuItemUpdatePromotion(oldF, newF);
        } else {
            menuItem.setPrice(price);
            sort();
            menu.setMenuItems(existingMenuItem);
            menu.save();

        }
    }

    //SETTERS for Promotions---------------------------------------------------------------

    /**
     * Updates the discount of the Promotion at the specified position
     *
     * @param discount new Promotion discount
     * @param i        position
     */
    public void editPromoDiscount(double discount, int i) {
        existingMenuItem = getExistingMenuItem();
        Promotion p = (Promotion) existingMenuItem.get(i);
        p.setDiscount(discount);
        menu.save();
    }


    /**
     * Updates the start date of the Promotion at the specified position
     *
     * @param date new Promotion start date
     * @param i    position
     * @return returns true if the update fail and false if the update succeeded
     */
    public boolean editPromoStartDate(LocalDate date, int i) {
        existingMenuItem = getExistingMenuItem();
        Promotion p = (Promotion) existingMenuItem.get(i);
        boolean result = p.setStartDate(date);
        menu.save();
        return result;
    }

    /**
     * Updates the end date of the Promotion at the specified position
     *
     * @param date new Promotion end date
     * @param i    position
     * @return returns true if the update fail and false if the update succeeded
     */
    public boolean editPromoEndDate(LocalDate date, int i) {
        existingMenuItem = getExistingMenuItem();
        Promotion p = (Promotion) existingMenuItem.get(i);
        boolean result = p.setEndDate(date);
        menu.save();
        return result;
    }

    //ADD (TO MENU.DAT) OPERATIONS---------------------------------------------------------------

    /**
     * Get Food using index
     *
     * @param i index
     * @return Food at the specified position
     */
    public Food getFoodByIndex(int i) {
        try {
            return existingFood.get(i);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Adds menu item to the menu and updates menu.dat
     *
     * @param menuItem
     */
    public void addToMenu(MenuItem menuItem) {
        menu = getWholeMenu();
        if(menu==null){
            existingMenuItem=new ArrayList<>();
            existingMenuItem.add(menuItem);
            menu = new Menu(Crypto.genUUID(),existingMenuItem);
            menu.add();
        }else{
            existingMenuItem=menu.getMenuItems();
            existingMenuItem.add(menuItem);
            Collections.sort(existingMenuItem);
            menu.setMenuItems(existingMenuItem);
            menu.save();
        }
    }

    /**
     * Check if the bundle contains the food
     *
     * @param bundle ArrayList of Food
     * @param food   Food to be added to the bundle
     * @return returns true if the bundle already contains the food, else returns false
     */
    public boolean checkDupeFood(ArrayList<Food> bundle, Food food) {
        for (Food f : bundle) {
            if (f.getName().equalsIgnoreCase(food.getName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Check if the food is in any AlaCarte item
     *
     * @param food Food to be added to the bundle
     * @return returns true if the Food is in any Menu item, else returns false
     */
    public boolean checkDupeFood(Food food) {
        ArrayList<MenuItem> temp = getExistingMenuItem();
        if (temp != null) {
            for (MenuItem m : temp) {
                if (m instanceof AlaCarte && m.getName().equalsIgnoreCase(food.getName())) return true;
            }
        }
        return false;
    }


    /**
     * Includes more food into the promotion bundle
     *
     * @param food Food to be added into the bundle
     * @param i    position
     */
    public void addPromoFood(Food food, int i) {
        Promotion p =(Promotion) getExistingMenuItem().get(i);
        p.getBundle().add(food);
        String newDesc = "";
        for(Food f : p.getBundle()){
            newDesc+=f.getName()+", ";
        }
        p.setDesc(newDesc);
        menu.save();
    }

//DELETE OPERATIONS---------------------------------------------------------------

    /**
     * Remove menu item at the specified position from the menu
     *
     * @param i position
     */
    public void deleteMenuItem(int i) {
        this.existingMenuItem.remove(i);
        menu = getWholeMenu();
        sort();
        menu.setMenuItems(existingMenuItem);
        menu.save();
    }

    //Delete promo from Filtered Promotion list

    /**
     * Removes the food at the specified position from the bundle
     * @param position index of the promotion
     * @param i index of the unwanted Food
     * @return returns true if Food has been removed successfully, else returns false
     */
    public boolean deletePromoItem(int position, int i) {
        Promotion p = (Promotion) existingMenuItem.get(position);
        try {
            p.getBundle().remove(i);
            menu.save();
            String newDesc="Contains ";
            for(Food f : p.getBundle()){
                newDesc+=f.getName()+", ";
            }
            p.setDesc(newDesc);
            return true;
        } catch (IndexOutOfBoundsException e) {
            menu.save();
            return false;
        }
    }

//MISC------------------------------------------------------------------------------------------------------------------------------


    /**
     * Checks if any promotion bundle contains the outdated Food
     * If yes, updates the promotion bundle with details of the new Food
     *
     * @param food    outdated Food
     * @param newFood updated Food
     */
    private void editMenuItemUpdatePromotion(Food food, Food newFood) {
        ArrayList<Food> bundle;
        ArrayList<Promotion> promotions = getExistingPromotions();
        int i = 0;

        if (promotions.isEmpty()) return;

        for (Promotion p : promotions) {
            bundle = p.getBundle();
            //If bundle contains the old Food item, update it to the new Food item
            if (bundle.contains(food)) {

                //Update bundle
                bundle.remove(food);
                bundle.add(newFood);
                Promotion promo = (Promotion) existingMenuItem.get(i);
                promo.setBundle(bundle);

                //Update desc
                String desc = "";
                double price = 0.0;
                for (Food f1 : bundle) {
                    desc += f1.getName() + ", ";
                    price += f1.getPrice();
                }
                existingMenuItem.get(i).setDesc(desc);
                //Update original and final price of the bundle
                existingMenuItem.get(i).setPrice(price);
            }
            i++;
        }
        sort();
        menu.setMenuItems(existingMenuItem);
        menu.save();
    }

    //"Deep copy" Food item

    /**
     * Create a new Food item containing the details of the outdated Food
     * <br>
     * Prevents mutation when the outdated Food is updated
     *
     * @param food outdated Food
     * @return a copy of the outdated Food item
     */
    private Food copyFood(Food food) {
        return new Food(food.getFoodID(), food.getName(), food.getCourse(), food.getDesc(), food.getPrice());
    }

    /**
     * Sort menu based on CourseType
     */
    private void sort() {
        Collections.sort(existingMenuItem);
    }
}
