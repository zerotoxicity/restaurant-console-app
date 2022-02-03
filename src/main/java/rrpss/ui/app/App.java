package rrpss.ui.app;

import rrpss.controller.AuthController;
import rrpss.ui.Router;
import rrpss.ui.UI;
import rrpss.ui.RouteMap;

/**
 * Boundary class for the whole application function
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class App extends UI {
    /**
     * Singleton instance of app
     */
    private static App INSTANCE;

    /**
     * authController to access auth methods
     */
    private AuthController authController = AuthController.getInstance();
    /**
     * Router instance for app routing.
     */
    private Router router = new Router();

    /**
     * returns singleton instance of app
     * @return
     */
    public static App getInstance() {
        if (INSTANCE == null)
            INSTANCE = new App();
        return INSTANCE;
    }

    /**
     * Class constructor
     */
    private App() {
        println("Welcome to RRPSS");
    }

    /**
     *
     * @param presentOption determines to present option to user
     */
    public void navigateTo(boolean presentOption, Router.RoutePath route) throws Navigable {
        if (!presentOption) return;
        String response = promptStr(String.format("Navigate to screen [%s]\n[Y] to continue\n[N] to cancel\n", route.toString()), true);
        if (!response.equalsIgnoreCase("Y")) return;
        router.route(route);
        throw new Navigable(route);
    }

    /**
     * Display available functions post-login
     */
    private void routeAppRoutes() {
        title("Welcome %s | %s ", authController.getStaff().getName(), authController.getStaff().getTitle());
        displayOptions(router.appRoutes);
        router.appRoutesRouter(promptInt(true, 0, router.appRoutes.length - 1)).display();
    }
    /**
     * Display available functions pre-login
     */
    private void routePreRoutes() {
        displayOptions(router.preRoutes);
        router.preRoutesRouter(promptInt(true, 0, router.preRoutes.length - 1)).display();
    }


    private void displayOptions(RouteMap[] options) {
        for (int i = 0; i < options.length; i++)
            println(String.format("[%d] %s", i, options[i].getTitle()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void display() {
        if (authController.isAuthenticated()) routeAppRoutes();
        else routePreRoutes();
    }
}
