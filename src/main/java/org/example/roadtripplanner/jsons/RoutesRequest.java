package org.example.roadtripplanner.jsons;

import java.time.LocalDateTime;
import java.util.List;

public class RoutesRequest {

    private Location origin;
    private Location destination;
    private List<Location> intermediates;
    private String travelMode;
    private boolean computeAlternativeRoutes;
    private RouteModifiers routeModifiers;
    private String languageCode;
    private String units;
//    private String routingPreference;
//    private LocalDateTime departureTime;

    public RoutesRequest() {
        new RoutesRequest(true);
    }

    public RoutesRequest(boolean useDefaults) {
        if(useDefaults) {
            this.travelMode = "DRIVE";
            this.computeAlternativeRoutes = false;
            this.routeModifiers = new RouteModifiers();
            this.routeModifiers.setAvoidTolls(true);
            this.routeModifiers.setAvoidHighways(false);
            this.routeModifiers.setAvoidFerries(false);
            this.languageCode = "en-US";
            this.units = "IMPERIAL";
        }
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public List<Location> getIntermediates() {
        return intermediates;
    }

    public void setIntermediates(List<Location> intermediates) {
        this.intermediates = intermediates;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public boolean isComputeAlternativeRoutes() {
        return computeAlternativeRoutes;
    }

    public void setComputeAlternativeRoutes(boolean computeAlternativeRoutes) {
        this.computeAlternativeRoutes = computeAlternativeRoutes;
    }

    public RouteModifiers getRouteModifiers() {
        return routeModifiers;
    }

    public void setRouteModifiers(RouteModifiers routeModifiers) {
        this.routeModifiers = routeModifiers;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "RoutesRequest{" +
                "origin=" + origin +
                ", destination=" + destination +
                ", intermediates=" + intermediates +
                ", travelMode='" + travelMode + '\'' +
                ", computeAlternativeRoutes=" + computeAlternativeRoutes +
                ", routeModifiers=" + routeModifiers +
                ", languageCode='" + languageCode + '\'' +
                ", units='" + units + '\'' +
                '}';
    }
}
