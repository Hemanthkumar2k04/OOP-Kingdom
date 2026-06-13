package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BarracksTest {

    private Barracks barracks;
    private Lumberyard lumberyard;


    @BeforeEach
    void setup() {
        barracks = new Barracks();
        lumberyard = new Lumberyard();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructorUnderConstructionStatus() {
        assertEquals(Barracks.Status.UNDER_CONSTRUCTION, barracks.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructorDefaultValues() {
        assertNotNull(barracks.getIdentity());
        assertTrue(barracks.getIdentity().startsWith("BARRACKS-"));
        assertEquals("Barracks", barracks.getName());
        assertNotNull(barracks.getDescription());
        assertNotNull(barracks.getFoundingDate());
    }


    @Test
    @DisplayName("Parameterized constructor should have OPERATIONAL status")
    void parameterizedConstructorOperationalStatus() {
        Barracks operational = new Barracks("Omega OOP Kingdom Barrack", 2, 10, 100);
        assertEquals(Barracks.Status.OPERATIONAL, operational.getStatus());
    }

    @Test
    @DisplayName("Train Archer Troop should add troop and equipments")
    void trainArcherAddTroopAndEquipment() {
        barracks.trainTroop("archer");

        assertEquals(1, barracks.getEquipments().get("bow"));
        assertTrue(barracks.getTroops().contains("archer"));
    }

    @Test
    @DisplayName("trainTroop should be case insensitive")
    void trainTroopCaseInsensitive() {
        assertDoesNotThrow(() -> barracks.trainTroop("ARCHER"));
    }

    @Test
    @DisplayName("Train invalid troop should throw illegal Argument Exception")
    void trainInvalidTroop() {
        assertThrows(IllegalArgumentException.class, () -> barracks.trainTroop("dragon"));
    }

    @Test
    @DisplayName("Train troop when at full capacity should throw Illegal State Exception")
    void fullCapacityTrainTroop() {
        for(int i = 0; i < 10; i++)
        {
            barracks.trainTroop("goblin");
        }

        assertThrows(IllegalStateException.class, () -> barracks.trainTroop("goblin"));
    }

    @Test
    @DisplayName("Train Knight Special Troop should add troop and equipments")
    void TrainSpecialTroopsKightAddTroopAndEquipment() {
        barracks.trainSpecialTroops("knight");

        assertTrue(barracks.getSpecialTroops().contains("knight"));
        assertEquals(1, barracks.getEquipments().get("long sword"));
    }

    @Test
    @DisplayName("Invalid Special Troop should Throw Illegal Argument Exception")
    void trainInvalidSpecialTroop() {
        assertThrows(IllegalArgumentException.class, () -> barracks.trainSpecialTroops("big troll"));
    }

    @Test
    @DisplayName("Train Special Troop when at full capacity should throw Illegal State Exception")
    void fullCapacityTrainSpecialTroop() {
        barracks.trainSpecialTroops("knight");
        barracks.trainSpecialTroops("wizard");

        assertThrows(IllegalStateException.class, () -> barracks.trainSpecialTroops("knight"));
    }

    @Test
    @DisplayName("Train troop when equipment is at full capacity should throw Illegal State Exception")
    void trainTroopWhenEquipmentFullThrowIllegalStateException() {
        Barracks alpha = new Barracks("Xray Barrack", 2, 10, 1);
        assertThrows(IllegalStateException.class, () -> alpha.trainTroop("archer"));
        assertTrue(alpha.getTroops().isEmpty());
    }

    @Test
    @DisplayName("getTroop should return UnmodifiableList")
    void getTroopShouldReturnUnmodifiableList() {
        List<String> troops = barracks.getTroops();
        assertThrows(UnsupportedOperationException.class, () -> troops.add("spy"));
    }

    @Test
    @DisplayName("getSpecialTroop should return UnmodifiableList")
    void getSpecialTroopShouldReturnUnmodifiableList() {
        List<String> specialTroops = barracks.getSpecialTroops();
        assertThrows(UnsupportedOperationException.class, () -> specialTroops.add("ranger"));
    }

    @Test
    @DisplayName("getEquipments should return UnmodifiableMap")
    void getEquipmentsShouldReturnUnmodifiableMap() {
        Map<String, Integer> equipments = barracks.getEquipments();
        assertThrows(UnsupportedOperationException.class, () -> equipments.put("axe", 1));
    }

    @Test
    @DisplayName("Improve Barracks needs to increase capacities")
    void improveBarracksIncreaseCapacities() {
        lumberyard.setWoodStockpile(500);
        int troopsBefore = barracks.getMaxTroopsCapacity();

        barracks.improveBarracks(lumberyard);

        assertEquals(troopsBefore + 5, barracks.getMaxTroopsCapacity());
    }

    @Test
    @DisplayName("Try to improve Barracks without enough wood should throw Illegal State Exception")
    void ImproveBarracksWithoutEnoughWood() {
        assertThrows(IllegalStateException.class, () -> barracks.improveBarracks(lumberyard));
    }

    @Test
    @DisplayName("Repair when Damaged and enough wood should set Status to Operational")
    void repairWhenDamagedShouldSetStatusOperational() {
        lumberyard.setWoodStockpile(500);
        barracks.setStatus(Barracks.Status.DAMAGED);
        barracks.repair(lumberyard);

        assertEquals(Barracks.Status.OPERATIONAL, barracks.getStatus());
    }

    @Test
    @DisplayName("Repair when not Damaged should Throw Illegal State Exception")
    void repairWhenNotamageIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> barracks.repair(lumberyard));
    }

    @Test
    @DisplayName("Repair when Damage but without enough wood should throw Illegal State Exception")
    void repairWhenDamageButNotEnoughWoodIllegalStateException() {
        barracks.setStatus(Barracks.Status.DAMAGED);
        assertThrows(IllegalStateException.class, () -> barracks.repair(lumberyard));
    }

    @Test
    @DisplayName("Should serialize and deserialize correctly with Jackson")
    void serialization_shouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE
        );

        barracks.trainSpecialTroops("wizard");
        String json = mapper.writeValueAsString(barracks);
        Barracks deserialized = mapper.readValue(json, Barracks.class);

        assertEquals(barracks.getIdentity(), deserialized.getIdentity());
        assertEquals(barracks.getName(), deserialized.getName());
        assertEquals(barracks.getStatus(), deserialized.getStatus());
        assertEquals(barracks.getTroops(), deserialized.getTroops());
        assertEquals(barracks.getSpecialTroops(), deserialized.getSpecialTroops());
        assertEquals(barracks.getEquipments(), deserialized.getEquipments());
    }
}
