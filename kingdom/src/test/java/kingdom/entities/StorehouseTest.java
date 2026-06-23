package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorehouseTest {

    @Test
    void defaultConstructorShouldInitializeStorehouse() {
        Storehouse storehouse = new Storehouse();

        assertNotNull(storehouse.getIdentity());
        assertTrue(storehouse.getIdentity().startsWith("STOREHOUSE-"));
        assertEquals("Storehouse", storehouse.getName());
        assertNotNull(storehouse.getDescription());
        assertNotNull(storehouse.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, storehouse.getStatus());
        assertEquals(0, storehouse.getGrainStockpile());
        assertEquals(0, storehouse.getResourceStockpile());
    }

    @Test
    void parameterizedConstructorShouldSetValues() {
        Storehouse storehouse =
                new Storehouse(
                        "Royal Storehouse",
                        "Central storage facility");

        assertEquals("Royal Storehouse", storehouse.getName());
        assertEquals("Central storage facility", storehouse.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, storehouse.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        Storehouse first = new Storehouse();
        Storehouse second = new Storehouse();

        assertNotEquals(
                first.getIdentity(),
                second.getIdentity());
    }

    @Test
    void storeGrainShouldIncreaseStockpile() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeGrain(50);

        assertEquals(50, storehouse.getGrainStockpile());
    }

    @Test
    void storeGrainShouldIgnoreZeroOrNegativeValues() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeGrain(0);
        storehouse.storeGrain(-10);

        assertEquals(0, storehouse.getGrainStockpile());
    }

    @Test
    void storeResourcesShouldIncreaseStockpile() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeResources(75);

        assertEquals(75, storehouse.getResourceStockpile());
    }

    @Test
    void storeResourcesShouldIgnoreZeroOrNegativeValues() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeResources(0);
        storehouse.storeResources(-20);

        assertEquals(0, storehouse.getResourceStockpile());
    }

    @Test
    void distributeShouldReturnFalseWhenEmpty() {
        Storehouse storehouse = new Storehouse();

        assertFalse(storehouse.distribute());
    }

    @Test
    void distributeShouldReturnTrueWhenStockExists() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeGrain(10);
        storehouse.storeResources(10);

        assertTrue(storehouse.distribute());
    }

    @Test
    void distributeShouldReduceBothStockpiles() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeGrain(10);
        storehouse.storeResources(10);

        storehouse.distribute();

        assertEquals(9, storehouse.getGrainStockpile());
        assertEquals(9, storehouse.getResourceStockpile());
    }

    @Test
    void distributeShouldReduceOnlyAvailableStockpile() {
        Storehouse storehouse = new Storehouse();

        storehouse.storeGrain(5);

        assertTrue(storehouse.distribute());

        assertEquals(4, storehouse.getGrainStockpile());
        assertEquals(0, storehouse.getResourceStockpile());
    }

    @Test
    void jacksonSerializationShouldPreserveValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Storehouse original =
                new Storehouse(
                        "Royal Storehouse",
                        "Kingdom reserves");

        original.storeGrain(100);
        original.storeResources(50);

        String json = mapper.writeValueAsString(original);

        Storehouse restored =
                mapper.readValue(json, Storehouse.class);

        assertEquals(
                original.getIdentity(),
                restored.getIdentity());

        assertEquals(
                original.getName(),
                restored.getName());

        assertEquals(
                original.getDescription(),
                restored.getDescription());

        assertEquals(
                original.getGrainStockpile(),
                restored.getGrainStockpile());

        assertEquals(
                original.getResourceStockpile(),
                restored.getResourceStockpile());

        assertEquals(
                original.getStatus(),
                restored.getStatus());
    }
}