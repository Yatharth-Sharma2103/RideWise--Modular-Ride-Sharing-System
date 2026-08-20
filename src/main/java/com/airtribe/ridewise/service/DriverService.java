package com.airtribe.ridewise.service;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.util.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DriverService {
    private final Map<String, Driver> drivers = new HashMap<>();

    public Driver registerDriver(String name, double currentLocation, VehicleType vehicleType) {
        String id = IdGenerator.nextId("D");
        Driver driver = new Driver(id, name, currentLocation, vehicleType);
        drivers.put(id, driver);
        return driver;
    }

    public Driver getDriverById(String id) {
        return drivers.get(id);
    }

    public void updateAvailability(String driverId, boolean available) {
        Driver driver = drivers.get(driverId);
        if (driver != null) {
            driver.setAvailable(available);
        }
    }

    public List<Driver> listAvailableDrivers() {
        List<Driver> available = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable()) {
                available.add(driver);
            }
        }
        return available;
    }
}
