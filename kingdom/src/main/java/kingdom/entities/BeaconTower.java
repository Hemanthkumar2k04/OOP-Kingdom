package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractBeaconTower;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
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
    private Status status;

    @JsonProperty
    private boolean lit;

    @JsonProperty
    private String lastSignal;

    @JsonProperty
    private int signalCount;

    public BeaconTower() {
        this.id = "BEACONTOWER-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
        this.name = "Beacon Tower";
        this.description = "A tower used to send signals across the battlefield using fire and smoke.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.lit = false;
        this.lastSignal = "";
        this.signalCount = 0;
    }

    public BeaconTower(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void lightBeacon(String signalType) {
        this.lit = true;
        this.lastSignal = signalType;
        this.signalCount++;
    }

    @Override
    public boolean isLit() {
        return lit;
    }

    @Override
    public String getLastSignal() {
        return lastSignal;
    }

    @Override
    public int getSignalCount() {
        return signalCount;
    }

    public void extinguish() {
        this.lit = false;
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