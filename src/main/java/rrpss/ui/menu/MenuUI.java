package rrpss.ui.menu;

import rrpss.entities.*;
import rrpss.ui.UI;
import rrpss.util.Crypto;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Boundary class for managing menu items
 *
 * @author SSP4 Group 1
 * @version 1.0
 * @since 2021-10-26
 */
public class MenuUI extends UI {
    /**
     * Control class for menu
     */
    private MenuController controller = new MenuController();

    /**
     * Form for adding an ala carte menu item to the menu
     */
    private void newAlaCarteForm() {
        Food food;
        int i = 0;
        subtitle("Add a new ala carte item to the menu");
        println("Select the food to be added to the menu:");

        for (Food f : controller.getExistingFood())
            println(String.format("[%d] %s", i++, f.getName()));
        if(controller.getExistingFood().isEmpty()){
            println("No available food in the database");
            return;
        }
        while (true) {
            int index = promptInt();
            food = controller.getFoodByIndex(index);
            if (food != null) {
                if (controller.checkDupeFood(food)) {
                    println(String.format("%s has already been added to the menu.\n", food.getName()));
                } else {
                    MenuItem menuItem = new AlaCarte(Crypto.genUUID(), food);
                    controller.addToMenu(menuItem);
                    println(String.format("Added %s to the menu.\n", food.getName()));
                }
                break;
            } else {
                println("Invalid index.");
            }
        }
    }

    /**
     * Form for adding a new promotion to the menu
     */
    private void newPromotionForm() {
        int i = 0;
        boolean flag = false;
        ArrayList<Food> bundle = new ArrayList<>();
        LocalDate startDate;
        LocalDate endDate;
        subtitle("Add a new promotion");
        println("Select the food to be added to the promotion: ");
        for (Food f : controller.getExistingFood())
            println(String.format("[%d] %s", i++, f.getName()));

        //Cant break in the first iteration
        while (true) {
            int index = promptInt();
            if (index == -1 && flag) break;
            Food food = controller.getExistingFood().get(index);
            if (controller.checkDupeFood(bundle, food)) {
                println(String.format("%s has already been added to the promotion", food.getName()));
            } else {
                bundle.add(food);
                println(String.format("%s has been added to the promotion", food.getName()));
                if (!flag) println("Enter -1 to exit.");
            }
            //Set flag=true after first iteration
            flag = true;
        }

        print("Enter promotion name: ");
        String name = promptStr();

        CourseType courseType = CourseType.PROMOTIONS;
        String desc = "Contains ";
        for (Food food : bundle) {
            desc += food.getName() + ", ";
        }

        println("Enter the % of discount from 0 to 100:");
        double discount = promptDouble(true, 0, 100);

        startDate = promptDate("Enter the start date(e.g. 15/10/2021): ");
        endDate = promptDate("Enter the end date(e.g. 15/10/2021): ");

        while (startDate.compareTo(endDate) >= 0) {
            println("Start date cannot be later than the end date.");
            startDate = promptDate("Enter the start date(e.g. 15/10/2021): ");
            endDate = promptDate("Enter the end date(e.g. 15/10/2021): ");
        }
        Promotion promotion = new Promotion(Crypto.genUUID(), name, courseType, desc, discount, startDate, endDate, bundle);
        controller.addToMenu(promotion);
        println("Promotion has been added.\n");
    }

    /**
     * Form to select outdated menu items that needs to be updated
     *
     * @param item true if editing ala carte items, or false if editing promotions
     */
    private void editMenuForm(boolean item) {

        int count = 0;
        ArrayList<MenuItem> allMenuItem = controller.getExistingMenuItem();
        ArrayList<AlaCarte> allAlaCarte;
        ArrayList<Promotion> allPromotions ;

        if (item) {
            subtitle("Update ala carte Item");
            println("Select ala carte Item");
            allAlaCarte = controller.getExistingAlaCarteItems();
            if (allAlaCarte == null || allAlaCarte.size() == 0) {
                println("No ala carte items available\n");
                return;
            }

            for (AlaCarte m : allAlaCarte) {
                println(String.format("[%d] %s", count, m.getName()));
                count++;
            }
        } else {
            subtitle("Update Promotion");
            println("Select Promotion");
            allPromotions = controller.getExistingPromotions();
            if (allPromotions == null || allPromotions.size() == 0) {
                println("No promotion available\n");
                return;
            }
            for (Promotion p : allPromotions) {
                println(String.format("[%d] %s", count, p.getName()));
                count++;
            }
        }
        int size = controller.getExistingPromotions().size();
        println("Enter index: ");
        if (item) {
            int index = promptInt(true, 0, count - 1);
            index+=size;
            editAlaCarte((AlaCarte) allMenuItem.get(index), index);
        } else {
            int index = promptInt(true, 0, count - 1);
            editPromotions((Promotion) allMenuItem.get(index), index);
        }
    }

