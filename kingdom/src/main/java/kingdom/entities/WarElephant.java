package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kingdom.contracts.AbstractWarElephant;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WarElephant extends AbstractWarElephant {

    static {
        KingdomRegistry.register(WarElephant.class);
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
    private KingdomEntity.Status status;

    @JsonProperty
    private boolean battleReady;

    @JsonProperty
    private List<String> riders;

    public WarElephant() {
        this.id = "WARELEPHANT-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();
        this.name = "War Elephant";
        this.description = "A powerful war elephant trained for battlefield charges and carrying elite riders.";
        this.foundingDate = LocalDate.now();
        this.status = KingdomEntity.Status.UNDER_CONSTRUCTION;
        this.battleReady = false;
        this.riders = new ArrayList<>();
    }

    public WarElephant(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = KingdomEntity.Status.OPERATIONAL;
    }

    @Override
    public void assignRider(String riderName) {
        if (riderName == null) {
            return;
        }

        String trimmedName = riderName.trim();
        if (trimmedName.isEmpty()) {
            return;
        }

        if (riders.contains(trimmedName)) {
            return;
        }

        riders.add(trimmedName);
        battleReady = true;
    }

    @Override
    @JsonIgnore
    public int getRiderCount() {
        return riders.size();
    }

    @Override
    public String charge() {
        if (!battleReady) {
            return "The war elephant is not battle-ready.";
        }
        return "The war elephant charges into enemy lines with " + getRiderCount() + " rider(s)!";
    }

    @Override
    public boolean isBattleReady() {
        return battleReady;
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
    public KingdomEntity.Status getStatus() {
        return status;
    }
}