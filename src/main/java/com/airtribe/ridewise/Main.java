package com.airtribe.ridewise;

import com.airtribe.ridewise.exception.NoDriverAvailableException;
import com.airtribe.ridewise.model.Driver;
import com.airtribe.ridewise.model.FareReceipt;
import com.airtribe.ridewise.model.Ride;
import com.airtribe.ridewise.model.Rider;
import com.airtribe.ridewise.model.VehicleType;
import com.airtribe.ridewise.service.DriverService;
import com.airtribe.ridewise.service.RideService;
import com.airtribe.ridewise.service.RiderService;
import com.airtribe.ridewise.strategy.DefaultFareStrategy;
import com.airtribe.ridewise.strategy.FareStrategy;
import com.airtribe.ridewise.strategy.NearestDriverStrategy;
import com.airtribe.ridewise.strategy.RideMatchingStrategy;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RiderService riderService = new RiderService();
        DriverService driverService = new DriverService();
        RideMatchingStrategy matchingStrategy = new NearestDriverStrategy();
        FareStrategy fareStrategy = new DefaultFareStrategy();
        RideService rideService = new RideService(riderService, driverService, matchingStrategy, fareStrategy);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addRider(scanner, riderService);
                    case "2" -> addDriver(scanner, driverService);
                    case "3" -> viewAvailableDrivers(driverService);
                    case "4" -> requestRide(scanner, rideService);
                    case "5" -> completeRide(scanner, rideService);
                    case "6" -> viewRides(rideService);
                    case "7" -> {
                        running = false;
                        System.out.println("Goodbye.");
                    }
                    default -> System.out.println("Invalid option. Enter 1-7.");
                }
            } catch (NoDriverAvailableException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== RideWise =====");
        System.out.println("1. Add Rider");
        System.out.println("2. Add Driver");
        System.out.println("3. View Available Drivers");
        System.out.println("4. Request Ride");
        System.out.println("5. Complete Ride");
        System.out.println("6. View Rides");
        System.out.println("7. Exit");
    }

    private static void addRider(Scanner scanner, RiderService riderService) {
        System.out.print("Rider name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        Double location = readDouble(scanner, "Location (number): ");
        if (location == null) {
            return;
        }
        Rider rider = riderService.registerRider(name, location);
        System.out.println("Registered: " + rider);
    }

    private static void addDriver(Scanner scanner, DriverService driverService) {
        System.out.print("Driver name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        Double location = readDouble(scanner, "Current location (number): ");
        if (location == null) {
            return;
        }
        System.out.print("Vehicle type (BIKE, AUTO, CAR): ");
        String vehicleInput = scanner.nextLine().trim().toUpperCase();
        VehicleType vehicleType;
        try {
            vehicleType = VehicleType.valueOf(vehicleInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type. Use BIKE, AUTO, or CAR.");
            return;
        }
        Driver driver = driverService.registerDriver(name, location, vehicleType);
        System.out.println("Registered: " + driver);
    }

    private static void viewAvailableDrivers(DriverService driverService) {
        List<Driver> drivers = driverService.listAvailableDrivers();
        if (drivers.isEmpty()) {
            System.out.println("No available drivers.");
            return;
        }
        drivers.forEach(System.out::println);
    }

    private static void requestRide(Scanner scanner, RideService rideService) {
        System.out.print("Rider ID: ");
        String riderId = scanner.nextLine().trim();
        Double distance = readDouble(scanner, "Distance: ");
        if (distance == null) {
            return;
        }
        if (distance < 0) {
            System.out.println("Distance cannot be negative.");
            return;
        }
        Ride ride = rideService.requestRide(riderId, distance);
        System.out.println("Ride assigned: " + ride);
    }

    private static void completeRide(Scanner scanner, RideService rideService) {
        System.out.print("Ride ID: ");
        String rideId = scanner.nextLine().trim();
        FareReceipt receipt = rideService.completeRide(rideId);
        System.out.println("Ride completed. " + receipt);
    }

    private static void viewRides(RideService rideService) {
        List<Ride> rides = rideService.getRides();
        if (rides.isEmpty()) {
            System.out.println("No rides yet.");
            return;
        }
        rides.forEach(System.out::println);
    }

    private static Double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number: " + raw);
            return null;
        }
    }
}
