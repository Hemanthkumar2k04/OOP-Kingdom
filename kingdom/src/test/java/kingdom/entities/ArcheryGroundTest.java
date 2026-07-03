package kingdom.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ArcheryGroundTest {

    private ArcheryGround ground;

    @BeforeEach
    void setup() {
        ground = new ArcheryGround();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructor_shouldHaveUnderConstructionStatus() {
        assertEquals(ArcheryGround.Status.UNDER_CONSTRUCTION, ground.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructor_shouldHaveIdentity() {
        assertNotNull(ground.getIdentity());
        assertTrue(ground.getIdentity().startsWith("ARCHERYGROUND-"));
    }

    @Test
    @DisplayName("trainArcher should increase archer count")
    void trainArcher_shouldIncreaseCount() {
        ground.trainArcher("Robin");
        assertEquals(1, ground.getArcherCount());
    }

    @Test
    @DisplayName("holdPractice with no archers should return zero")
    void holdPractice_noArchers_shouldReturnZero() {
        assertEquals(0.0, ground.holdPractice());
    }

    @Test
    @DisplayName("holdPractice should return valid accuracy")
    void holdPractice_shouldReturnValidAccuracy() {
        ground.trainArcher("Robin");

        double accuracy = ground.holdPractice();

        assertTrue(accuracy >= 0.0);
        assertTrue(accuracy <= 100.0);
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

        ground.trainArcher("Robin");

        String json = mapper.writeValueAsString(ground);
        ArcheryGround deserialized = mapper.readValue(json, ArcheryGround.class);

        assertEquals(ground.getIdentity(), deserialized.getIdentity());
        assertEquals(ground.getArcherCount(), deserialized.getArcherCount());
    }
}