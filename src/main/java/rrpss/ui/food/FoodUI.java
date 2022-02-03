package rrpss.ui.food;

import rrpss.entities.CourseType;
import rrpss.entities.Food;
import rrpss.ui.UI;
import rrpss.util.Crypto;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Boundary class for Food management
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class FoodUI extends UI {
    /**
     * Control class for food
     */
    private FoodController controller = new FoodController();

    /**
     * Add new food object
     */
    private void addForm(){
        subtitle("Add a new food");
        String name;
        print("Enter food name: ");
        while(true){
            name = promptStr();
            if(controller.nameExists(name)){
                println("Name already exists");
                return;
            }
            else break;
        }
        CourseType[] courseTypes = CourseType.values();
        int count =0;
        for (CourseType c : courseTypes){
            if(count==0) {
                count++;
                continue;
            }
            print(String.format("[%d] %s ", c.ordinal()-1, c.toString()));


        }
        println("\nSelect Course Type: ");
        CourseType courseType = courseTypes[promptInt(true,0, courseTypes.length - 2)+1];
        print("Enter description: ");
        String desc = promptStr();
        print("Enter price: $");
        double price = promptDouble(false);
        controller.addFood(new Food(Crypto.genUUID(),name,courseType,desc,price));
        println(name+" has been added\n");
    }

    /**
     * Remove food object from the database
     */
    private void deleteFood(){
        int i=0;
        subtitle("Delete food");
        for(Food f: controller.getExistingFood()){
            println(String.format("[%d] %s ", i++, controller.getName(f),controller.getPrice(f)));
        }
        println("Enter index: ");
        int num = promptInt(true, 0,i-1);
        ArrayList<String> names = controller.deleteItem(num);
        if(names.isEmpty()){
            println("Item has been deleted.\n");
        }else{
            println(String.format("The following items contains %s:",controller.getFoodByIndex(num).getName()));
            i=0;
            for(String n: names){
                println(String.format("[%d] %s",i++,n));
            }
            println("Please remove them from the menu before proceeding with removing "+controller.getFoodByIndex(num).getName());
            println("");
        }

    }

    /**
     * View all available food
     */
    private void viewFood(){
        subtitle("View all food");
        int i=0;
        for(Food f : controller.getExistingFood()){
            if(i!=0 && controller.getCourse(f)!=controller.getExistingFood().get(i-1).getCourse()) {
                subtitle(String.format("%s", controller.getCourse(f)));
            }
            else if(i==0) subtitle("STARTERS");
            println(String.format("[%d] %s - $%.2f", i++, controller.getName(f),controller.getPrice(f)));
        }
            int num = promptInt(true,0,i-1);
            Food f = controller.getFoodByIndex(num);
            println("Name: "+controller.getName(f));
            println("Course Type: "+controller.getCourse(f));
            println("Description: "+controller.getDesc(f));
            println("Price: "+controller.getPrice(f));
            println("");
    }

    /**
     * Generate a list of predefined food into food.dat
     */
    private void createBulkFood(){
        ArrayList<Food> food = new ArrayList<>(
                Arrays.asList(
                        new Food(Crypto.genUUID(),"Fried Mozarella",CourseType.STARTERS,"Deep-fired mozarella sticks,served with marinara",7.29),
                        new Food(Crypto.genUUID(),"Calamari",CourseType.STARTERS,"Deep-fried calamari, served with marinara",10.79),
                        new Food(Crypto.genUUID(),"Salmon Mango Salsa",CourseType.MAIN_COURSE,"Mango salsa served with salmon",13.80),
                        new Food(Crypto.genUUID(),"Ribeye Steak",CourseType.MAIN_COURSE,"200g Ribeye Steak",16.30),
                        new Food(Crypto.genUUID(),"Chicken Chop",CourseType.MAIN_COURSE,"Chicken chop",10.60),
                        new Food(Crypto.genUUID(),"Steak & Prawn",CourseType.MAIN_COURSE,"Steak served with prawn, pasta, and gravy",10.60),
                        new Food(Crypto.genUUID(),"Tiramisu",CourseType.DESERTS,"A slice of tiramisu cake",5.60),
                        new Food(Crypto.genUUID(),"Chocolate Cake",CourseType.DESERTS,"A slice of chocolate cake",5.60),
                        new Food(Crypto.genUUID(),"Coke",CourseType.DRINKS,"Coke",3.25),
                        new Food(Crypto.genUUID(),"Coke Light",CourseType.DRINKS,"Coke Light",3.25),
                        new Food(Crypto.genUUID(),"Snapple",CourseType.DRINKS,"Snapple",3.25)

                )
        );
        controller.createBulkFood(food);
    }

    /**
     * Form containing the options for food
     */
    private void form(){
        while(true){
        println("[0] New food");
        println("[1] Delete food");
        println("[2] View all food");
       // println("[4] Generate a predefined list of food");
        println("[3] Exit");
        int num = promptInt(true,0,4);
        if(num==3) break;
            switch (num) {
                case 0: addForm(); break;
                case 1: deleteFood(); break;
                case 2: viewFood();break;
                case 4: createBulkFood(); break;
                default: break;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Food management");
        form();
       }

    }

