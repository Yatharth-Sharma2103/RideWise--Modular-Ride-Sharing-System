# SOLID Reflection

How RideWise applies SOLID, plus DRY, KISS, YAGNI, and Law of Demeter.

## SRP — Single Responsibility

Each class has one reason to change:

- `RiderService` registers and looks up riders.
- `DriverService` registers drivers and tracks availability.
- `RideService` orchestrates request → assign → complete.
- Matching algorithms live in `RideMatchingStrategy` implementations.
- Pricing lives in `FareStrategy` implementations.
- `Main` only prints the menu, reads input, and calls services.

Models do not match drivers or calculate fares.

## OCP — Open/Closed

`RideService` does not contain `if (matching == NEAREST)` or hard-coded fare formulas. A new matching or pricing rule is a new class that implements the existing interface. Core request/complete logic stays closed to modification.

## LSP — Liskov Substitution

Any `RideMatchingStrategy` may replace another: same inputs, same kind of result (a `Driver` or `null`). Any `FareStrategy` may replace another: same input (`Ride`), same kind of result (`double`). `Main` can swap `NearestDriverStrategy` for `LeastActiveDriverStrategy`, or `DefaultFareStrategy` for `PeakHourFareStrategy`, without changing `RideService`.

## ISP — Interface Segregation

Interfaces are small:

- `RideMatchingStrategy` has only `findDriver`.
- `FareStrategy` has only `calculateFare`.

Callers are not forced to implement unused methods.

## DIP — Dependency Inversion

`RideService` depends on `RideMatchingStrategy` and `FareStrategy` (abstractions). Concrete classes are created in `Main` and passed into the constructor. `RideService` never does `new NearestDriverStrategy()`.

## Other principles used

| Principle | Where |
| --- | --- |
| DRY | IDs come from `IdGenerator`. Matching and fare each live in one place. |
| KISS | Location is a number. Data is in-memory lists/maps. |
| YAGNI | No cancel menu, no database, no API, no ratings. |
| Law of Demeter | `RideService` calls `riderService`, `driverService`, and the strategies directly. It does not walk `ride.getDriver().getLocation()...` through unrelated objects. |
| Composition over inheritance | Strategies are injected into `RideService`; RideService is not subclassed per algorithm. |
