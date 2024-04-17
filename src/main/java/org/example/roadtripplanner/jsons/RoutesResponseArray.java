package org.example.roadtripplanner.jsons;

import java.util.List;

public class RoutesResponseArray {
    private List<RoutesResponse> routes;

    public List<RoutesResponse> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesResponse> routes) {
        this.routes = routes;
    }

    @Override
    public String toString() {
        return "RouteResponseArray{" +
                "routes=" + routes +
                '}';
    }
}
