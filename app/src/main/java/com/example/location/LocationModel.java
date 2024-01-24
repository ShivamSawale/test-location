package com.example.location;

public class LocationModel {

    private double latitude;
    private double longitude;
    private long timestamp;

    // Empty constructor is required for Firestore
    public LocationModel(double longitude, double latitude) {
        // Default constructor
    }

    public LocationModel(double latitude, double longitude, long timestamp) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    // Getter and setter methods

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

