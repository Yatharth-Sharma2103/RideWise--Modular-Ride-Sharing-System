package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {

    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        Driver leastActive = null;

        for (Driver driver : drivers) {
            if (!driver.isAvailable()) {
                continue;
            }
            if (leastActive == null || driver.getCompletedRides() < leastActive.getCompletedRides()) {
                leastActive = driver;
            }
        }
        return leastActive;
    }
}
