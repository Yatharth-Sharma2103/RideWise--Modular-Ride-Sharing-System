# Requirements

RideWise is a console ride-sharing system. The goal is LLD practice (SOLID, Strategy Pattern, composition), not a production app.

## A. Functional requirements

- Register riders.
- Register drivers.
- Show available drivers.
- Request a ride.
- Match the ride to a driver using a `RideMatchingStrategy`.
- Calculate fare using a `FareStrategy`.
- Track ride status: `REQUESTED`, `ASSIGNED`, `COMPLETED`, `CANCELLED`.
- Complete a ride and produce a `FareReceipt`.
- View rides.

Console menu:

1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
7. Exit

There is no Cancel Ride menu action. `CANCELLED` exists on the enum for the domain model.

## B. Non-functional requirements

- Pricing is extendable by adding a new `FareStrategy` implementation.
- Driver matching is changeable by injecting a different `RideMatchingStrategy`.
- Low coupling: services depend on strategy interfaces, not concrete classes.
- Code stays readable: one job per class, menu talks only to the service layer.
- In-memory storage; no database, REST, or UI framework.

## C. Domain entities (`model/`)

| Entity | Fields |
| --- | --- |
| Rider | id, name, location |
| Driver | id, name, currentLocation, available |
| Ride | id, rider, driver, distance, status |
| FareReceipt | rideId, amount, generatedAt |

Enums: `RideStatus`, `VehicleType` (`BIKE`, `AUTO`, `CAR`).

## D. Strategy design

- `RideMatchingStrategy.findDriver(Rider, List<Driver>)`  
  Implementations: `NearestDriverStrategy`, `LeastActiveDriverStrategy`.
- `FareStrategy.calculateFare(Ride)`  
  Implementations: `DefaultFareStrategy`, `PeakHourFareStrategy`.

Both strategies are injected into the `RideService` constructor (DIP, OCP, composition over inheritance).

## E. Service layer (`service/`)

- `RiderService`: register riders, get rider by id.
- `DriverService`: register drivers, update availability, list available drivers.
- `RideService`: request ride, assign driver, calculate fare, complete ride, list rides.

## F. Out of scope

Persistence, REST APIs, multithreading, maps/GPS, payments, ratings, login, surge beyond peak-hour fare.
