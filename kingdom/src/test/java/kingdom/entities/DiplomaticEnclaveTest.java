package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiplomaticEnclaveTest {

    @Test
    void defaultConstructorShouldInitializeDiplomaticEnclave() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        assertNotNull(enclave.getIdentity());
        assertTrue(enclave.getIdentity().startsWith("DIPLOMATICENCLAVE-"));
        assertEquals("Diplomatic Enclave", enclave.getName());
        assertNotNull(enclave.getDescription());
        assertNotNull(enclave.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, enclave.getStatus());
        assertEquals(0, enclave.getPendingMessageCount());
        assertEquals("", enclave.readLatestMessage());
    }

    @Test
    void parameterizedConstructorShouldInitializeOperationalEnclave() {
        DiplomaticEnclave enclave = new DiplomaticEnclave(
                "Royal Embassy",
                "Handles diplomacy between kingdoms");

        assertEquals("Royal Embassy", enclave.getName());
        assertEquals("Handles diplomacy between kingdoms", enclave.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, enclave.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        DiplomaticEnclave first = new DiplomaticEnclave();
        DiplomaticEnclave second = new DiplomaticEnclave();

        assertNotEquals(first.getIdentity(), second.getIdentity());
    }

    @Test
    void sendEnvoyShouldStoreMessage() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        enclave.sendEnvoy("Avalon", "Peace treaty accepted");

        assertEquals(1, enclave.getPendingMessageCount());
        assertEquals(
                "Avalon: Peace treaty accepted",
                enclave.readLatestMessage()
        );
    }

    @Test
    void latestMessageShouldReturnMostRecentMessage() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        enclave.sendEnvoy("Avalon", "Peace treaty accepted");
        enclave.sendEnvoy("Eldoria", "Trade agreement signed");

        assertEquals(2, enclave.getPendingMessageCount());
        assertEquals(
                "Eldoria: Trade agreement signed",
                enclave.readLatestMessage()
        );
    }

    @Test
    void invalidMessagesShouldBeIgnored() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        enclave.sendEnvoy(null, "Message");
        enclave.sendEnvoy("", "Message");
        enclave.sendEnvoy("   ", "Message");
        enclave.sendEnvoy("Avalon", null);
        enclave.sendEnvoy("Avalon", "");
        enclave.sendEnvoy("Avalon", "   ");

        assertEquals(0, enclave.getPendingMessageCount());
        assertEquals("", enclave.readLatestMessage());
    }

    @Test
    void envoyShouldTrimInputs() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        enclave.sendEnvoy(
                "  Avalon  ",
                "  Peace treaty accepted  "
        );

        assertEquals(
                "Avalon: Peace treaty accepted",
                enclave.readLatestMessage()
        );
    }

    @Test
    void forgeAllianceShouldReturnTrueForNewAlliance() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        assertTrue(enclave.forgeAlliance("Avalon"));
    }

    @Test
    void forgeAllianceShouldReturnFalseForDuplicateAlliance() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        assertTrue(enclave.forgeAlliance("Avalon"));
        assertFalse(enclave.forgeAlliance("Avalon"));
    }

    @Test
    void forgeAllianceShouldRejectInvalidKingdoms() {
        DiplomaticEnclave enclave = new DiplomaticEnclave();

        assertFalse(enclave.forgeAlliance(null));
        assertFalse(enclave.forgeAlliance(""));
        assertFalse(enclave.forgeAlliance("   "));
    }

    @Test
    void jacksonSerializationShouldPreserveState() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        DiplomaticEnclave original = new DiplomaticEnclave(
                "Royal Embassy",
                "Handles diplomacy between kingdoms");

        original.sendEnvoy("Avalon", "Peace treaty accepted");
        original.sendEnvoy("Eldoria", "Trade agreement signed");
        original.forgeAlliance("Avalon");

        String json = mapper.writeValueAsString(original);

        DiplomaticEnclave restored =
                mapper.readValue(json, DiplomaticEnclave.class);

        assertEquals(original.getIdentity(), restored.getIdentity());
        assertEquals(original.getName(), restored.getName());
        assertEquals(original.getDescription(), restored.getDescription());
        assertEquals(original.getStatus(), restored.getStatus());
        assertEquals(
                original.readLatestMessage(),
                restored.readLatestMessage()
        );
    }
}