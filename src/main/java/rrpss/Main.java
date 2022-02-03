package rrpss;

import rrpss.controller.AuthController;
import rrpss.ui.tables.TableController;
import rrpss.ui.app.App;

/**
 *  RRPSS starting point
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class Main {

    /**
     * Initialization
     */
    public static void init() {
        AuthController.getInstance();
        TableController.getInstance();
    }

    /**
     * Start the app
     * @param args
     */
    public static void main(String[] args) {
        init();
        App app = App.getInstance();

        lifecycle: while(true) {
            app.display();
        }
    }
}
