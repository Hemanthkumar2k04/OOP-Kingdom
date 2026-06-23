package kingdom.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kingdom.core.KingdomEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    void defaultConstructorShouldInitializeLibrary() {
        Library library = new Library();

        assertNotNull(library.getIdentity());
        assertTrue(library.getIdentity().startsWith("LIBRARY-"));
        assertEquals("Library", library.getName());
        assertNotNull(library.getDescription());
        assertNotNull(library.getFoundingDate());
        assertEquals(KingdomEntity.Status.UNDER_CONSTRUCTION, library.getStatus());
        assertTrue(library.getScrolls().isEmpty());
    }

    @Test
    void parameterizedConstructorShouldSetValues() {
        Library library =
                new Library(
                        "Royal Library",
                        "Kingdom archive of ancient scrolls");

        assertEquals("Royal Library", library.getName());
        assertEquals("Kingdom archive of ancient scrolls", library.getDescription());
        assertEquals(KingdomEntity.Status.OPERATIONAL, library.getStatus());
    }

    @Test
    void identitiesShouldBeUnique() {
        Library first = new Library();
        Library second = new Library();

        assertNotEquals(
                first.getIdentity(),
                second.getIdentity());
    }

    @Test
    void addScrollShouldStoreScroll() {
        Library library = new Library();

        library.addScroll("Fire Magic");

        assertTrue(library.hasScroll("Fire Magic"));
        assertEquals(1, library.getScrolls().size());
    }

    @Test
    void addScrollShouldTrimWhitespace() {
        Library library = new Library();

        library.addScroll("   Ancient History   ");

        assertTrue(library.hasScroll("Ancient History"));
    }

    @Test
    void addScrollShouldIgnoreNullAndBlankValues() {
        Library library = new Library();

        library.addScroll(null);
        library.addScroll("");
        library.addScroll("   ");

        assertTrue(library.getScrolls().isEmpty());
    }

    @Test
    void hasScrollShouldReturnFalseForMissingScroll() {
        Library library = new Library();

        assertFalse(library.hasScroll("Unknown Scroll"));
    }

    @Test
    void hasScrollShouldHandleNullSafely() {
        Library library = new Library();

        assertFalse(library.hasScroll(null));
    }

    @Test
    void getScrollsShouldReturnDefensiveCopy() {
        Library library = new Library();

        library.addScroll("Alchemy");

        List<String> copy = library.getScrolls();
        copy.clear();

        assertEquals(1, library.getScrolls().size());
        assertTrue(library.hasScroll("Alchemy"));
    }

    @Test
    void jacksonSerializationShouldPreserveValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        Library original =
                new Library(
                        "Royal Library",
                        "Kingdom archive");

        original.addScroll("Alchemy");
        original.addScroll("Engineering");

        String json = mapper.writeValueAsString(original);

        Library restored =
                mapper.readValue(json, Library.class);

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
                original.getScrolls().size(),
                restored.getScrolls().size());

        assertTrue(restored.hasScroll("Alchemy"));
        assertTrue(restored.hasScroll("Engineering"));

        assertEquals(
                original.getStatus(),
                restored.getStatus());
    }
}