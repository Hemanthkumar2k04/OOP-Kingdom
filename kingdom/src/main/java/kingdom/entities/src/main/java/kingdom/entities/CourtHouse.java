package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractCourtHouse;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.UUID;

public class CourtHouse extends AbstractCourtHouse {

    static {
        KingdomRegistry.register(CourtHouse.class);
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
    private int casesResolved;

    @JsonProperty
    private String currentCase;

    public CourtHouse() {
        this.id = "COURT-" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();
        this.name = "Court House";
        this.description = "A place where justice is served in the kingdom.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.casesResolved = 0;
        this.currentCase = null;
    }

    public CourtHouse(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void holdTrial(String caseName) {
        if (caseName == null || caseName.isBlank()) {
            return;
        }
        this.currentCase = caseName.trim();
    }

    @Override
    public int getCasesResolved() {
        return casesResolved;
    }

    @Override
    public String passJudgment(boolean guilty) {
        if (currentCase == null) {
            return "No active case.";
        }

        casesResolved++;
        String verdict = guilty
                ? "Guilty"
                : "Not Guilty";

        currentCase = null;
        return verdict;
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
