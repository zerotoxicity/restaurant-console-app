package rrpss.ui.app;

import rrpss.ui.Router;

/**
 * Custom exception
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class Navigable extends Exception {
    /**
     * Path to be navigated when thrown
     */
    public Router.RoutePath path;

    /**
     * Constructor for Navigable exception
     * @param path
     */
    Navigable(Router.RoutePath path) {
        super();
        this.path = path;
    }
}
