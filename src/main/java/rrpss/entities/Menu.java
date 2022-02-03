package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 Represents Menu which stores a collection of menu items
 @author  SSP4 Group 1
 @version 1.0
 @since   2021-10-26
 */
public class Menu implements Serializable {

	/**
	 * Specifies where Menu instances should be stored.
	 */
	private final static String DATA = "data/menu.dat";

	/**
	 * unique identifier for JVM to identify class.
	 */
	private static final long serialVersionUID = 6049881091480607663L;

	/**
	 * stores both alacarte items and promotions
	 */
	private ArrayList<MenuItem> menuItems;

	/**
	 * ID to uniquely identify Menu.
	 */
	private String ID;

	/**
	 * Class constructor that initializes alaCarte
	 * @param ID ID of the Menu object
	 */
	public Menu(String ID, ArrayList<MenuItem> menuItems) {
		this.menuItems = new ArrayList(menuItems);
		this.ID=ID;
	}
	/**
	 * Get all existing menu items
	 * @return all existing menu items
	 */
	public ArrayList<MenuItem> getMenuItems(){return this.menuItems;}



	/**
	 * Set menuItems items
	 * @param menuItems
	 */
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}


	/**
	 *	Retrieves all Menu objects from menu.dat
	 * @return ArrayList containing all Menu object
	 */
	public static ArrayList<Menu> retrieveAll() {
		return (ArrayList<Menu>) Store.readSerializedObject(DATA);
	}
	/**
	 * Add this menu object to the ArrayList containing alls Menu object, and update menu.dat
	 *
	 */
	public void add(){
		ArrayList<Menu> list = (ArrayList<Menu>) Store.readSerializedObject(DATA);
		list.add(this);
		Store.writeSerializedObject(DATA,list);
	}
	/**
	 * Add this menu object to the ArrayList containing alls Menu object, and update menu.dat
	 *
	 */
	public void save(){
		ArrayList<Menu> list = (ArrayList<Menu>) Store.readSerializedObject(DATA);
		list.set(0,this);

		Store.writeSerializedObject(DATA,list);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Menu){
			Menu m = (Menu) obj;
			if(m != null && this.ID.equals(m.ID)){
				return true;
			}
		}
		return false;
	}

}