package kingdom.entities;

import kingdom.contracts.AbstractDiplomaticEnclave;
import kingdom.core.KingdomEntity;
import kingdom.core.KingdomRegistry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DiplomaticEnclave extends AbstractDiplomaticEnclave {

    static {
        KingdomRegistry.register(DiplomaticEnclave.class);
    }

    @JsonProperty("identity")
    private String id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String description;

    @JsonProperty
    private LocalDate foundingDate;

    @JsonProperty
    private KingdomEntity.Status status;

    @JsonProperty
    private List<String> pendingMessages;

    @JsonProperty
    private Set<String> alliedKingdoms;

    public DiplomaticEnclave() {
        this.id = "DIPLOMATICENCLAVE-" +
                UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 8)
                        .toUpperCase();
        this.name = "Diplomatic Enclave";
        this.description =
                "A diplomatic center responsible for managing alliances and communications between kingdoms.";
        this.foundingDate = LocalDate.now();
        this.status = KingdomEntity.Status.UNDER_CONSTRUCTION;
        this.pendingMessages = new ArrayList<>();
        this.alliedKingdoms = new HashSet<>();
    }

    public DiplomaticEnclave(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = KingdomEntity.Status.OPERATIONAL;
    }

    @Override
    public void sendEnvoy(String kingdomName, String message) {
        if (kingdomName == null || kingdomName.isBlank()) {
            return;
        }
        if (message == null || message.isBlank()) {
            return;
        }
        String trimmedKingdomName = kingdomName.trim();
        String trimmedMessage = message.trim();
        pendingMessages.add(trimmedKingdomName + ": " + trimmedMessage);
    }

    @Override
    @JsonIgnore
    public int getPendingMessageCount() {
        return pendingMessages.size();
    }

    @Override
    @JsonIgnore
    public String readLatestMessage() {
        if (pendingMessages.isEmpty()) {
            return "";
        }
        return pendingMessages.get(pendingMessages.size() - 1);
    }

    @Override
    public boolean forgeAlliance(String kingdomName) {
        if (kingdomName == null || kingdomName.isBlank()) {
            return false;
        }
        String trimmedKingdomName = kingdomName.trim();
        return alliedKingdoms.add(trimmedKingdomName);
    }

    @Override
    public String getIdentity() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    @Override
    public KingdomEntity.Status getStatus() {
        return status;
    }
}