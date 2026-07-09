# BeaconTower UML Diagram

```mermaid
classDiagram

class KingdomEntity {
    <<interface>>
    +String getIdentity()
    +String getName()
    +String getDescription()
    +LocalDate getFoundingDate()
    +Status getStatus()
}

class AbstractBeaconTower {
    <<abstract>>
    +lightBeacon(String signalType) void
    +isLit() boolean
    +getLastSignal() String
    +getSignalCount() int
}

class BeaconTower {
    -String id
    -String name
    -String description
    -LocalDate foundingDate
    -Status status
    -boolean lit
    -String lastSignal
    -int signalCount

    +BeaconTower()
    +BeaconTower(String name, String description)

    +lightBeacon(String signalType) void
    +isLit() boolean
    +getLastSignal() String
    +getSignalCount() int
    +extinguish() void

    +getIdentity() String
    +getName() String
    +getDescription() String
    +getFoundingDate() LocalDate
    +getStatus() Status
}

KingdomEntity <|.. AbstractBeaconTower
AbstractBeaconTower <|-- BeaconTower
```

## Design Notes

- `BeaconTower` extends `AbstractBeaconTower` and fulfills all required contract methods.
- The entity maintains three pieces of operational state: `lit`, `lastSignal`, and `signalCount`.
- Calling `lightBeacon(String signalType)` lights the beacon, records the signal type, and increments the signal count.
- `isLit()` returns whether the beacon is currently active without modifying state.
- `getLastSignal()` returns the most recent signal type sent from the tower.
- `getSignalCount()` returns the total number of signals sent from this tower.
- The implementation follows the project's OOP conventions while maintaining a simple and focused design.