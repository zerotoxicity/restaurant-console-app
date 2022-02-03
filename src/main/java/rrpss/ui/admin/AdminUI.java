package rrpss.ui.admin;

import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.app.Back;

/**
 * Boundary class for membership
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class AdminUI extends UI {

    RouteMap[] routes = {
            new RouteMap("Bulk create Membership", new CreateMembershipUI()),
            new RouteMap("Go Back", new Back()),
    };

    /**
     * Show available functions
     * @return selected function index
     */
    public int showOptions() {
        for (int i = 0; i < routes.length; i++)
            println(String.format("[%d] %s", i, routes[i].getTitle()));
        return promptInt(true, 0, routes.length - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        title("Admin Options");
        routes[showOptions()].getUi().display();
    }
}
