package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import kingdom.contracts.AbstractBarracks;
import kingdom.core.KingdomRegistry;

import java.time.LocalDate;
import java.util.*;

public class Barracks extends AbstractBarracks {

    static {
        KingdomRegistry.register(Barracks.class);
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
    private Status status;

    @JsonProperty
    private List<String> troops;

    @JsonProperty
    private List<String> specialTroops;

    @JsonProperty
    private Map<String, Integer> equipments;

    @JsonProperty
    private int maxSpecialTroopsCapacity;

    @JsonProperty
    private int maxTroopsCapacity;

    @JsonProperty
    private int maxEquipmentCapacity;

    // No-arg constructor - UNDER_CONSTRUCTION
    public Barracks() {
        this.id = "BARRACKS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Barracks";
        this.description = "A military facility for training and housing the kingdom's troops.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.equipments = new HashMap<>();
        this.troops = new ArrayList<>();
        this.specialTroops = new ArrayList<>();
        this.maxTroopsCapacity = 10;
        this.maxSpecialTroopsCapacity = 2;
        this.maxEquipmentCapacity = 100;
    }

    // Parameterized constructor - OPERATIONAL
    public Barracks(String name, int maxSpecialTroopsCapacity, int maxTroopsCapacity, int maxEquipmentCapacity) {
        this();
        this.name = name;
        this.maxTroopsCapacity = maxTroopsCapacity;
        this.maxSpecialTroopsCapacity = maxSpecialTroopsCapacity;
        this.maxEquipmentCapacity = maxEquipmentCapacity;
        this.status = Status.OPERATIONAL;
    }

    @Override
    public void trainTroop(String troopName) {
        if(troops.size() >= maxTroopsCapacity) {
            throw new IllegalStateException("Barracks is at full troop capacity.");
        }

        switch (troopName.toLowerCase()) {
            case "archer" -> {
                addEquipment("bow", 1);
                addEquipment("helmet", 1);
                addEquipment("chest",1 );
                addEquipment("boot", 2);
            }
            case "guard" -> {
                addEquipment("spear", 1);
                addEquipment("helmet", 1);
                addEquipment("chest", 1);
                addEquipment("leg armor", 1);
                addEquipment("boot", 2);
            }
            case "goblin" -> addEquipment("knife", 1);
            default -> throw new IllegalArgumentException("Troop Not Found: " + troopName);
        }

        this.troops.add(troopName);
    }

    public void trainSpecialTroops(String specialTroopName) {
        if(specialTroops.size() >= maxSpecialTroopsCapacity) {
            throw new IllegalStateException("Barracks is at full special troop capacity.");
        }

        switch (specialTroopName.toLowerCase()) {
            case "knight" -> {
                addEquipment("long sword", 1);
                addEquipment("shield", 1);
                addEquipment("reinforced chest", 1);
                addEquipment("leg armor", 1);
                addEquipment("glove", 2);
                addEquipment("boot", 2);
            }
            case "wizard" -> {
                addEquipment("magic scepter", 1);
                addEquipment("grimoire", 1);
                addEquipment("magic hat", 1);
                addEquipment("magic boot", 2);
                addEquipment("magic clothes", 1);
            }
            default -> throw new IllegalArgumentException("Special Troop not found: " + specialTroopName);
        }

        this.specialTroops.add(specialTroopName);
    }

    private void addEquipment(String equipmentName, int quantity) {
        if(equipments.values().stream().mapToInt(Integer::intValue).sum() + quantity > maxEquipmentCapacity) {
            throw new IllegalStateException("Barracks is at full equipment capacity.");
        }
        equipments.merge(equipmentName, quantity, Integer:: sum);
    }

    public void improveBarracks(Lumberyard lumberyard) {
        if(!lumberyard.consumeWood(200)) {
            throw new IllegalStateException("Not enough wood to improve barracks.");
        }

        this.maxTroopsCapacity += 5;
        this.maxSpecialTroopsCapacity +=1;
        this.maxEquipmentCapacity += 25;
    }

    public void repair(Lumberyard lumberyard) {
        if(this.status != Status.DAMAGED) {
            throw new IllegalStateException("Barracks isn't damaged.");
        }
        if(!lumberyard.consumeWood(150)) {
            throw new IllegalStateException("Not enough wood to repair barracks.");
        }
        this.status = Status.OPERATIONAL;
    }

    public List<String> getSpecialTroops()
    {
        return Collections.unmodifiableList(specialTroops);
    }

    public Map<String, Integer> getEquipments() {
        return Collections.unmodifiableMap(equipments);
    }

    @Override
    public List<String> getTroops() {
        return Collections.unmodifiableList(troops);
    }

    @Override
    public String getIdentity() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public LocalDate getFoundingDate() {
        return this.foundingDate;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    public int getMaxTroopsCapacity() {
        return this.maxTroopsCapacity;
    }

    public int getMaxSpecialTroopsCapacity() {
        return this.maxSpecialTroopsCapacity;
    }

    public int getMaxEquipmentCapacity() {
        return this.maxEquipmentCapacity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setMaxTroopsCapacity(int maxTroopsCapacity) {
        this.maxTroopsCapacity = maxTroopsCapacity;
    }

    public void setMaxSpecialTroopsCapacity(int maxSpecialTroopsCapacity) {
        this.maxSpecialTroopsCapacity = maxSpecialTroopsCapacity;
    }

    public void setMaxEquipmentCapacity(int maxEquipmentCapacity) {
        this.maxEquipmentCapacity = maxEquipmentCapacity;
    }
}
