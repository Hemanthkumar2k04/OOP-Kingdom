package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractHospital;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Hospital extends AbstractHospital {

    static {
     KingdomRegistry.register(Hospital.class);
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
    private List<String> patients;

    public Hospital() {
        this.id = "HOSPITAL-" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        this.name = "Hospital";
        this.description = "A place of healing where the sick and wounded are treated and cared for.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.patients = new ArrayList<>();
    }

    public Hospital(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void admitPatient(String patientName) {
        if (patientName == null || patientName.isBlank()) {
            return;
        }
        patients.add(patientName.trim());
    }

    @Override
    public int getPatientCount() {
        return patients.size();
    }

    @Override
    public void dischargePatient(String patientName) {
        if (patientName == null) {
            return;
        }
        patients.remove(patientName.trim());
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