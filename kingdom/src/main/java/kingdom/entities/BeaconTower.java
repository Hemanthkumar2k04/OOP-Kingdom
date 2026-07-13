package kingdom.entities;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kingdom.contracts.AbstractBeaconTower;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;
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
        this.id = "BEACONTOWER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Beacon Tower";
        this.description = "To send signals across the battlefield";
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
        return this.lit;
    }

    @Override
    @JsonIgnore
    public String getLastSignal() {
        if (this.signals.isEmpty()) {
            return "";
        }
        return this.signals.get(this.signals.size() - 1);
    }

    @Override
    @JsonIgnore
    public int getSignalCount() {
        return this.signals.size();
    }
    @JsonIgnore
    public List<String> getSignalHistory() {
        return Collections.unmodifiableList(this.signals);
    }
    public void extinguishBeacon() {
        this.lit = false;
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
    public KingdomEntity.Status getStatus() {
        return this.status;
    }
}