package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcheryGroundTest {

    @Test
    void defaultConstructorShouldInitializeArcheryGround() {
        ArcheryGround ground = new ArcheryGround();

        assertNotNull(ground.getIdentity());
        assertTrue(ground.getIdentity().startsWith("ARCHERYGROUND-"));
        assertEquals("Archery Ground", ground.getName());
        assertNotNull(ground.getDescription());
        assertNotNull(ground.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, ground.getStatus());
        assertEquals(0, ground.getArcherCount());
        assertEquals(0.0, ground.holdPractice());
    }

    @Test
    void parameterizedConstructorShouldInitializeOperationalGround() {
        ArcheryGround ground =
                new ArcheryGround(
                        "Royal Archery Ground",
                        "Elite archery training facility");

        assertEquals("Royal Archery Ground", ground.getName());
        assertEquals("Elite archery training facility", ground.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, ground.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        ArcheryGround first = new ArcheryGround();
        ArcheryGround second = new ArcheryGround();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void trainArcherShouldIncreaseArcherCount() {
        ArcheryGround ground = new ArcheryGround();

        ground.trainArcher("Robin");

        assertEquals(1, ground.getArcherCount());
    }

    @Test
    void duplicateArchersShouldBeIgnored() {
        ArcheryGround ground = new ArcheryGround();

        ground.trainArcher("Robin");
        ground.trainArcher("Robin");

        assertEquals(1, ground.getArcherCount());
    }

    @Test
    void invalidArcherNamesShouldBeIgnored() {
        ArcheryGround ground = new ArcheryGround();

        ground.trainArcher(null);
        ground.trainArcher("");
        ground.trainArcher("   ");

        assertEquals(0, ground.getArcherCount());
    }

    @Test
    void holdPracticeShouldReturnZeroWhenNoArchersExist() {
        ArcheryGround ground = new ArcheryGround();

        assertEquals(0.0, ground.holdPractice());
    }

    @Test
    void holdPracticeShouldCalculateAccuracy() {
        ArcheryGround ground = new ArcheryGround();

        ground.trainArcher("Robin");
        ground.trainArcher("Marian");
        ground.trainArcher("Arthur");

        assertEquals(30.0, ground.holdPractice());
    }

    @Test
    void holdPracticeShouldCapAccuracyAtHundredPercent() {
        ArcheryGround ground = new ArcheryGround();

        for (int i = 1; i <= 15; i++) {
            ground.trainArcher("Archer" + i);
        }

        assertEquals(100.0, ground.holdPractice());
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        ArcheryGround original =
                new ArcheryGround(
                        "Royal Archery Ground",
                        "Elite archery training facility");

        original.trainArcher("Robin");
        original.trainArcher("Marian");

        String json = mapper.writeValueAsString(original);

        ArcheryGround restored =
                mapper.readValue(json, ArcheryGround.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getArcherCount(), restored.getArcherCount());
    }
}