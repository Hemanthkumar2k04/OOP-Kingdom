package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarElephantTest {

    @Test
    void defaultConstructorShouldInitializeWarElephant() {
        WarElephant elephant = new WarElephant();

        assertNotNull(elephant.getIdentity());
        assertTrue(elephant.getIdentity().startsWith("WARELEPHANT-"));
        assertEquals("War Elephant", elephant.getName());
        assertNotNull(elephant.getDescription());
        assertNotNull(elephant.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, elephant.getStatus());
        assertFalse(elephant.isBattleReady());
        assertEquals(0, elephant.getRiderCount());
        assertEquals("The war elephant is not battle-ready.", elephant.charge());
    }

    @Test
    void parameterizedConstructorShouldInitializeOperationalWarElephant() {
        WarElephant elephant = new WarElephant(
                "Royal War Elephant",
                "Elite battlefield elephant");

        assertEquals("Royal War Elephant", elephant.getName());
        assertEquals("Elite battlefield elephant", elephant.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, elephant.getStatus());
        assertFalse(elephant.isBattleReady());
    }

    @Test
    void identitiesShouldBeUnique() {
        WarElephant first = new WarElephant();
        WarElephant second = new WarElephant();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void assignRiderShouldAddRiderAndMakeBattleReady() {
        WarElephant elephant = new WarElephant();

        elephant.assignRider("Arjun");

        assertTrue(elephant.isBattleReady());
        assertEquals(1, elephant.getRiderCount());
    }

    @Test
    void duplicateRidersShouldBeIgnored() {
        WarElephant elephant = new WarElephant();

        elephant.assignRider("Arjun");
        elephant.assignRider("Arjun");

        assertEquals(1, elephant.getRiderCount());
    }

    @Test
    void invalidRidersShouldBeIgnored() {
        WarElephant elephant = new WarElephant();

        elephant.assignRider(null);
        elephant.assignRider("");
        elephant.assignRider("   ");

        assertFalse(elephant.isBattleReady());
        assertEquals(0, elephant.getRiderCount());
    }

    @Test
    void riderNamesShouldBeTrimmed() {
        WarElephant elephant = new WarElephant();

        elephant.assignRider("   Arjun   ");

        assertEquals(1, elephant.getRiderCount());
        assertEquals(
                "The war elephant charges into enemy lines with 1 rider(s)!",
                elephant.charge()
        );
    }

    @Test
    void chargeShouldReturnBattleMessageWhenReady() {
        WarElephant elephant = new WarElephant();

        elephant.assignRider("Arjun");
        elephant.assignRider("Bhima");

        assertEquals(
                "The war elephant charges into enemy lines with 2 rider(s)!",
                elephant.charge()
        );
    }

    @Test
    void chargeShouldReturnNotReadyMessageWhenNoRidersAssigned() {
        WarElephant elephant = new WarElephant();

        assertEquals(
                "The war elephant is not battle-ready.",
                elephant.charge()
        );
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        WarElephant original = new WarElephant(
                "Royal War Elephant",
                "Elite battlefield elephant");

        original.assignRider("Arjun");
        original.assignRider("Bhima");

        String json = mapper.writeValueAsString(original);

        WarElephant restored =
                mapper.readValue(json, WarElephant.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.isBattleReady(), restored.isBattleReady());
        assertEquals(original.getRiderCount(), restored.getRiderCount());
        assertEquals(original.charge(), restored.charge());
    }
}