package kingdom.entities;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
public class ExplosiveArenaTest {
    private ExplosiveArena arena;
    @BeforeEach
    void setup() {
        arena = new ExplosiveArena();
    }
    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructor_shouldHaveUnderConstructionStatus() {
        assertEquals(ExplosiveArena.Status.UNDER_CONSTRUCTION, arena.getStatus());
    }
    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructor_shouldHaveIdentity() {
        assertNotNull(arena.getIdentity());
        assertTrue(arena.getIdentity().startsWith("EXPLOSIVEARENA-"));
    }
    @Test
    @DisplayName("trainDemolitionist should increase demolitionist count")
    void trainDemolitionist_shouldIncreaseCount() {
        arena.trainDemolitionist("Wade");
        assertEquals(1, arena.getDemolitionistCount());
    }
    @Test
    @DisplayName("trainDemolitionist should reject blank names")
    void trainDemolitionist_blankName_shouldThrow() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> arena.trainDemolitionist("  ")
        );
    }
    @Test
    @DisplayName("prepareExplosive with no demolitionists should ready one explosive")
    void prepareExplosive_noDemolitionists_shouldReadyOne() {
        assertEquals(1, arena.prepareExplosive());
    }
    @Test
    @DisplayName("prepareExplosive should scale with demolitionist count and accumulate")
    void prepareExplosive_shouldScaleAndAccumulate() {
        arena.trainDemolitionist("Wade");
        arena.trainDemolitionist("Grace");
        int firstBatch = arena.prepareExplosive();
        assertEquals(3, firstBatch);
        int secondBatch = arena.prepareExplosive();
        assertEquals(6, secondBatch);
        assertEquals(6, arena.getExplosivesReady());
    }
    @Test
    @DisplayName("Should serialize and deserialize correctly")
    void serialization_shouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE
        );
        arena.trainDemolitionist("Wade");
        arena.prepareExplosive();
        String json = mapper.writeValueAsString(arena);
        ExplosiveArena deserialized = mapper.readValue(json, ExplosiveArena.class);
        assertEquals(arena.getIdentity(), deserialized.getIdentity());
        assertEquals(arena.getDemolitionistCount(), deserialized.getDemolitionistCount());
        assertEquals(arena.getExplosivesReady(), deserialized.getExplosivesReady());
    }
}