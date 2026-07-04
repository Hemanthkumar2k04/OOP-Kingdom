package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExplosiveArenaTest {

    private ExplosiveArena explosiveArena;

    @BeforeEach
    void setup() {
        explosiveArena = new ExplosiveArena();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructorUnderConstructionStatus() {
        assertEquals(ExplosiveArena.Status.UNDER_CONSTRUCTION, explosiveArena.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructorDefaultValues() {
        assertNotNull(explosiveArena.getIdentity());
        assertTrue(explosiveArena.getIdentity().startsWith("EXPLOSIVEARENA-"));
        assertEquals("Explosive Arena", explosiveArena.getName());
        assertNotNull(explosiveArena.getDescription());
        assertNotNull(explosiveArena.getFoundingDate());
    }

    @Test
    @DisplayName("No-arg constructor should initialize empty demolitionists list")
    void noArgConstructorEmptyDemolitionistsList() {
        assertEquals(0, explosiveArena.getDemolitionistCount());
        assertTrue(explosiveArena.getDemolitionists().isEmpty());
    }

    @Test
    @DisplayName("No-arg constructor should initialize explosives ready to zero")
    void noArgConstructorZeroExplosivesReady() {
        assertEquals(0, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("Parameterized constructor should have OPERATIONAL status")
    void parameterizedConstructorOperationalStatus() {
        ExplosiveArena operational = new ExplosiveArena("Siege Explosives Arena", 15, 150);
        assertEquals(ExplosiveArena.Status.OPERATIONAL, operational.getStatus());
    }

    @Test
    @DisplayName("Parameterized constructor should set correct values")
    void parameterizedConstructorCorrectValues() {
        ExplosiveArena operational = new ExplosiveArena("Kingdom Explosion Center", 20, 200);
        assertEquals("Kingdom Explosion Center", operational.getName());
        assertEquals(20, operational.getPreparationRate());
        assertEquals(200, operational.getMaxExplosiveCapacity());
    }

    @Test
    @DisplayName("trainDemolitionist should add demolitionist to list")
    void trainDemolitionistAddsToList() {
        explosiveArena.trainDemolitionist("Sir Blast");
        assertEquals(1, explosiveArena.getDemolitionistCount());
        assertTrue(explosiveArena.getDemolitionists().contains("Sir Blast"));
    }

    @Test
    @DisplayName("trainDemolitionist with multiple names")
    void trainMultipleDemolitionists() {
        explosiveArena.trainDemolitionist("Pyro Pete");
        explosiveArena.trainDemolitionist("TNT Tom");
        explosiveArena.trainDemolitionist("Boom Bob");

        assertEquals(3, explosiveArena.getDemolitionistCount());
        assertTrue(explosiveArena.getDemolitionists().contains("Pyro Pete"));
        assertTrue(explosiveArena.getDemolitionists().contains("TNT Tom"));
        assertTrue(explosiveArena.getDemolitionists().contains("Boom Bob"));
    }

    @Test
    @DisplayName("trainDemolitionist should trim whitespace")
    void trainDemolitionistTrimsWhitespace() {
        explosiveArena.trainDemolitionist("  Blast Master  ");
        assertEquals(1, explosiveArena.getDemolitionistCount());
        assertTrue(explosiveArena.getDemolitionists().contains("Blast Master"));
    }

    @Test
    @DisplayName("trainDemolitionist with null should throw IllegalArgumentException")
    void trainDemolitionistWithNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> explosiveArena.trainDemolitionist(null));
    }

    @Test
    @DisplayName("trainDemolitionist with empty string should throw IllegalArgumentException")
    void trainDemolitionistWithEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> explosiveArena.trainDemolitionist(""));
    }

    @Test
    @DisplayName("trainDemolitionist with whitespace only should throw IllegalArgumentException")
    void trainDemolitionistWithWhitespaceOnlyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> explosiveArena.trainDemolitionist("   "));
    }

    @Test
    @DisplayName("getDemolitionistCount should return correct count")
    void getDemolitionistCountCorrect() {
        assertEquals(0, explosiveArena.getDemolitionistCount());

        explosiveArena.trainDemolitionist("Demolitionist 1");
        assertEquals(1, explosiveArena.getDemolitionistCount());

        explosiveArena.trainDemolitionist("Demolitionist 2");
        assertEquals(2, explosiveArena.getDemolitionistCount());
    }

    @Test
    @DisplayName("getDemolitionists should return UnmodifiableList")
    void getDemolitionistsShouldReturnUnmodifiableList() {
        explosiveArena.trainDemolitionist("Test Demolitionist");
        List<String> demolitionists = explosiveArena.getDemolitionists();
        assertThrows(UnsupportedOperationException.class, () -> demolitionists.add("Hacker"));
    }

    @Test
    @DisplayName("prepareExplosive should increase explosives ready")
    void prepareExplosiveIncreasesExplosivesReady() {
        int beforePreparation = explosiveArena.getExplosivesReady();
        explosiveArena.prepareExplosive();
        assertEquals(beforePreparation + 10, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("prepareExplosive multiple times should accumulate")
    void prepareExplosiveMultipleTimes() {
        explosiveArena.prepareExplosive();
        explosiveArena.prepareExplosive();
        explosiveArena.prepareExplosive();
        assertEquals(30, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("prepareExplosive should not exceed max capacity")
    void prepareExplosiveRespectMaxCapacity() {
        ExplosiveArena arena = new ExplosiveArena("Test Arena", 30, 50);
        arena.prepareExplosive(); // 30 explosives
        arena.prepareExplosive(); // would be 60, but capped at 50
        assertEquals(50, arena.getExplosivesReady());
    }

    @Test
    @DisplayName("prepareExplosive when DAMAGED should be less efficient")
    void prepareExplosiveWhenDamagedLessEfficient() {
        explosiveArena.setStatus(ExplosiveArena.Status.DAMAGED);
        explosiveArena.prepareExplosive();
        // Damaged arena prepares 50% less (10 / 2 = 5)
        assertEquals(5, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("prepareExplosive when DAMAGED returns correct number")
    void prepareExplosiveWhenDamagedReturnsCorrectNumber() {
        explosiveArena.setStatus(ExplosiveArena.Status.DAMAGED);
        int result = explosiveArena.prepareExplosive();
        assertEquals(5, result);
    }

    @Test
    @DisplayName("getExplosivesReady should return current count")
    void getExplosivesReadyCorrect() {
        assertEquals(0, explosiveArena.getExplosivesReady());
        explosiveArena.prepareExplosive();
        assertEquals(10, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("setExplosivesReady should update explosives count")
    void setExplosivesReadyUpdatesCount() {
        explosiveArena.setExplosivesReady(25);
        assertEquals(25, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("setExplosivesReady should respect max capacity")
    void setExplosivesReadyRespectsMaxCapacity() {
        explosiveArena.setExplosivesReady(150);
        assertEquals(100, explosiveArena.getExplosivesReady());
    }

    @Test
    @DisplayName("prepareExplosive should return the number of explosives ready after preparation")
    void prepareExplosiveReturnsCorrectCount() {
        int result = explosiveArena.prepareExplosive();
        assertEquals(10, result);
        assertEquals(explosiveArena.getExplosivesReady(), result);
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

        explosiveArena.trainDemolitionist("Blast Expert");
        explosiveArena.trainDemolitionist("Explosive Specialist");
        explosiveArena.prepareExplosive();
        explosiveArena.setStatus(ExplosiveArena.Status.OPERATIONAL);

        String json = mapper.writeValueAsString(explosiveArena);
        ExplosiveArena deserialized = mapper.readValue(json, ExplosiveArena.class);

        assertEquals(explosiveArena.getIdentity(), deserialized.getIdentity());
        assertEquals(explosiveArena.getName(), deserialized.getName());
        assertEquals(explosiveArena.getStatus(), deserialized.getStatus());
        assertEquals(explosiveArena.getDemolitionistCount(), deserialized.getDemolitionistCount());
        assertEquals(explosiveArena.getDemolitionists(), deserialized.getDemolitionists());
        assertEquals(explosiveArena.getExplosivesReady(), deserialized.getExplosivesReady());
        assertEquals(explosiveArena.getPreparationRate(), deserialized.getPreparationRate());
        assertEquals(explosiveArena.getMaxExplosiveCapacity(), deserialized.getMaxExplosiveCapacity());
    }

    @Test
    @DisplayName("Jackson deserialized object should function correctly")
    void serialization_deserializedObjectShouldFunctionCorrectly() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE
        );

        explosiveArena.trainDemolitionist("Original Demolitionist");
        explosiveArena.prepareExplosive();

        String json = mapper.writeValueAsString(explosiveArena);
        ExplosiveArena deserialized = mapper.readValue(json, ExplosiveArena.class);

        // Test that the deserialized object has the correct state
        assertEquals(1, deserialized.getDemolitionistCount());
        assertEquals(10, deserialized.getExplosivesReady());
        assertTrue(deserialized.getDemolitionists().contains("Original Demolitionist"));
    }
}
