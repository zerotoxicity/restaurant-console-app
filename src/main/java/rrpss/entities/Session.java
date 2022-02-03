package rrpss.entities;

import rrpss.store.Store;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Session for current staff logged in
 */
public class Session implements Serializable {
	/**
	 * unique identifier for JVM to identify class.
	 */
    private static final long serialVersionUID = 6529685098267757690L;
    /**
     * specifies where to store Session data
     */
    private final static String DATA = "data/session.dat";
    /**
     * specifies user of current session
     */
    private Staff currentUser;
    /**
     * time of login
     */
    private LocalDateTime login;

    /**
     * Class constructor
     * @param login datetime of the login
     * @param currentUser current staff logged in
     */
    public Session(LocalDateTime login, Staff currentUser) {
        this.login = login;
        this.currentUser = currentUser;
    }

    /**
     *Get staff who is currently logged in
     * @return logged in staff
     */
    public Staff getCurrentUser() {
        return currentUser;
    }

    /**
     * Get datetime of login
     * @return datetime
     */
    public LocalDateTime getLogin() {
        return login;
    }

    /**
     * Saves and overrides the session saved in store
     */
    public void save() {
        ArrayList<Session> list = new ArrayList();
        list.add(this);
        Store.writeSerializedObject(DATA, list);
    }

    public static Session load()  {
        ArrayList<Session> list = (ArrayList) Store.readSerializedObject(DATA);
        if (list.isEmpty()) return null;
        return list.get(0);
    }

}
