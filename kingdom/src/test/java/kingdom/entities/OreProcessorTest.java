package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OreProcessorTest {

    @Test
    void defaultConstructorShouldInitializeOreProcessor() {
        OreProcessor processor = new OreProcessor();

        assertNotNull(processor.getIdentity());
        assertTrue(processor.getIdentity().startsWith("OREPROCESSOR-"));
        assertEquals("Ore Processor", processor.getName());
        assertNotNull(processor.getDescription());
        assertNotNull(processor.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, processor.getStatus());
        assertEquals(50, processor.getRawOreStockpile());
        assertEquals(0, processor.getProcessedStoneCount());
        assertEquals(10, processor.getRawOreNeeded());
    }

    @Test
    void parameterizedConstructorShouldSetValues() {
        OreProcessor processor =
                new OreProcessor(
                        "Royal Ore Processor",
                        "Processes rare velfire ore");

        assertEquals("Royal Ore Processor", processor.getName());
        assertEquals("Processes rare velfire ore", processor.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, processor.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        OreProcessor first = new OreProcessor();
        OreProcessor second = new OreProcessor();

        assertNotEquals(
                first.getIdentity(),
                second.getIdentity());
    }

    @Test
    void processOreShouldConsumeRawOreAndProduceStone() {
        OreProcessor processor = new OreProcessor();

        processor.processOre();

        assertEquals(40, processor.getRawOreStockpile());
        assertEquals(1, processor.getProcessedStoneCount());
    }

    @Test
    void processOreShouldNotRunWhenInsufficientOre() {
        OreProcessor processor = new OreProcessor();

        for (int i = 0; i < 5; i++) {
            processor.processOre();
        }

        assertEquals(0, processor.getRawOreStockpile());
        assertEquals(5, processor.getProcessedStoneCount());

        processor.processOre();

        assertEquals(0, processor.getRawOreStockpile());
        assertEquals(5, processor.getProcessedStoneCount());
    }

    @Test
    void getRawOreNeededShouldReturnConfiguredValue() {
        OreProcessor processor = new OreProcessor();

        assertEquals(10, processor.getRawOreNeeded());
    }

    @Test
    void jacksonSerializationShouldPreserveValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        OreProcessor original =
                new OreProcessor(
                        "Royal Ore Processor",
                        "Processes rare velfire ore");

        original.processOre();

        String json = mapper.writeValueAsString(original);

        OreProcessor restored =
                mapper.readValue(json, OreProcessor.class);

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
                original.getRawOreStockpile(),
                restored.getRawOreStockpile());

        assertEquals(
                original.getProcessedStoneCount(),
                restored.getProcessedStoneCount());

        assertEquals(
                original.getRawOreNeeded(),
                restored.getRawOreNeeded());

        assertEquals(
                original.getStatus(),
                restored.getStatus());
    }
}