package org.example.roadtripplanner.jsons;

public class Location {
    private String address;
    private String placeID;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", placeID='" + placeID + '\'' +
                '}';
    }
}
