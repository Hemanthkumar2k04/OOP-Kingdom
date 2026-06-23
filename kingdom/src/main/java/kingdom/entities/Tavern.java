package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import kingdom.contracts.AbstractTavern;
import kingdom.core.KingdomRegistry;

/**
 * A tavern entity where heroes are recruited and may rest between adventures.
 * Tracks the roster of hired heroes and recovers from damage through rest.
 */
public class Tavern extends AbstractTavern {

    static {
        KingdomRegistry.register(Tavern.class);
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
    private List<String> heroes = new ArrayList<>();

    public Tavern() {
        this.id = "TAVERN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Tavern";
        this.description = "A warm refuge where heroes gather, share tales, and answer the kingdom's call.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.heroes = new ArrayList<>();
    }

    public Tavern(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    /**
     * Hires a hero by name. Null and blank names are silently ignored.
     * Leading and trailing whitespace is trimmed before storing.
     */
    @Override
    public void hireHero(String heroName) {
        if (heroName == null || heroName.isBlank()) {
            return;
        }
        this.heroes.add(heroName.trim());
    }

    /**
     * Returns a defensive copy of the current hero roster.
     */
    @Override
    public List<String> getHeroes() {
        return new ArrayList<>(this.heroes);
    }

    /**
     * Restores the tavern to OPERATIONAL if it is currently DAMAGED.
     */
    @Override
    public void rest() {
        if (this.status == Status.DAMAGED) {
            this.status = Status.OPERATIONAL;
        }
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