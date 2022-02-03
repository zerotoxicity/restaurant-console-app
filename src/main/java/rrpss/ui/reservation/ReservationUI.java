package rrpss.ui.reservation;

import rrpss.ui.tables.TableController;
import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.app.Back;
import rrpss.ui.customer.CustomerController;
import rrpss.ui.tables.ViewTableUI;

/**
 * Boundary class for reservation
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class ReservationUI extends UI {

    private ReservationController reservationController = new ReservationController();
//

    /**
     * Show available routes
     * @return selected route index
     */
    public int showOptions() {
        for (int i = 0; i < reservationController.routes.length; i++)
            println(String.format("[%d] %s", i, reservationController.routes[i].getTitle()));
        return promptInt(true, 0,reservationController. routes.length - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        reservationController.routes[showOptions()].getUi().display();
    }
}
