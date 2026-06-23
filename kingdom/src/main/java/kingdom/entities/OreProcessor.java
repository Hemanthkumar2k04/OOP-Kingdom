package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractOreProcessor;
import kingdom.core.KingdomRegistry;


import java.time.LocalDate;
import java.util.UUID;

public class OreProcessor extends AbstractOreProcessor {

    static {
        KingdomRegistry.register(OreProcessor.class);
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
    private int rawOreStockpile;

    @JsonProperty
    private int processedStoneCount;

    @JsonProperty
    private int rawOreNeeded;

    public OreProcessor() {
        this.id = "OREPROCESSOR-" +
                  UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Ore Processor";
        this.description =
                "A facility that refines raw ore into processed stone for construction and crafting.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.rawOreStockpile = 50;
        this.processedStoneCount = 0;
        this.rawOreNeeded = 10;
    }

    public OreProcessor(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void processOre() {
        if (rawOreStockpile >= rawOreNeeded) {
            rawOreStockpile -= rawOreNeeded;
            processedStoneCount++;
        }
    }

    @Override
    public int getProcessedStoneCount() {
        return processedStoneCount;
    }

    @Override
    public int getRawOreNeeded() {
        return rawOreNeeded;
    }

    @Override
    public int getRawOreStockpile() {
        return rawOreStockpile;
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