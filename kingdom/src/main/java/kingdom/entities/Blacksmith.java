package kingdom.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.util.UUID;

import kingdom.contracts.AbstractBlacksmith;
import kingdom.core.KingdomRegistry;
/**
 * The Blacksmith is a kingdom entity responsible for forging weapons.
 * It tracks weapon production and anvil durability.
 *
 * If the anvil wears out, the Blacksmith becomes DAMAGED and must be
 * repaired before forging can continue.
 */
public class Blacksmith extends AbstractBlacksmith {

    static {
        KingdomRegistry.register(Blacksmith.class);
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
    private int weaponCount;

    @JsonProperty
    private int anvilDurability;

    @JsonProperty
    private int maxDurability;

    /**
     * Default constructor for Jackson serialization and reflection-based instantiation.
     * Initializes safe defaults.
     */
    
    public Blacksmith() {
        this.id = "BLACKSMITH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.name = "Blacksmith";
        this.description = "A workshop responsible for forging weapons and tools for the kingdom.";
        this.foundingDate = LocalDate.now();
        this.status = Status.UNDER_CONSTRUCTION;
        this.weaponCount = 0;
        this.anvilDurability = 100;
        this.maxDurability = 100;
    }

    /**
     * Parameterized constructor for explicit instantiation.
     */
    public Blacksmith(String name, String description) {
        this();
        this.name = name;
        this.description = description;
        this.status = Status.OPERATIONAL;
    }

    /**
     * Forges a new weapon.
     * Each weapon forged reduces anvil durability by 1.
     * When durability reaches 0, the Blacksmith becomes DAMAGED.
     */
    @Override
    public void forgeWeapon() {
        if (this.status == Status.DAMAGED) {
            return;
        }

        this.weaponCount++;
        this.anvilDurability--;

        if (this.anvilDurability <= 0) {
            this.anvilDurability = 0;
            this.status = Status.DAMAGED;
        }
    }

    /**
     * Returns the total number of weapons forged.
     */
    @Override
    public int getWeaponCount() {
        return this.weaponCount;
    }

    /**
     * Repairs the anvil and restores the Blacksmith to OPERATIONAL status.
     */
    @Override
    public void repairAnvil() {
        this.anvilDurability = this.maxDurability;
        this.status = Status.OPERATIONAL;
    }

    /**
     * Returns the current durability of the anvil.
     */
    public int getAnvilDurability() {
        return this.anvilDurability;
    }

    /**
     * Returns the maximum durability of the anvil.
     */
    public int getMaxDurability() {
        return this.maxDurability;
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
}