    /**
     * Form for editing the ala carte item at the specified position
     *
     * @param alaCarte alaCarte containing outdated details
     * @param i        position
     */
    private void editAlaCarte(AlaCarte alaCarte, int i) {
        int num;
        subtitle("Edit "+alaCarte.getName());
        println("[0] Name");
        println("[1] Course Type");
        println("[2] Description");
        println("[3] Price");
        println("[4] Exit");
        num = promptInt(true, 0, 4);
        switch (num) {
            case 0:
                println("Current name: " + controller.getName(alaCarte));
                print("New name: ");
                String name = promptStr();
                controller.editName(name, i);
                println("Name has been updated.\n");
                break;
            case 1:
                println("Current Course Type: " + controller.getType(alaCarte));
                CourseType[] courseTypes = CourseType.values();
                for (CourseType c : courseTypes)
                    if(c==CourseType.PROMOTIONS) continue;
                    else print(String.format("[%d] %s", c.ordinal(), c));
                print("Select new Course Type: ");
                CourseType courseType = courseTypes[promptInt(0, courseTypes.length - 2)+1];
                controller.editCourse(courseType, i);
                println("Course Type has been updated.\n");
                break;
            case 2:
                println("Current description: " + controller.getDesc(alaCarte));
                print("New description: ");
                String desc = promptStr();
                controller.editDesc(desc, i);
                println("Description has been updated.\n");
                break;
            case 3:
                println("Current price: $" + controller.getPrice(alaCarte));
                print("New price: $");
                double price = promptDouble();
                controller.editPrice(price, i);
                println("Price has been updated.\n");
                break;
            default:
                println("Exiting edit menu item.");
                break;
        }
    }

    /**
     * Form for editing the promotion
     *
     * @param promotion promotion containing the outdated details
     * @param i         position
     */
    private void editPromotions(Promotion promotion, int i) {
        int num;
        subtitle("Edit "+promotion.getName());
        println("[0] Name");
        println("[1] Price");
        println("[2] Discount");
        println("[3] Start Date");
        println("[4] End Date");
        println("[5] Edit Promotional Food");
        println("[6] Exit");
        num = promptInt(true, 0, 7);
        switch (num) {
            case 0:
                println("Current name: " + controller.getName(promotion));
                print("New name: ");
                String name = promptStr();
                controller.editName(name, i);
                println("Name has been updated.\n");
                break;

            case 1:
                println("Current price: $" + controller.getPrice(promotion) );
                print("New price: $");
                double price = promptDouble(0, 100);
                controller.editPrice(price, i);
                println("Discount has been updated.\n");
                break;

            case 2:
                println("Current Discount: " + controller.getDiscount(promotion) + "%");
                print("New discount: ");
                double discount = promptDouble(0, 100);
                controller.editPromoDiscount(discount, i);
                println("Price has been updated.\n");
                break;

            case 3:
                println("Current start date: " + controller.getStartDate(promotion));
                LocalDate startDate = promptDate("Enter new start date(e.g. 15/10/2021): ");
                boolean result = controller.editPromoStartDate(startDate, i);
                if (result) println("Start date cannot be later than end date.\n");
                else println("Start date has been updated.\n");
                break;

            case 4:
                println("Current end date: " + controller.getEndDate(promotion));
                LocalDate endDate = promptDate("Enter new end date(e.g. 15/10/2021): ");
                boolean res = controller.editPromoEndDate(endDate, i);
                if (res) println("End date cannot be earlier than start date.\n");
                else println("End date has been updated.\n");
                break;

            case 5:
                editBundle(i);
                break;

            default:
                println("Exiting edit promotion.\n");
                break;
        }
    }

    /**
     * Prints all the name of every food in the promotion bundle
     *
     * @param p Promotion object
     * @return total number of food in the bundle
     */
    private int printExistingBundleFood(Promotion p) {
        int count = 0;
        for (Food f : p.getBundle()) {
            println(String.format("[%d] %s", count++, f.getName()));
        }
        print("\n");
        return count;
    }

    /**
     * Form for editing the promotion bundle
     *
     * @param position position
     */
    private void editBundle(int position) {
        int count=0;
        Promotion p =(Promotion) controller.getExistingMenuItem().get(position);
        subtitle("Edit promotional food");
        println("[0] Add food to the promotion");
        println("[1] Remove food from the promotion");

        int num = promptInt(true);
        switch (num) {
            case 0:
                int i = 0;
                printExistingBundleFood(p);
                println("Select food to be added to the promotion: ");
                for (Food f : controller.getExistingFood()){
                    println(String.format("[%d] %s", i++, f.getName()));
                    count++;
                }
                println("Enter -1 to exit.");
                while (true) {
                    int index = promptInt(true,-1,count);
                    if (index == -1) break;
                    Food food = controller.getExistingFood().get(index);
                    if (controller.checkDupeFood(p.getBundle(), food)) {
                        println(String.format("%s has already been added to the promotion", food.getName()));
                    } else {
                        controller.addPromoFood(food, position);
                        println(String.format("%s has been added to the promotion", food.getName()));
                    }
                }
                break;
            case 1:
                count = printExistingBundleFood(p);
                println("Select food to be removed from the promotion bundle");

                if (p.getBundle().size() == 0) {
                    println("No food to be removed from the promotion bundle\n");
                    return;
                }
                int index = promptInt(true,0,count);
                boolean result = controller.deletePromoItem(position, index);
                if (result) {
                    println("Item has been removed from the promotion.\n");
                } else {
                    println("Invalid index.\n");
                }
                break;
            default:
                println("Exiting edit promotional food.\n");
                break;
        }
    }
//
//

