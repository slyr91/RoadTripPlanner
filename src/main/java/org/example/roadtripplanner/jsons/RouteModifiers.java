package org.example.roadtripplanner.jsons;

public class RouteModifiers {

    private boolean avoidTolls;
    private boolean avoidHighways;
    private boolean avoidFerries;

    public boolean isAvoidTolls() {
        return avoidTolls;
    }

    public void setAvoidTolls(boolean avoidTolls) {
        this.avoidTolls = avoidTolls;
    }

    public boolean isAvoidHighways() {
        return avoidHighways;
    }

    public void setAvoidHighways(boolean avoidHighways) {
        this.avoidHighways = avoidHighways;
    }

    public boolean isAvoidFerries() {
        return avoidFerries;
    }

    public void setAvoidFerries(boolean avoidFerries) {
        this.avoidFerries = avoidFerries;
    }

    @Override
    public String toString() {
        return "RouteModifiers{" +
                "avoidTolls=" + avoidTolls +
                ", avoidHighways=" + avoidHighways +
                ", avoidFerries=" + avoidFerries +
                '}';
    }
}
