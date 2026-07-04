package kingdom.entities;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kingdom.contracts.AbstractExplosiveArena;
import kingdom.core.KingdomRegistry;
public class ExplosiveArena extends AbstractExplosiveArena {
    static {
        KingdomRegistry.register(ExplosiveArena.class);
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
    private List<String> demolitionists;

    @JsonProperty
    private int maxCapacity = 30;

    @JsonProperty
    private int explosivesReady;

    public ExplosiveArena() {
        this.id = "EXPLOSIVEARENA-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.name = "Explosive Arena";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.demolitionists = new ArrayList<>();
    }
    public ExplosiveArena(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }
    @Override
    public void trainDemolitionist(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank.");
        }
        if (this.demolitionists.size() >= this.maxCapacity) {
            throw new IllegalStateException("maximum capacity.");
        }
        this.demolitionists.add(name);
    }
    @Override
    @JsonIgnore
    public int getDemolitionistCount() {
        return this.demolitionists.size();
    }
    @Override
    public int prepareExplosive() {
        // Each demolitionist on staff can prep one extra charge alongside the base charge.
        int prepared = 1 + this.demolitionists.size();
        this.explosivesReady += prepared;
        return this.explosivesReady;
    }
    @Override
    public int getExplosivesReady() {
        return this.explosivesReady;
    }
    public List<String> getDemolitionists() {
        return this.demolitionists;
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