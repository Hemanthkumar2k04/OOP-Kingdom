# DiplomaticEnclave UML Diagram

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

class AbstractDiplomaticEnclave {
    <<abstract>>
    +sendEnvoy(String kingdomName, String message) void
    +getPendingMessageCount() int
    +readLatestMessage() String
    +forgeAlliance(String kingdomName) boolean
}

class DiplomaticEnclave {
    -String id
    -String name
    -String description
    -LocalDate foundingDate
    -Status status
    -List~String~ pendingMessages
    -Set~String~ alliedKingdoms

    +DiplomaticEnclave()
    +DiplomaticEnclave(String name, String description)

    +sendEnvoy(String kingdomName, String message) void
    +getPendingMessageCount() int
    +readLatestMessage() String
    +forgeAlliance(String kingdomName) boolean

    +getIdentity() String
    +getName() String
    +getDescription() String
    +getFoundingDate() LocalDate
    +getStatus() Status
}

KingdomEntity <|.. AbstractDiplomaticEnclave
AbstractDiplomaticEnclave <|-- DiplomaticEnclave
```

## Design Notes

The `DiplomaticEnclave` entity represents a diplomatic center responsible for managing alliances and diplomatic communications between kingdoms.

### Design Decisions

- The entity maintains a `List<String>` of pending diplomatic messages rather than storing only a message count or latest message.
- `sendEnvoy(String kingdomName, String message)` validates input, trims whitespace, and stores messages in the format `<Kingdom>: <Message>`.
- `getPendingMessageCount()` derives the total number of pending messages directly from the message list, avoiding redundant state.
- `readLatestMessage()` retrieves the most recent diplomatic message and returns an empty string when no messages are available.
- Alliances are stored using a `Set<String>` to ensure uniqueness. `forgeAlliance(String kingdomName)` naturally returns `true` only when a new alliance is successfully established.
- Computed properties are excluded from Jackson serialization using `@JsonIgnore`, preventing redundant serialized data while maintaining a clean object model.