package kingdom.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test suite for CourtHouse entity.
 */
class CourtHouseTest {

    private CourtHouse courtHouse;

    @BeforeEach
    void setUp() {
        courtHouse = new CourtHouse();
    }

    @Test
    void testDefaultConstructorSafeDefaults() {
        assertNotNull(courtHouse.getIdentity());
        assertTrue(courtHouse.getIdentity().startsWith("COURTHOUSE-"));
        assertEquals("Court House", courtHouse.getName());
        assertNotNull(courtHouse.getDescription());
        assertEquals(LocalDate.now(), courtHouse.getFoundingDate());
        assertEquals(CourtHouse.Status.UNDER_CONSTRUCTION, courtHouse.getStatus());
        assertEquals(0, courtHouse.getCasesResolved());
        assertNull(courtHouse.getActiveCaseName());
        assertNull(courtHouse.getLastVerdict());
    }

    @Test
    void testParameterizedConstructor() {
        CourtHouse customCourt = new CourtHouse("Royal High Court", "The supreme tribunal of the realm.");
        assertTrue(customCourt.getIdentity().startsWith("COURTHOUSE-"));
        assertEquals("Royal High Court", customCourt.getName());
        assertEquals("The supreme tribunal of the realm.", customCourt.getDescription());
        assertEquals(CourtHouse.Status.OPERATIONAL, customCourt.getStatus());
    }

    @Test
    void testUUIDUniqueness() {
        CourtHouse court1 = new CourtHouse();
        CourtHouse court2 = new CourtHouse();
        assertNotEquals(court1.getIdentity(), court2.getIdentity());
    }

    @Test
    void testHoldTrialAndPassJudgmentGuilty() {
        courtHouse.holdTrial("Case #101: Stolen Bread");
        assertEquals("Case #101: Stolen Bread", courtHouse.getActiveCaseName());

        String verdict = courtHouse.passJudgment(true);
        assertTrue(verdict.contains("Guilty"));
        assertTrue(verdict.contains("Case #101: Stolen Bread"));
        assertEquals(1, courtHouse.getCasesResolved());
        assertNull(courtHouse.getActiveCaseName());
        assertEquals(verdict, courtHouse.getLastVerdict());
    }

    @Test
    void testHoldTrialAndPassJudgmentAcquitted() {
        courtHouse.holdTrial("Case #102: Boundary Dispute");
        String verdict = courtHouse.passJudgment(false);
        assertTrue(verdict.contains("Acquitted"));
        assertTrue(verdict.contains("Case #102: Boundary Dispute"));
        assertEquals(1, courtHouse.getCasesResolved());
    }

    @Test
    void testPassJudgmentWithoutActiveCase() {
        String verdict = courtHouse.passJudgment(true);
        assertEquals("No active case to judge", verdict);
        assertEquals(0, courtHouse.getCasesResolved());
    }

    @Test
    void testMultipleCasesResolvedCount() {
        courtHouse.holdTrial("Case A");
        courtHouse.passJudgment(true);

        courtHouse.holdTrial("Case B");
        courtHouse.passJudgment(false);

        courtHouse.holdTrial("Case C");
        courtHouse.passJudgment(true);

        assertEquals(3, courtHouse.getCasesResolved());
    }

    @Test
    void testStatusTransitions() {
        assertEquals(CourtHouse.Status.UNDER_CONSTRUCTION, courtHouse.getStatus());
        courtHouse.setStatus(CourtHouse.Status.OPERATIONAL);
        assertEquals(CourtHouse.Status.OPERATIONAL, courtHouse.getStatus());
        courtHouse.setStatus(CourtHouse.Status.DAMAGED);
        assertEquals(CourtHouse.Status.DAMAGED, courtHouse.getStatus());
    }

    @Test
    void testJacksonSerializationAndDeserialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        courtHouse.holdTrial("Case #200: Tax Evasion");
        courtHouse.passJudgment(true);

        String json = mapper.writeValueAsString(courtHouse);
        assertNotNull(json);
        assertTrue(json.contains("identity"));
        assertTrue(json.contains("casesResolved"));

        CourtHouse deserialized = mapper.readValue(json, CourtHouse.class);
        assertEquals(courtHouse.getIdentity(), deserialized.getIdentity());
        assertEquals(courtHouse.getName(), deserialized.getName());
        assertEquals(courtHouse.getCasesResolved(), deserialized.getCasesResolved());
        assertEquals(courtHouse.getLastVerdict(), deserialized.getLastVerdict());
    }
}