    /**
     * Removes a menu item from the menu
     *
     * @param item true if editing ala carte items, or false if editing promotions
     */
    private void deleteItem(boolean item) {
        int count = 0;
        subtitle("Delete item");

        if (item) {
            if (controller.getExistingAlaCarteItems() == null || controller.getExistingAlaCarteItems().size() == 0) {
                println("There are no menu item available\n");
                return;
            }
            for (AlaCarte m : controller.getExistingAlaCarteItems()) {
                println(String.format("[%d] %s", count, controller.getName(m)));
                count++;
            }
        } else {
            if (controller.getExistingPromotions() == null || controller.getExistingPromotions().size() == 0) {
                println("There are no promotions available\n");
                return;
            }
            for (Promotion p : controller.getExistingPromotions()) {
                println(String.format("[%d] %s", count, controller.getName(p)));
                count++;
            }
        }
        println("Enter index: ");

        int index = promptInt(0, count);
        if (item) {
            index+=controller.getExistingPromotions().size();
        } controller.deleteMenuItem(index);
        println("Item has been deleted \n");
    }


    /**
     * Prints menu items
     * <br />
     * Prints the name and index of all Menu objects
     */
    public void viewMenuItems() {
        title("RRPSS menu");
        ArrayList<MenuItem> menuItems = controller.getExistingMenuItem();

        if(menuItems==null || menuItems.isEmpty()) {
            subtitle("No menu items available");
            println();
            return;
        }

        int indexOfAlaCarte = controller.indexOfAlaCarte(menuItems);
        int indexOfPromotions = controller.indexOfPromotions(menuItems);
        boolean printPromoTitle = true;
        boolean printAlaCarteTitle = true;

        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);

            if (item instanceof Promotion) {
                // Print title
                if (indexOfPromotions != -1 && printPromoTitle) {
                    printPromoTitle = false;
                    title("Promotions");
                }

                println(String.format("[%d] [%s]", i, controller.getType(item)));
            } else if (item instanceof AlaCarte) {
                // Print title
                if (indexOfAlaCarte != -1 && printAlaCarteTitle) {
                    printAlaCarteTitle = false;
                    title("Ala carte");
                }
                println(String.format("[%d] [%s]", i, controller.getType(item)));
            }
            printDetails(item);
        }
        return;
    }

    /**
     * Print menu item details
     * @param menuItem menu item to be printed
     */
    private void printDetails(MenuItem menuItem) {
        printf("%-20s %s\n", "Name:", menuItem.getName());
        printf("%-20s %s\n", "Course Type:", controller.getType(menuItem));
        printf("%-20s %s\n", "Description:", controller.getDesc(menuItem));
        println(String.format("%-20s $%.2f", "Price:", controller.getPrice(menuItem)));

        if (menuItem instanceof Promotion) {
            printf("%-20s $%.2f\n", "Original Price:", controller.getOGPrice((Promotion) menuItem));
            printf("%-20s %.2f%%\n", "Discount:", controller.getDiscount((Promotion) menuItem));
            printf("%-20s %s\n", "Start Date:", controller.getStartDate((Promotion) menuItem));
            printf("%-20s %s\n", "End Date:", controller.getEndDate((Promotion) menuItem));
        }
        println();
    }


    /**
     * Form containing the other user interfaces of Task 1 and 2
     */
    private void mainForm() {
        int num;
        while (true) {
            println("[0] New ala carte item");
            println("[1] Update ala carte item ");
            println("[2] Delete ala carte item");
            println("[3] New promotion");
            println("[4] Update promotion");
            println("[5] Delete promotion ");
            println("[6] View Menu");
            println("[7] Exit");
            num = promptInt(true, 0, 7);
            if (num == 7) break;
            switch (num) {
                case 0:
                    newAlaCarteForm();
                    break;
                case 1:
                    editMenuForm(true);
                    break;
                case 2:
                    deleteItem(true);
                    break;
                case 3:
                    newPromotionForm();
                    break;
                case 4:
                    editMenuForm(false);
                    break;
                case 5:
                    deleteItem(false);
                    break;
                case 6:
                    viewMenuItems();
                    break;
                default:
                    println("Invalid Input, please try again.");
                    break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        mainForm();
    }
}

