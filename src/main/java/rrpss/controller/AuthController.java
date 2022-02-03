package rrpss.controller;

import rrpss.entities.Session;
import rrpss.entities.Staff;
import java.time.LocalDateTime;

/**
 * Control class for authentication
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public final class AuthController {
    /**
     * Singleton instance of auth controller
     */
    private static AuthController INSTANCE;

    /**
     * Session object of current authenticated user
     */
    private Session session;

    /**
     * Class constructor
     */
    private AuthController() {
        this.session = Session.load();
    }

    /**
     * Get authentication controller instance
     * @return instance
     */
    public static AuthController getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AuthController();
        return INSTANCE;
    }

    /**
     * Check if authenticated
     * @return true if authenticated
     */
    public boolean isAuthenticated() {
        return session != null;
    }

    /**
     * Get the logged in staff
     * @return Staff
     */
    public Staff getStaff() {
        return session.getCurrentUser();
    }

    /**
     * Authenticate user
     * @param currentUser
     */
    public void authenticate(Staff currentUser) {
        // Save session
        this.session = new Session(LocalDateTime.now(), currentUser);
        this.session.save();
    }


}