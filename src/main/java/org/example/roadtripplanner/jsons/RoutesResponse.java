package org.example.roadtripplanner.jsons;

public class RoutesResponse {

    private Long distanceMeters;
    private String duration;
    private Polyline polyline;

    public Long getDistanceMeters() {
        return distanceMeters;
    }

    public void setDistanceMeters(Long distanceMeters) {
        this.distanceMeters = distanceMeters;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Polyline getPolyline() {
        return polyline;
    }

    public void setPolyline(Polyline polyline) {
        this.polyline = polyline;
    }

    @Override
    public String toString() {
        return "RoutesResponse{" +
                "distanceMeters=" + distanceMeters +
                ", duration='" + duration + '\'' +
                ", polyline=" + polyline +
                '}';
    }
}
