# WarElephant UML Diagram

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

class AbstractWarElephant {
    <<abstract>>
    +assignRider(String riderName) void
    +getRiderCount() int
    +charge() String
    +isBattleReady() boolean
}

class WarElephant {
    -String id
    -String name
    -String description
    -LocalDate foundingDate
    -Status status
    -boolean battleReady
    -List~String~ riders

    +WarElephant()
    +WarElephant(String name, String description)

    +assignRider(String riderName) void
    +getRiderCount() int
    +charge() String
    +isBattleReady() boolean

    +getIdentity() String
    +getName() String
    +getDescription() String
    +getFoundingDate() LocalDate
    +getStatus() Status
}

KingdomEntity <|.. AbstractWarElephant
AbstractWarElephant <|-- WarElephant
```

## Design Notes

The `WarElephant` entity represents a trained battle elephant capable of carrying riders into combat.

### Design Decisions

- The entity maintains a `List<String>` of assigned riders instead of storing only a rider count.
- `assignRider(String riderName)` validates input, trims whitespace, prevents duplicate rider assignments, and marks the elephant as battle-ready once at least one rider has been assigned.
- `getRiderCount()` derives the total number of assigned riders directly from the rider list, avoiding redundant state.
- `charge()` returns a descriptive message based on the elephant's current battle readiness and rider count.
- `isBattleReady()` represents the actual operational state of the elephant and is stored as persistent state.
- Computed properties are excluded from Jackson serialization using `@JsonIgnore`, while the battle readiness state remains serializable.