package rrpss.ui.tables;

import rrpss.ui.RouteMap;
import rrpss.ui.app.Back;

public class TableControllerSingle {

    public RouteMap[] routes = {
            new RouteMap("Create Table", new CreateTableUI()),
            new RouteMap("View Table availability", new ViewTableUI()),
            new RouteMap("Go Back", new Back()),
    };
}
