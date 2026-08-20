package com.airtribe.ridewise.strategy;

import com.airtribe.ridewise.model.Ride;

import java.time.LocalTime;

public class PeakHourFareStrategy implements FareStrategy {
    private static final double BASE_FARE = 50.0;
    private static final double RATE_PER_UNIT = 10.0;
    private static final double PEAK_MULTIPLIER = 1.5;

    @Override
    public double calculateFare(Ride ride) {
        double fare = BASE_FARE + (RATE_PER_UNIT * ride.getDistance());
        if (isPeakHour(LocalTime.now())) {
            fare *= PEAK_MULTIPLIER;
        }
        return fare;
    }

    private boolean isPeakHour(LocalTime time) {
        int hour = time.getHour();
        return (hour >= 8 && hour < 11) || (hour >= 17 && hour < 21);
    }
}
