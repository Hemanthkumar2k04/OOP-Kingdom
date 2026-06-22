package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractStorehouse;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.UUID;

public class Storehouse extends AbstractStorehouse {

    static {
        KingdomRegistry.register(Storehouse.class);
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
    private int grainStockpile;

    @JsonProperty
    private int resourceStockpile;

    public Storehouse() {
        this.id = "STOREHOUSE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Storehouse";
        this.description = "A fortified storehouse safeguarding the kingdom's grain and vital resources.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.grainStockpile = 0;
        this.resourceStockpile = 0;
    }

    public Storehouse(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void storeGrain(int amount) {
        if (amount > 0) {
            grainStockpile += amount;
        }
    }

    @Override
    public void storeResources(int amount) {
        if (amount > 0) {
            resourceStockpile += amount;
        }
    }

    @Override
    public int getGrainStockpile() {
        return grainStockpile;
    }

    @Override
    public int getResourceStockpile() {
        return resourceStockpile;
    }

    @Override
    public boolean distribute() {
        if (grainStockpile == 0 && resourceStockpile == 0) {
            return false;
        }
        if (grainStockpile > 0) {
            grainStockpile--;
        }
        if (resourceStockpile > 0) {
            resourceStockpile--;
        }
        return true;
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