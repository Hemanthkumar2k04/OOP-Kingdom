package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BarracksTest {

    private Barracks barracks;

    @BeforeEach
    void setup() {
        barracks = new Barracks();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructor_shouldHaveUnderConstructionStatus() {
        assertEquals(Barracks.Status.UNDER_CONSTRUCTION, barracks.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructor_shouldHaveNonNullIdentity() {
        assertNotNull(barracks.getIdentity());
        assertTrue(barracks.getIdentity().startsWith("BARRACKS-"));
    }

    @Test
    @DisplayName("No-arg constructor should start with an empty troop list")
    void noArgConstructor_shouldStartAnEmptyTroopList() {
        assertTrue(barracks.getTroops().isEmpty());
    }

    @Test
    @DisplayName("Parameterized constructor should have OPERATIONAL status")
    void parameterizedConstructor_shouldHaveOperationalStatus() {
        Barracks operational = new Barracks("Royal Barracks", "The main military training facility of the kingdom.");
        assertEquals(Barracks.Status.OPERATIONAL, operational.getStatus());
    }

    @Test
    @DisplayName("trainTroop should add troop to list")
    void trainTroop_shouldAddTroopToList() {
        barracks.trainTroop("Archer");
        assertEquals(1, barracks.getTroops().size());
        assertTrue(barracks.getTroops().contains("Archer"));
    }

    @Test
    @DisplayName("Multiple trainTroop calls should accumulate troops")
    void trainTroop_multipleTroops_shouldAccumulate() {
        barracks.trainTroop("Knight");
        barracks.trainTroop("Hunter");
        assertEquals(2, barracks.getTroops().size());
        assertTrue(barracks.getTroops().containsAll(List.of("Knight", "Hunter")));
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

        barracks.trainTroop("Wizard");
        String json = mapper.writeValueAsString(barracks);
        Barracks deserialized = mapper.readValue(json, Barracks.class);

        assertEquals(barracks.getTroops(), deserialized.getTroops());
        assertEquals(barracks.getIdentity(), deserialized.getIdentity());
    }
}
