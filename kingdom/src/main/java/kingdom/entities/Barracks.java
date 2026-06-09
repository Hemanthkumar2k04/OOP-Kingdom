package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractBarracks;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Barracks extends AbstractBarracks {

    static {
        KingdomRegistry.register(Barracks.class);
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
    private List<String> troops;

    // No-arg constructor - UNDER_CONSTRUCTION
    public Barracks() {
        this.id = "BARRACKS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Barracks";
        this.description = "A military facility for training and housing the kingdom's troops.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.troops = new ArrayList<>();
    }

    // Parameterized constructor - OPERATIONAL
    public Barracks(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void trainTroop(String troopName) {
        this.troops.add(troopName);
    }

    @Override
    public List<String> getTroops() {
        return Collections.unmodifiableList(troops);
    }

    @Override
    public String getIdentity() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public LocalDate getFoundingDate() {
        return this.foundingDate;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
