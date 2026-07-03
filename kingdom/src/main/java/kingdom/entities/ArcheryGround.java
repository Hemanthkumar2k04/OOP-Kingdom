package kingdom.entities;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import kingdom.contracts.AbstractArcheryGround;
import kingdom.core.KingdomRegistry;

public class ArcheryGround extends AbstractArcheryGround {

    static {
        KingdomRegistry.register(ArcheryGround.class);
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
    private List<String> archers;

    @JsonProperty
    private int practiceSessions;

    @JsonProperty
    private int maxCapacity = 30;

    @JsonProperty
    private double lastPracticeAccuracy;

    private final Random random = new Random();

    public ArcheryGround() {
        this.id = "ARCHERYGROUND-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.name = "Archery Ground";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.archers = new ArrayList<>();
    }
    public ArcheryGround(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }


    @Override
    public void trainArcher(String archerName) {
        if (archerName == null || archerName.isBlank()) {
            throw new IllegalArgumentException("name must not be blank.");
        }
        if (this.archers.size() >= this.maxCapacity) {
            throw new IllegalStateException("maximum capacity.");
        }
        this.archers.add(archerName);
    }
    @Override
    @JsonIgnore
    public int getArcherCount() {
        return this.archers.size();
    }
    @Override
    public double holdPractice() {
        this.practiceSessions++;
        if (this.archers.isEmpty()) {
            this.lastPracticeAccuracy = 0.0;
            return this.lastPracticeAccuracy;
        }
        double base = 40.0 + random.nextDouble() * 20.0;
        double crowdBonus = Math.min(this.archers.size() * 2.0, 40.0);
        this.lastPracticeAccuracy = Math.min(100.0, base + crowdBonus);
        return this.lastPracticeAccuracy;
    }



    public List<String> getArchers() {
        return this.archers;
    }

    public int getPracticeSessions() {
        return this.practiceSessions;
    }

    public double getLastPracticeAccuracy() {
        return this.lastPracticeAccuracy;
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