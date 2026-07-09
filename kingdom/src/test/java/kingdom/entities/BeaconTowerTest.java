package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeaconTowerTest {

    @Test
    void defaultConstructorShouldInitializeBeaconTower() {
        BeaconTower beaconTower = new BeaconTower();

        assertNotNull(beaconTower.getIdentity());
        assertTrue(beaconTower.getIdentity().startsWith("BEACONTOWER-"));
        assertEquals("Beacon Tower", beaconTower.getName());
        assertNotNull(beaconTower.getDescription());
        assertNotNull(beaconTower.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, beaconTower.getStatus());
        assertFalse(beaconTower.isLit());
        assertEquals("", beaconTower.getLastSignal());
        assertEquals(0, beaconTower.getSignalCount());
    }

    @Test
    void parameterizedConstructorShouldSetValues() {
        BeaconTower beaconTower =
                new BeaconTower(
                        "Royal Beacon Tower",
                        "Strategic tower for military communications");

        assertEquals("Royal Beacon Tower", beaconTower.getName());
        assertEquals("Strategic tower for military communications", beaconTower.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, beaconTower.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        BeaconTower first = new BeaconTower();
        BeaconTower second = new BeaconTower();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void lightBeaconShouldSetLitStatus() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("advance");

        assertTrue(beaconTower.isLit());
    }

    @Test
    void lightBeaconShouldRecordLastSignal() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("retreat");

        assertEquals("retreat", beaconTower.getLastSignal());
    }

    @Test
    void lightBeaconShouldIncreaseSignalCount() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("advance");

        assertEquals(1, beaconTower.getSignalCount());
    }

    @Test
    void multipleSignalsShouldAccumulateCount() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("advance");
        beaconTower.lightBeacon("reinforcements");
        beaconTower.lightBeacon("victory");

        assertEquals(3, beaconTower.getSignalCount());
        assertEquals("victory", beaconTower.getLastSignal());
        assertTrue(beaconTower.isLit());
    }

    @Test
    void getLastSignalShouldReturnEmptyStringWhenNoSignalSent() {
        BeaconTower beaconTower = new BeaconTower();

        assertEquals("", beaconTower.getLastSignal());
    }

    @Test
    void isLitShouldReturnFalseInitially() {
        BeaconTower beaconTower = new BeaconTower();

        assertFalse(beaconTower.isLit());
    }

    @Test
    void extinguishShouldSetLitToFalse() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("advance");
        assertTrue(beaconTower.isLit());

        beaconTower.extinguish();
        assertFalse(beaconTower.isLit());
    }

    @Test
    void extinguishShouldNotAffectSignalCount() {
        BeaconTower beaconTower = new BeaconTower();

        beaconTower.lightBeacon("advance");
        beaconTower.lightBeacon("reinforcements");
        assertEquals(2, beaconTower.getSignalCount());

        beaconTower.extinguish();
        assertEquals(2, beaconTower.getSignalCount());
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        BeaconTower original =
                new BeaconTower(
                        "Royal Beacon Tower",
                        "Strategic tower for military communications");

        original.lightBeacon("advance");
        original.lightBeacon("reinforcements");

        String json = mapper.writeValueAsString(original);

        BeaconTower restored =
                mapper.readValue(json, BeaconTower.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.isLit(), restored.isLit());
        assertEquals(original.getLastSignal(), restored.getLastSignal());
        assertEquals(original.getSignalCount(), restored.getSignalCount());
        assertEquals(original.getStatus(), restored.getStatus());
    }
}