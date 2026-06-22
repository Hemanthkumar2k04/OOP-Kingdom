package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TownCrierTest {

    private TownCrier townCrier;

    @BeforeEach
    void setup() {
        townCrier = new TownCrier();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructor_shouldHaveUnderConstructionStatus() {
        assertEquals(TownCrier.Status.UNDER_CONSTRUCTION, townCrier.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructor_shouldHaveNonNullIdentity() {
        assertNotNull(townCrier.getIdentity());
        assertTrue(townCrier.getIdentity().startsWith("TOWNCRIER-"));
    }

    @Test
    @DisplayName("announce should increase announcement count and set lastAnnouncement")
    void announce_shouldIncreaseCountAndSetLast() {
        townCrier.announce("Hear ye, hear ye!");
        assertEquals(1, townCrier.getAnnouncementCount());
        assertEquals("Hear ye, hear ye!", townCrier.getLastAnnouncement());
    }

    @Test
    @DisplayName("Multiple announcements should accumulate count")
    void multipleAnnouncements_shouldAccumulate() {
        townCrier.announce("First");
        townCrier.announce("Second");
        assertEquals(2, townCrier.getAnnouncementCount());
        assertEquals("Second", townCrier.getLastAnnouncement());
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

        townCrier.announce("Royal decree");
        String json = mapper.writeValueAsString(townCrier);
        TownCrier deserialized = mapper.readValue(json, TownCrier.class);

        assertEquals(townCrier.getAnnouncementCount(), deserialized.getAnnouncementCount());
        assertEquals(townCrier.getLastAnnouncement(), deserialized.getLastAnnouncement());
        assertEquals(townCrier.getIdentity(), deserialized.getIdentity());
    }
}
