package kingdom.entities;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HospitalTest {

    @Test
    void defaultConstructorShouldInitializeHospital() {
        Hospital hospital = new Hospital();

        assertNotNull(hospital.getIdentity());
        assertEquals("Hospital", hospital.getName());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION,
             hospital.getStatus());
        assertEquals(0, hospital.getPatientCount());
    }

    @Test
    void parameterizedConstructorShouldSetValues() {
        Hospital hospital = new Hospital(
                "Royal Hospital",
                "Kingdom healthcare center"
        );

        assertEquals("Royal Hospital", hospital.getName());
        assertEquals("Kingdom healthcare center", hospital.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL,
             hospital.getStatus());
        }
    @Test
    void admitPatientShouldIncreaseCount() {
        Hospital hospital = new Hospital();

        hospital.admitPatient("Arthur");

        assertEquals(1, hospital.getPatientCount());
    }

    @Test
    void admitPatientShouldIgnoreBlankNames() {
        Hospital hospital = new Hospital();

        hospital.admitPatient("");
        hospital.admitPatient(" ");
        hospital.admitPatient(null);

        assertEquals(0, hospital.getPatientCount());
    }

    @Test
    void dischargePatientShouldRemovePatient() {
        Hospital hospital = new Hospital();

        hospital.admitPatient("Arthur");
        hospital.dischargePatient("Arthur");

        assertEquals(0, hospital.getPatientCount());
    }
}