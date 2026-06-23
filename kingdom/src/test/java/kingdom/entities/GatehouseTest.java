package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GatehouseTest {

    private Gatehouse gatehouse;

    @BeforeEach
    void setup() {
        gatehouse = new Gatehouse();
    }

    @Test
    @DisplayName("No-arg constructor should have UNDER_CONSTRUCTION status")
    void noArgConstructorUnderConstructionStatus() {
        assertEquals(Gatehouse.Status.UNDER_CONSTRUCTION, gatehouse.getStatus());
    }

    @Test
    @DisplayName("No-arg constructor should have non-null identity")
    void noArgConstructorDefaultValues() {
        assertNotNull(gatehouse.getIdentity());
        assertTrue(gatehouse.getIdentity().startsWith("GATEHOUSE-"));
        assertEquals("Gatehouse", gatehouse.getName());
        assertNotNull(gatehouse.getDescription());
        assertNotNull(gatehouse.getFoundingDate());
    }

    @Test
    @DisplayName("Parameterized constructor should have OPERATIONAL status")
    void parameterizedConstructorOperationalStatus() {
        Gatehouse operational = new Gatehouse("GATEHOUSE-001", "Main Gate");
        assertEquals(Gatehouse.Status.OPERATIONAL, operational.getStatus());
    }

    @Test
    @DisplayName("Admit visitor should add visitor and increase total")
    void admitVisitorAddsAndCounts() {
        gatehouse.admitVisitor("Alice");
        assertTrue(gatehouse.getCurrentVisitors().contains("Alice"));
        assertEquals(1, gatehouse.getTotalVisitors());
    }

    @Test
    @DisplayName("Depart visitor should remove visitor from current list")
    void departVisitorRemoves() {
        gatehouse.admitVisitor("Bob");
        gatehouse.departVisitor("Bob");
        assertFalse(gatehouse.getCurrentVisitors().contains("Bob"));
    }

    @Test
    @DisplayName("Departing non-present visitor should throw IllegalStateException")
    void departNonPresentThrows() {
        assertThrows(IllegalStateException.class, () -> gatehouse.departVisitor("NoOne"));
    }

    @Test
    @DisplayName("Admit null or empty visitor should throw IllegalArgumentException")
    void admitNullOrEmptyThrows() {
        assertThrows(IllegalArgumentException.class, () -> gatehouse.admitVisitor(null));
        assertThrows(IllegalArgumentException.class, () -> gatehouse.admitVisitor(""));
    }

    @Test
    @DisplayName("getCurrentVisitors should return UnmodifiableList")
    void getCurrentVisitorsUnmodifiable() {
        List<String> visitors = gatehouse.getCurrentVisitors();
        assertThrows(UnsupportedOperationException.class, () -> visitors.add("intruder"));
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

        gatehouse.admitVisitor("Carol");
        String json = mapper.writeValueAsString(gatehouse);
        Gatehouse deserialized = mapper.readValue(json, Gatehouse.class);

        assertEquals(gatehouse.getIdentity(), deserialized.getIdentity());
        assertEquals(gatehouse.getName(), deserialized.getName());
        assertEquals(gatehouse.getStatus(), deserialized.getStatus());
        assertEquals(gatehouse.getCurrentVisitors(), deserialized.getCurrentVisitors());
        assertEquals(gatehouse.getTotalVisitors(), deserialized.getTotalVisitors());
    }
}
