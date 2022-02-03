package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Staff entity
 */
public class Staff implements Serializable {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * specifies where to store Staff data
     */
    private final static String DATA = "data/staffs.dat";
    /**
     * id to uniquely identify Staff instances
     */
    private String ID;
    /**
     * name of staff member
     */
    private String name;
    /**
     * job title of staff member
     */
    private JobTitle title;
    /**
     * username of staff member
     */
    private String username;
    /** password of staff member*/
    private String password;


    /**
     * Class constructor
     * @param ID
     * @param name
     * @param title
     * @param username
     * @param password
     */
    public Staff(String ID, String name, JobTitle title, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.title = title;
        this.username = username;
        this.password = password;
    }

    /**
     * Get staff ID
     * @return ID
     */
    public String getID() {
        return this.ID;
    }

    /**
     * Get staff's username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get staff's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get staff's job title
     * @return job title
     */
    public JobTitle getTitle() {
        return title;
    }


    /**
     * Get staff name
     * @return staff name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set staff ID
     * @param ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     *Set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set username
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set job title
     * @param title
     */
    public void setTitle(JobTitle title) {
        this.title = title;
    }

    /**
     * Set password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get all existing staffs
     * @return all existing staffs
     */
    public static ArrayList<Staff> getStaffs() {
        return (ArrayList) Store.readSerializedObject(DATA);
    }

    /**
     * Find staff by username
     * @param username
     * @return staff details
     */
    public static Staff findByUsername(String username) {
        ArrayList<Staff> list = (ArrayList) Store.readSerializedObject(DATA);
        for (Staff staff : list) {
            if (staff.getUsername().equalsIgnoreCase(username)) return staff;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Staff) {
            return (getID().equalsIgnoreCase(((Staff) o).getID()));
        }
        return false;
    }

    /**
     * Add/update staff entity
     */
    public void save() {
        ArrayList<Staff> list = (ArrayList) Store.readSerializedObject(DATA);
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
}