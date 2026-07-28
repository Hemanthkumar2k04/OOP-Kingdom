package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;
import kingdom.contracts.AbstractCourtHouse;
import kingdom.core.KingdomRegistry;

/**
 * The CourtHouse entity maintains justice and resolves cases in the kingdom.
 * It tracks active trials, verdicts passed, and total cases resolved.
 */
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
    private String activeCaseName;

    @JsonProperty
    private String lastVerdict;

    /**
     * Default constructor for Jackson serialization and reflection-based instantiation.
     * Initializes safe defaults.
     */
    public CourtHouse() {
        this.id = "COURTHOUSE-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Court House";
        this.description = "The hall of justice where trials are held and law is enforced.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.casesResolved = 0;
        this.activeCaseName = null;
        this.lastVerdict = null;
    }

    /**
     * Parameterized constructor for explicit creation.
     */
    public CourtHouse(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    /**
     * Holds a trial for a given case name.
     * @param caseName description of the case under trial
     */
    @Override
    public void holdTrial(String caseName) {
        if (caseName != null && !caseName.trim().isEmpty()) {
            this.activeCaseName = caseName.trim();
        }
    }

    /**
     * Returns total number of resolved cases.
     */
    @Override
    public int getCasesResolved() {
        return this.casesResolved;
    }

    /**
     * Passes a judgment on the current active case.
     * @param guilty true if guilty, false if acquitted
     * @return verdict description
     */
    @Override
    public String passJudgment(boolean guilty) {
        if (this.activeCaseName == null) {
            return "No active case to judge";
        }
        String verdict = guilty 
            ? "Guilty: " + this.activeCaseName + " sentenced according to OOP Kingdom law."
            : "Acquitted: " + this.activeCaseName + " cleared of all charges.";

        this.lastVerdict = verdict;
        this.activeCaseName = null;
        this.casesResolved++;
        return verdict;
    }

    /**
     * Retrieves the current active case name.
     */
    public String getActiveCaseName() {
        return this.activeCaseName;
    }

    /**
     * Retrieves the latest verdict passed by this court.
     */
    public String getLastVerdict() {
        return this.lastVerdict;
    }

    /**
     * Sets the status of the court house.
     */
    public void setStatus(Status status) {
        this.status = status;
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
