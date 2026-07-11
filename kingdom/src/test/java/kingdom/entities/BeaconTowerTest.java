package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeaconTowerTest {

    @Test
    void defaultConstructorShouldInitializeBeaconTower() {
        BeaconTower tower = new BeaconTower();

        assertNotNull(tower.getIdentity());
        assertTrue(tower.getIdentity().startsWith("BEACONTOWER-"));
        assertEquals("Beacon Tower", tower.getName());
        assertNotNull(tower.getDescription());
        assertNotNull(tower.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, tower.getStatus());
        assertFalse(tower.isLit());
        assertEquals(0, tower.getSignalCount());
        assertEquals("", tower.getLastSignal());
    }

    @Test
    void parameterizedConstructorShouldInitializeOperationalTower() {
        BeaconTower tower = new BeaconTower(
                "Northern Beacon",
                "Signals northern defenses");

        assertEquals("Northern Beacon", tower.getName());
        assertEquals("Signals northern defenses", tower.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, tower.getStatus());
        assertFalse(tower.isLit());
    }

    @Test
    void identitiesShouldBeUnique() {
        BeaconTower first = new BeaconTower();
        BeaconTower second = new BeaconTower();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void lightBeaconShouldStoreSignalAndLightTower() {
        BeaconTower tower = new BeaconTower();

        tower.lightBeacon("advance");

        assertTrue(tower.isLit());
        assertEquals(1, tower.getSignalCount());
        assertEquals("advance", tower.getLastSignal());
    }

    @Test
    void latestSignalShouldReturnMostRecentSignal() {
        BeaconTower tower = new BeaconTower();

        tower.lightBeacon("advance");
        tower.lightBeacon("reinforcements");

        assertEquals(2, tower.getSignalCount());
        assertEquals("reinforcements", tower.getLastSignal());
    }

    @Test
    void invalidSignalsShouldBeIgnored() {
        BeaconTower tower = new BeaconTower();

        tower.lightBeacon(null);
        tower.lightBeacon("");
        tower.lightBeacon("   ");

        assertFalse(tower.isLit());
        assertEquals(0, tower.getSignalCount());
        assertEquals("", tower.getLastSignal());
    }

    @Test
    void signalShouldBeTrimmedBeforeStorage() {
        BeaconTower tower = new BeaconTower();

        tower.lightBeacon("   retreat   ");

        assertEquals("retreat", tower.getLastSignal());
        assertEquals(1, tower.getSignalCount());
    }

    @Test
    void beaconShouldRemainLitAfterMultipleSignals() {
        BeaconTower tower = new BeaconTower();

        tower.lightBeacon("advance");
        tower.lightBeacon("victory");

        assertTrue(tower.isLit());
        assertEquals(2, tower.getSignalCount());
        assertEquals("victory", tower.getLastSignal());
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        BeaconTower original = new BeaconTower(
                "Western Beacon",
                "Signals western defenses");

        original.lightBeacon("advance");
        original.lightBeacon("victory");

        String json = mapper.writeValueAsString(original);

        BeaconTower restored =
                mapper.readValue(json, BeaconTower.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.isLit(), restored.isLit());
        assertEquals(original.getSignalCount(), restored.getSignalCount());
        assertEquals(original.getLastSignal(), restored.getLastSignal());
    }
}