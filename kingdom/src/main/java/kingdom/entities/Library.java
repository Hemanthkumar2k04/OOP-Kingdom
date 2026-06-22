package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractLibrary;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Library extends AbstractLibrary {

    static {
        KingdomRegistry.register(Library.class);
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
    private List<String> scrolls;

    public Library() {
        this.id = "LIBRARY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Library";
        this.description = "A grand repository of scrolls and knowledge preserving the wisdom of the kingdom.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.scrolls = new ArrayList<>();
    }

    public Library(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void addScroll(String scrollName) {
        if (scrollName == null || scrollName.isBlank()) {
            return;
        }
        scrolls.add(scrollName.trim());
    }

    @Override
    public List<String> getScrolls() {
        return new ArrayList<>(scrolls);
    }

    @Override
    public boolean hasScroll(String scrollName) {
        if (scrollName == null || scrollName.isBlank()) {
            return false;
        }
        return scrolls.contains(scrollName.trim());
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