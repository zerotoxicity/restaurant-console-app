package rrpss.ui.tables;

import rrpss.ui.RouteMap;
import rrpss.ui.UI;
import rrpss.ui.app.Back;

/**
 * Boundary class for table management
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class TableUI extends UI {

    TableControllerSingle tableControllerSingle = new TableControllerSingle();

    public int showOptions() {
        for (int i = 0; i < tableControllerSingle.routes.length; i++)
            println(String.format("[%d] %s", i, tableControllerSingle.routes[i].getTitle()));
        return promptInt(true, 0, tableControllerSingle.routes.length - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        subtitle("Table Options");
        tableControllerSingle.routes[showOptions()].getUi().display();
    }
}
