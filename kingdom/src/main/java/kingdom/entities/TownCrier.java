package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractTownCrier;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TownCrier extends AbstractTownCrier {

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
    private List<String> announcements;

    public TownCrier() {
        this.id = "TOWNCRIER-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();

        this.name = "Town Crier";
        this.description = "A royal messenger responsible for delivering announcements throughout the kingdom.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.announcements = new ArrayList<>();
    }

    public TownCrier(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void announce(String message) {
        if (message == null || message.isBlank()) {
            return;
        }

        announcements.add(message.trim());
    }

    @Override
    @JsonIgnore
    public int getAnnouncementCount() {
        return announcements.size();
    }

    @Override
    @JsonIgnore
    public String getLatestAnnouncement() {
        if (announcements.isEmpty()) {
            return "";
        }
        return announcements.get(announcements.size() - 1);
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