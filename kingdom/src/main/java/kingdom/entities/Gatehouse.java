
package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.*;

public class Gatehouse implements KingdomEntity {

    static {
        KingdomRegistry.register(Gatehouse.class);
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
    private List<String> currentVisitors;

    @JsonProperty
    private int totalVisitors;

    public Gatehouse() {
        this.id = "GATEHOUSE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Gatehouse";
        this.description = "The gatehouse controls access to the settlement and records visitors.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.currentVisitors = new ArrayList<>();
        this.totalVisitors = 0;
    }

    public Gatehouse(String id, String name) {
        this();
        this.id = id;
        this.name = name;
        this.status = Status.OPERATIONAL;
    }

    public String admitVisitor(String visitorName) {
        if(visitorName == null || visitorName.isBlank()) {
            throw new IllegalArgumentException("Visitor name cannot be null or empty");
        }
        this.currentVisitors.add(visitorName);
        this.totalVisitors += 1;
        return visitorName;
    }

    public String departVisitor(String visitorName) {
        if(!this.currentVisitors.contains(visitorName)) {
            throw new IllegalStateException("Visitor not present: " + visitorName);
        }
        this.currentVisitors.remove(visitorName);
        return visitorName;
    }

    public int getTotalVisitors() {
        return this.totalVisitors;
    }

    public List<String> getCurrentVisitors() {
        return Collections.unmodifiableList(this.currentVisitors);
    }

    public String getIdentity() { return this.id; }

    public String getName() { return this.name; }

    public String getDescription() { return this.description; }

    public LocalDate getFoundingDate() { return this.foundingDate; }

    public Status getStatus() { return this.status; }

    public void setStatus(Status status) { this.status = status; }
}
