package kingdom.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kingdom.core.KingdomRegistry;

class BlacksmithTest {

    private Blacksmith blacksmith;

    @BeforeEach
    void setUp() {
        blacksmith = new Blacksmith();
    }

    // ==================== DEFAULT CONSTRUCTOR TESTS ====================

    @Test
    void testDefaultConstructorInitializesSafeDefaults() {
        assertNotNull(blacksmith.getIdentity());
        assertTrue(blacksmith.getIdentity().startsWith("BLACKSMITH-"));
        assertEquals("Blacksmith", blacksmith.getName());
        assertNotNull(blacksmith.getDescription());
        assertEquals(LocalDate.now(), blacksmith.getFoundingDate());
        assertEquals(Blacksmith.Status.UNDER_CONSTRUCTION, blacksmith.getStatus());
    }

    @Test
    void testDefaultValues() {
        assertEquals(0, blacksmith.getWeaponCount());
        assertEquals(100, blacksmith.getAnvilDurability());
        assertEquals(100, blacksmith.getMaxDurability());
    }

    @Test
    void testUUIDUniqueness() {
        Blacksmith blacksmith1 = new Blacksmith();
        Blacksmith blacksmith2 = new Blacksmith();

        assertNotEquals(
                blacksmith1.getIdentity(),
                blacksmith2.getIdentity());
    }

    // ==================== KINGDOM ENTITY TESTS ====================

    @Test
    void testImplementsKingdomEntity() {
        assertNotNull(blacksmith.getIdentity());
        assertNotNull(blacksmith.getName());
        assertNotNull(blacksmith.getDescription());
        assertNotNull(blacksmith.getFoundingDate());
        assertNotNull(blacksmith.getStatus());
    }

    // ==================== CONTRACT METHOD TESTS ====================

    @Test
    void testForgeWeaponIncrementsCount() {
        blacksmith.forgeWeapon();

        assertEquals(1, blacksmith.getWeaponCount());
    }

    @Test
    void testForgeWeaponReducesDurability() {
        blacksmith.forgeWeapon();

        assertEquals(99, blacksmith.getAnvilDurability());
    }

    @Test
    void testMultipleWeaponsForged() {
        for (int i = 0; i < 10; i++) {
            blacksmith.forgeWeapon();
        }

        assertEquals(10, blacksmith.getWeaponCount());
        assertEquals(90, blacksmith.getAnvilDurability());
    }

    @Test
    void testBlacksmithBecomesDamagedWhenDurabilityReachesZero() {
        for (int i = 0; i < 100; i++) {
            blacksmith.forgeWeapon();
        }

        assertEquals(Blacksmith.Status.DAMAGED, blacksmith.getStatus());
        assertEquals(0, blacksmith.getAnvilDurability());
    }

    @Test
    void testCannotForgeWhenDamaged() {
        for (int i = 0; i < 100; i++) {
            blacksmith.forgeWeapon();
        }

        int weaponCount = blacksmith.getWeaponCount();

        blacksmith.forgeWeapon();

        assertEquals(weaponCount, blacksmith.getWeaponCount());
    }

    @Test
    void testRepairAnvilRestoresOperationalStatus() {
        for (int i = 0; i < 100; i++) {
            blacksmith.forgeWeapon();
        }

        blacksmith.repairAnvil();

        assertEquals(Blacksmith.Status.OPERATIONAL, blacksmith.getStatus());
        assertEquals(100, blacksmith.getAnvilDurability());
    }

    @Test
    void testRepairAllowsForgingAgain() {
        for (int i = 0; i < 100; i++) {
            blacksmith.forgeWeapon();
        }

        blacksmith.repairAnvil();
        blacksmith.forgeWeapon();

        assertEquals(101, blacksmith.getWeaponCount());
        assertEquals(99, blacksmith.getAnvilDurability());
    }

    // ==================== PARAMETERIZED CONSTRUCTOR TESTS ====================

    @Test
    void testParameterizedConstructor() {
        Blacksmith custom =
                new Blacksmith(
                        "Royal Forge",
                        "The kingdom's elite forge");

        assertEquals("Royal Forge", custom.getName());
        assertEquals(
                "The kingdom's elite forge",
                custom.getDescription());

        assertEquals(
                Blacksmith.Status.OPERATIONAL,
                custom.getStatus());
    }

    // ==================== JACKSON SERIALIZATION TESTS ====================

    @Test
    void testJsonPropertyAnnotationsPresent() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(
                new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);

        String json = mapper.writeValueAsString(blacksmith);

        assertTrue(json.contains("\"identity\""));
        assertTrue(json.contains("\"name\""));
        assertTrue(json.contains("\"description\""));
        assertTrue(json.contains("\"foundingDate\""));
        assertTrue(json.contains("\"status\""));
        assertTrue(json.contains("\"weaponCount\""));
        assertTrue(json.contains("\"anvilDurability\""));
        assertTrue(json.contains("\"maxDurability\""));
    }

    @Test
    void testSerializationDeserializationRoundtrip() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(
                new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        mapper.activateDefaultTyping(
                mapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);

        Blacksmith original =
                new Blacksmith(
                        "Royal Forge",
                        "Elite weapons production");

        original.forgeWeapon();
        original.forgeWeapon();

        String json = mapper.writeValueAsString(original);

        Blacksmith deserialized =
                mapper.readValue(json, Blacksmith.class);

        assertEquals(
                original.getIdentity(),
                deserialized.getIdentity());

        assertEquals(
                original.getName(),
                deserialized.getName());

        assertEquals(
                original.getWeaponCount(),
                deserialized.getWeaponCount());

        assertEquals(
                original.getAnvilDurability(),
                deserialized.getAnvilDurability());

        assertEquals(
                original.getStatus(),
                deserialized.getStatus());
    }

    // ==================== REGISTRY TESTS ====================

    @Test
    void testStaticRegistrationOccurs() {
        assertTrue(
                KingdomRegistry.getRegisteredEntities()
                        .contains(Blacksmith.class));
    }
}