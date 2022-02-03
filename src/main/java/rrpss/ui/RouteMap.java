package rrpss.ui;

/**
 * Route map for the app
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class RouteMap {
    View component;
    Router.RoutePath route;
    String title;

    /**
     * Class constructor
     * @param route
     * @param ui
     */
    public RouteMap(Router.RoutePath route, UI ui) {
        this.component = ui;
        this.route = route;
        this.title = route.toString();
    }

    /**
     * Class constructor
     * @param title
     * @param ui
     */
    public RouteMap(String title, UI ui) {
        this.component = ui;
        this.title = title;
    }

    /**
     * Class constructor
     * @param title
     * @param component
     */
    public RouteMap(String title, Component component) {
        this.component = component;
        this.title = title;
    }

    /**
     * Get UI of the route map
     * @return
     */
    public View getUi() {
        return component;

    }

    /**
     * Route to the ui
     * @return route
     */
    public Router.RoutePath getRoute() {
        return route;
    }

    /**
     * Get route title
     * @return title
     */
    public String getTitle() {
        return title;
    }
}