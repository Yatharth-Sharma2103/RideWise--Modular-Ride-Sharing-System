package com.airtribe.ridewise.model;

public class Driver {
    private String id;
    private String name;
    private double currentLocation;
    private boolean available;
    private VehicleType vehicleType;
    private int completedRides;

    public Driver(String id, String name, double currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;
        this.completedRides = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getCurrentLocation() {
        return currentLocation;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public int getCompletedRides() {
        return completedRides;
    }

    public void incrementCompletedRides() {
        completedRides++;
    }

    @Override
    public String toString() {
        return "Driver{id='%s', name='%s', location=%.1f, vehicle=%s, available=%s, trips=%d}"
                .formatted(id, name, currentLocation, vehicleType, available, completedRides);
    }
}
