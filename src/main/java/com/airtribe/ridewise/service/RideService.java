package com.airtribe.ridewise.service;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.RideStatus;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;
import com.airtribe.ridewise.util.IdGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RideService {
    private final RiderService riderService;
    private final DriverService driverService;
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;
    private final Map<String, Ride> rides = new HashMap<>();

    public RideService(
            RiderService riderService,
            DriverService driverService,
            RideMatchingStrategy matchingStrategy,
            FareStrategy fareStrategy) {
        this.riderService = riderService;
        this.driverService = driverService;
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
    }

    public Ride requestRide(String riderId, double distance) {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) {
            throw new IllegalArgumentException("Rider not found: " + riderId);
        }

        List<Driver> availableDrivers = driverService.listAvailableDrivers();
        Driver driver = matchingStrategy.findDriver(rider, availableDrivers);
        if (driver == null) {
            throw new NoDriverAvailableException("No available driver for rider " + riderId);
        }

        Ride ride = new Ride(IdGenerator.nextId("T"), rider, distance);
        ride.assignDriver(driver);
        driverService.updateAvailability(driver.getId(), false);
        rides.put(ride.getId(), ride);
        return ride;
    }

    public FareReceipt completeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) {
            throw new IllegalArgumentException("Ride not found: " + rideId);
        }
        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride " + rideId + " is not ASSIGNED (current: " + ride.getStatus() + ")");
        }

        double amount = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(ride.getId(), amount);
        ride.attachReceipt(receipt);
        ride.setStatus(RideStatus.COMPLETED);

        Driver driver = ride.getDriver();
        driver.incrementCompletedRides();
        driverService.updateAvailability(driver.getId(), true);
        return receipt;
    }

    public List<Ride> getRides() {
        return new ArrayList<>(rides.values());
    }
}
