package kingdom.entities;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import kingdom.core.KingdomRegistry;

class TavernTest {

private Tavern tavern;

@BeforeEach
void setUp() {
    tavern = new Tavern();
}

// DEFAULT CONSTRUCTOR TESTS

@Test
void testDefaultConstructorInitializesSafeDefaults() {
    assertNotNull(tavern.getIdentity());
    assertTrue(tavern.getIdentity().startsWith("TAVERN-"));
    assertEquals("Tavern", tavern.getName());
    assertNotNull(tavern.getDescription());
    assertEquals(LocalDate.now(), tavern.getFoundingDate());
    assertEquals(Tavern.Status.UNDER_CONSTRUCTION, tavern.getStatus());
}

@Test
void testDefaultConstructorInitializesHeroesList() {
    assertNotNull(tavern.getHeroes());
    assertTrue(tavern.getHeroes().isEmpty());
}

@Test
void testUUIDUniqueness() {
    Tavern tavern1 = new Tavern();
    Tavern tavern2 = new Tavern();

    assertNotEquals(
            tavern1.getIdentity(),
            tavern2.getIdentity());
}

// =KINGDOM ENTITY TESTS 

@Test
void testImplementsKingdomEntity() {
    assertNotNull(tavern.getIdentity());
    assertNotNull(tavern.getName());
    assertNotNull(tavern.getDescription());
    assertNotNull(tavern.getFoundingDate());
    assertNotNull(tavern.getStatus());
}

//HERO MANAGEMENT TESTS 

@Test
void testHireHeroAddsHero() {
    tavern.hireHero("Arthur");

    assertEquals(1, tavern.getHeroes().size());
    assertTrue(tavern.getHeroes().contains("Arthur"));
}

@Test
void testHireMultipleHeroes() {
    tavern.hireHero("Arthur");
    tavern.hireHero("Lancelot");
    tavern.hireHero("Merlin");

    assertEquals(3, tavern.getHeroes().size());
}

@Test
void testHireHeroIgnoresNull() {
    tavern.hireHero(null);

    assertTrue(tavern.getHeroes().isEmpty());
}

@Test
void testHireHeroIgnoresBlankString() {
    tavern.hireHero("   ");

    assertTrue(tavern.getHeroes().isEmpty());
}

@Test
void testHireHeroTrimsWhitespace() {
    tavern.hireHero("   Arthur   ");

    assertEquals(1, tavern.getHeroes().size());
    assertEquals("Arthur", tavern.getHeroes().get(0));
}

@Test
void testGetHeroesReturnsDefensiveCopy() {
    tavern.hireHero("Arthur");

    List<String> heroes = tavern.getHeroes();
    heroes.add("InjectedHero");

    assertEquals(1, tavern.getHeroes().size());
    assertFalse(tavern.getHeroes().contains("InjectedHero"));
}

// REST TEST

@Test
void testRestDoesNothingWhenUnderConstruction() {
    tavern.rest();

    assertEquals(
            Tavern.Status.UNDER_CONSTRUCTION,
            tavern.getStatus());
}

//  PARAMETERIZED CONSTRUCTOR TESTS 

@Test
void testParameterizedConstructor() {
    Tavern custom =
            new Tavern(
                    "Golden Mug",
                    "The kingdom's finest tavern");

    assertEquals("Golden Mug", custom.getName());
    assertEquals(
            "The kingdom's finest tavern",
            custom.getDescription());

    assertEquals(
            Tavern.Status.OPERATIONAL,
            custom.getStatus());
}

//JACKSON SERIALIZATION TESTS 

@Test
void testJsonPropertyAnnotationsPresent() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    mapper.registerModule(
            new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    mapper.activateDefaultTyping(
            mapper.getPolymorphicTypeValidator(),
            ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);

    String json = mapper.writeValueAsString(tavern);

    assertTrue(json.contains("\"identity\""));
    assertTrue(json.contains("\"name\""));
    assertTrue(json.contains("\"description\""));
    assertTrue(json.contains("\"foundingDate\""));
    assertTrue(json.contains("\"status\""));
    assertTrue(json.contains("\"heroes\""));
}

@Test
void testSerializationDeserializationRoundtrip() throws Exception {
    ObjectMapper mapper = new ObjectMapper();

    mapper.registerModule(
            new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    mapper.activateDefaultTyping(
            mapper.getPolymorphicTypeValidator(),
            ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);

    Tavern original =
            new Tavern(
                    "Golden Mug",
                    "Elite adventurer tavern");

    original.hireHero("Arthur");
    original.hireHero("Merlin");

    String json = mapper.writeValueAsString(original);

    Tavern deserialized =
            mapper.readValue(json, Tavern.class);

    assertEquals(
            original.getIdentity(),
            deserialized.getIdentity());

    assertEquals(
            original.getName(),
            deserialized.getName());

    assertEquals(
            original.getHeroes(),
            deserialized.getHeroes());

    assertEquals(
            original.getStatus(),
            deserialized.getStatus());
}

// TESTS

@Test
void testStaticRegistrationOccurs() {
    assertTrue(
            KingdomRegistry.getRegisteredEntities()
                    .contains(Tavern.class));
}

}
