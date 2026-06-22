package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.UUID;

public class TownCrier implements KingdomEntity {

    static {
        KingdomRegistry.register(TownCrier.class);
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
    private int announcementCount;

    @JsonProperty
    private String lastAnnouncement;

    // No-arg constructor
    public TownCrier() {
        this.id = "TOWNCRIER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Town Crier";
        this.description = "Announces news and proclamations to the townsfolk.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.announcementCount = 0;
        this.lastAnnouncement = null;
    }

    // Parameterized constructor
    public TownCrier(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    public void announce(String message) {
        if (message == null) return;
        this.announcementCount++;
        this.lastAnnouncement = message;
    }

    public int getAnnouncementCount() {
        return this.announcementCount;
    }

    public String getLastAnnouncement() {
        return this.lastAnnouncement;
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
