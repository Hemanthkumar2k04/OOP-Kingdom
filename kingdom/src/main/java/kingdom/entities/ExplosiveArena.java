package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.*;
import kingdom.contracts.AbstractExplosiveArena;
import kingdom.core.KingdomRegistry;

/**
 * The Explosive Arena is a kingdom entity responsible for training demolitionists
 * and managing explosives for the kingdom's military operations.
 * It prepares explosive charges for battle and maintains trained demolitionists.
 *
 * Key Features:
 * - Trains demolitionists to handle explosives safely
 * - Prepares explosive charges for battle
 * - Tracks the number of explosives ready for use
 * - Maintains status (OPERATIONAL, UNDER_CONSTRUCTION, DAMAGED)
 */
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
    private int explosivesReady;

    @JsonProperty
    private int preparationRate;

    @JsonProperty
    private int maxExplosiveCapacity;

    /**
     * Default constructor for Jackson serialization and reflection-based instantiation.
     * Initializes safe defaults with UNDER_CONSTRUCTION status.
     */
    public ExplosiveArena() {
        this.id = "EXPLOSIVEARENA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Explosive Arena";
        this.description = "A specialized training facility for demolitionists and explosive preparation.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.demolitionists = new ArrayList<>();
        this.explosivesReady = 0;
        this.preparationRate = 10; // Each preparation yields 10 explosives
        this.maxExplosiveCapacity = 100; // Maximum storable explosives
    }

    /**
     * Parameterized constructor for explicit instantiation with OPERATIONAL status.
     */
    public ExplosiveArena(String name, int preparationRate, int maxExplosiveCapacity) {
        this();
        this.name = name;
        this.preparationRate = preparationRate;
        this.maxExplosiveCapacity = maxExplosiveCapacity;
        this.status = Status.OPERATIONAL;
    }

    /**
     * Trains a new demolitionist in handling explosives.
     * Adds the demolitionist to the list of trained personnel.
     * @param name the name of the demolitionist
     * @throws IllegalArgumentException if name is null or empty
     */
    @Override
    public void trainDemolitionist(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Demolitionist name cannot be null or empty");
        }
        this.demolitionists.add(name.trim());
    }

    /**
     * Returns the total number of trained demolitionists.
     * @return demolitionist count
     */
    @Override
    @JsonIgnore
    public int getDemolitionistCount() {
        return this.demolitionists.size();
    }

    /**
     * Prepares explosive charges for battle.
     * Increases the explosives ready count based on preparation rate.
     * If in DAMAGED status, preparation is inefficient (reduced by 50%).
     * Capped at maximum explosive capacity.
     * @return the number of explosives ready after preparation
     */
    @Override
    public int prepareExplosive() {
        if (this.status == Status.DAMAGED) {
            // Damaged arena is less efficient
            int reducedPreparation = this.preparationRate / 2;
            this.explosivesReady = Math.min(this.explosivesReady + reducedPreparation, this.maxExplosiveCapacity);
        } else {
            // Normal preparation
            this.explosivesReady = Math.min(this.explosivesReady + this.preparationRate, this.maxExplosiveCapacity);
        }
        return this.explosivesReady;
    }

    /**
     * Returns the number of explosives currently ready for use.
     * @return explosives ready count
     */
    @Override
    public int getExplosivesReady() {
        return this.explosivesReady;
    }

    /**
     * Gets the identity of this Explosive Arena.
     * @return the unique identifier
     */
    public String getIdentity() {
        return this.id;
    }

    /**
     * Gets the name of this Explosive Arena.
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the description of this Explosive Arena.
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the founding date of this Explosive Arena.
     * @return the founding date
     */
    public LocalDate getFoundingDate() {
        return this.foundingDate;
    }

    /**
     * Gets the current status of this Explosive Arena.
     * @return the status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Sets the status of this Explosive Arena.
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets an unmodifiable list of trained demolitionists.
     * @return unmodifiable list of demolitionists
     */
    public List<String> getDemolitionists() {
        return Collections.unmodifiableList(this.demolitionists);
    }

    /**
     * Gets the preparation rate per explosive preparation.
     * @return the preparation rate
     */
    public int getPreparationRate() {
        return this.preparationRate;
    }

    /**
     * Gets the maximum explosive capacity.
     * @return the max capacity
     */
    public int getMaxExplosiveCapacity() {
        return this.maxExplosiveCapacity;
    }

    /**
     * Sets the number of explosives ready (for testing purposes).
     * @param explosivesReady the number of explosives ready
     */
    public void setExplosivesReady(int explosivesReady) {
        this.explosivesReady = Math.min(explosivesReady, this.maxExplosiveCapacity);
    }
}
