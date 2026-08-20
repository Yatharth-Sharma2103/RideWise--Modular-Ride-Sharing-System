# RideWise

Console ride-sharing LLD demo in Java. Users register riders and drivers, request rides, match a driver with a strategy, calculate fare with a strategy, and track trip status.

This is **not** a production app. It shows composition over inheritance, the Strategy pattern, and SOLID.

## Features

- Register riders and drivers
- List available drivers
- Request a ride and assign a driver
- Complete a ride and print a fare receipt
- Ride status: `REQUESTED` → `ASSIGNED` → `COMPLETED`

**Matching:** `NearestDriverStrategy` (default) or `LeastActiveDriverStrategy`  
**Fare:** `DefaultFareStrategy` (default) or `PeakHourFareStrategy`

Swap either class in `Main` — do not change `RideService`.

## Requirements

- Java 21
- Maven

## Run

```bash
mvn compile
java -cp target/classes com.airtribe.ridewise.Main
```

Or run `com.airtribe.ridewise.Main` from IntelliJ.

## Menu

1. Add Rider  
2. Add Driver  
3. View Available Drivers  
4. Request Ride  
5. Complete Ride  
6. View Rides  
7. Exit  

Location is a number (used for nearest-driver distance). Vehicle type is `BIKE`, `AUTO`, or `CAR`. Default fare is `50 + 10 × distance`.

## Layout

```
com.airtribe.ridewise
├── Main.java
├── model/        Rider, Driver, Ride, FareReceipt, enums
├── strategy/     matching + fare (interfaces + implementations)
├── service/      RiderService, DriverService, RideService
├── exception/    NoDriverAvailableException
└── util/         IdGenerator
```

Design notes: [docs/Requirements.md](docs/Requirements.md), [docs/Class_Model.md](docs/Class_Model.md), [docs/SOLID_Reflection.md](docs/SOLID_Reflection.md), [docs/Object_Relationships.md](docs/Object_Relationships.md).
