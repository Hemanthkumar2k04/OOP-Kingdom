package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TownCrierTest {

    @Test
    void defaultConstructorShouldInitializeTownCrier() {
        TownCrier townCrier = new TownCrier();

        assertNotNull(townCrier.getIdentity());
        assertTrue(townCrier.getIdentity().startsWith("TOWNCRIER-"));
        assertEquals("Town Crier", townCrier.getName());
        assertNotNull(townCrier.getDescription());
        assertNotNull(townCrier.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, townCrier.getStatus());
        assertEquals(0, townCrier.getAnnouncementCount());
        assertEquals("", townCrier.getLatestAnnouncement());
    }

    @Test
    void parameterizedConstructorShouldInitializeOperationalTownCrier() {
        TownCrier townCrier = new TownCrier(
                "Royal Town Crier",
                "Official messenger of the kingdom");

        assertEquals("Royal Town Crier", townCrier.getName());
        assertEquals("Official messenger of the kingdom", townCrier.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, townCrier.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        TownCrier first = new TownCrier();
        TownCrier second = new TownCrier();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void announceShouldIncreaseAnnouncementCount() {
        TownCrier townCrier = new TownCrier();

        townCrier.announce("Festival begins tomorrow");

        assertEquals(1, townCrier.getAnnouncementCount());
    }

    @Test
    void latestAnnouncementShouldReturnMostRecentMessage() {
        TownCrier townCrier = new TownCrier();

        townCrier.announce("Market opens at dawn");
        townCrier.announce("Festival begins tomorrow");

        assertEquals("Festival begins tomorrow", townCrier.getLatestAnnouncement());
    }

    @Test
    void duplicateAnnouncementsShouldBeAllowed() {
        TownCrier townCrier = new TownCrier();

        townCrier.announce("Hear ye!");
        townCrier.announce("Hear ye!");

        assertEquals(2, townCrier.getAnnouncementCount());
        assertEquals("Hear ye!", townCrier.getLatestAnnouncement());
    }

    @Test
    void invalidAnnouncementsShouldBeIgnored() {
        TownCrier townCrier = new TownCrier();

        townCrier.announce(null);
        townCrier.announce("");
        townCrier.announce("   ");

        assertEquals(0, townCrier.getAnnouncementCount());
        assertEquals("", townCrier.getLatestAnnouncement());
    }

    @Test
    void announcementShouldBeTrimmed() {
        TownCrier townCrier = new TownCrier();

        townCrier.announce("   Royal Decree   ");

        assertEquals("Royal Decree", townCrier.getLatestAnnouncement());
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        TownCrier original = new TownCrier(
                "Royal Town Crier",
                "Official messenger of the kingdom");

        original.announce("Market opens at dawn");
        original.announce("Festival begins tomorrow");

        String json = mapper.writeValueAsString(original);

        TownCrier restored = mapper.readValue(json, TownCrier.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(original.getLatestAnnouncement(), restored.getLatestAnnouncement());
    }
}