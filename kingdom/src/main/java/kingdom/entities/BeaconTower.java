package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kingdom.contracts.AbstractBeaconTower;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BeaconTower extends AbstractBeaconTower {

    static {
        KingdomRegistry.register(BeaconTower.class);
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
    private boolean lit;

    @JsonProperty
    private List<String> signals;

    public BeaconTower() {
        this.id = "BEACONTOWER-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();
        this.name = "Beacon Tower";
        this.description = "A strategic tower used to communicate battlefield signals across the kingdom.";
        this.foundingDate = LocalDate.now();
        this.status = KingdomEntity.Status.UNDER_CONSTRUCTION;
        this.lit = false;
        this.signals = new ArrayList<>();
    }

    public BeaconTower(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = KingdomEntity.Status.OPERATIONAL;
    }

    @Override
    public void lightBeacon(String signalType) {
        if (signalType == null) {
            return;
        }

        String trimmedSignal = signalType.trim();
        if (trimmedSignal.isEmpty()) {
            return;
        }

        this.lit = true;
        this.signals.add(trimmedSignal);
    }

    @Override
    public boolean isLit() {
        return lit;
    }

    @Override
    @JsonIgnore
    public String getLastSignal() {
        if (signals.isEmpty()) {
            return "";
        }
        return signals.get(signals.size() - 1);
    }

    @Override
    @JsonIgnore
    public int getSignalCount() {
        return signals.size();
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