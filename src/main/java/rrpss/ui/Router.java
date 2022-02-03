package rrpss.ui;

import rrpss.ui.admin.AdminUI;
import rrpss.ui.auth.LoginUI;
import rrpss.ui.auth.RegisterUI;
import rrpss.ui.customer.CustomerUI;
import rrpss.ui.food.FoodUI;
import rrpss.ui.menu.MenuUI;
import rrpss.ui.order.OrderUI;
import rrpss.ui.report.ReportUI;
import rrpss.ui.reservation.ReservationUI;
import rrpss.ui.tables.TableUI;

/**
 * Navigation to other boundary class
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class Router {
    public enum RoutePath {
        ADMIN {
            @Override
            public String toString() {
                return "Admin Configuration";
            }
        },
        LOGIN {
            @Override
            public String toString() {
                return "Login";
            }
        },
        REGISTER {
            @Override
            public String toString() {
                return "Register";
            }
        },
        TABLE {
            @Override
            public String toString() {
                return "Table Management";
            }
        },
        MENU {
            @Override
            public String toString() {
                return "Menu Management";
            }
        },
        FOOD {
            @Override
            public String toString() {
                return "Food Management";
            }
        },
        RESERVATION {
            @Override
            public String toString() {
                return "Reservations";
            }
        },
        CUSTOMER {
            @Override
            public String toString() {
                return "Customer Management";
            }
        },
        ORDER {
            @Override
            public String toString() {
                return "Take Order";
            }
        },
        REPORT{
            @Override
            public String toString() {
                return "Sales revenue report";
            }
        }
    }

    /**
     * Application routes
     * Have to be authenticated
     */
    public RouteMap[] appRoutes = {
            new RouteMap(RoutePath.ADMIN, new AdminUI()),
            new RouteMap(RoutePath.TABLE, new TableUI()),
            new RouteMap(RoutePath.MENU, new MenuUI()),
            new RouteMap(RoutePath.FOOD, new FoodUI()),
            new RouteMap(RoutePath.RESERVATION, new ReservationUI()),
            new RouteMap(RoutePath.CUSTOMER, new CustomerUI()),
            new RouteMap(RoutePath.ORDER, new OrderUI()),
            new RouteMap(RoutePath.REPORT, new ReportUI()),
    };

    /**
     * Pre authentication routes
     */
    public RouteMap[] preRoutes = {
            new RouteMap(RoutePath.LOGIN, new LoginUI()),
            new RouteMap(RoutePath.REGISTER, new RegisterUI()),
    };

    public void route(RoutePath path)  {
        for (RouteMap map : appRoutes) {
            if (map.getRoute() == path) {
                map.getUi().display();
                return;
            }
        }
    }

    /**
     * Get the boundary class for the route at the specified index for pre-authentication routes
     * @param i index
     * @return boundary class
     */
    public View preRoutesRouter(int i) {
        return  preRoutes[i].getUi();
    }

    /**
     * Get the boundary class for the route at the specified index for post-authentication routes
     * @param i index
     * @return boundary class
     */
    public View appRoutesRouter(int i) {
        try {
            return appRoutes[i].getUi();
        } catch(Exception e) {
            return null;
        }
    }

}