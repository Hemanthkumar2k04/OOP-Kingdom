package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractArcheryGround;
import kingdom.core.KingdomRegistry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArcheryGround extends AbstractArcheryGround {

    static {
        KingdomRegistry.register(ArcheryGround.class);
    }

    @JsonProperty("identity")
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private LocalDate foundingDate;

    @JsonProperty
    private Status status;

    @JsonProperty
    private List<String> archers;

    public ArcheryGround() {
        this.id = "ARCHERYGROUND-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Archery Ground";
        this.description = "A training ground where skilled archers are trained and practice their accuracy.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.archers = new ArrayList<>();
    }

    public ArcheryGround(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void trainArcher(String archerName) {
        if (archerName == null || archerName.isBlank()) {
            return;
        }
        String trimmed = archerName.trim();
        if (!archers.contains(trimmed)) {
            archers.add(trimmed);
        }
    }

    @Override
    @JsonIgnore
    public int getArcherCount() {
        return archers.size();
    }

    @Override
    public double holdPractice() {
        if (archers.isEmpty()) {
            return 0.0;
        }
        return Math.min(100.0, archers.size() * 10.0);
    }

    @Override
    public String getIdentity() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    @Override
    public Status getStatus() {
        return status;
    }
}