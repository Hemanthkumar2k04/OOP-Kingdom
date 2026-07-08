# ⚔️ Week 05 Quest: The Phantom Alliance

The Phantom Kingdom of the West has arrived. Their army stands beside OOP's defenders. But war is chaos — and alliances must be maintained as surely as battles must be fought. The kingdom needs new structures to coordinate with its allies and deploy the Phantom Kingdom's legendary war units.

## Available Entities

| Entity | Contract | Status |
|--------|----------|--------|
| **Beacon Tower** | [`AbstractBeaconTower`](../kingdom/src/main/java/kingdom/contracts/AbstractBeaconTower.java) | ⚔️ Quest open |
| **War Elephant** | [`AbstractWarElephant`](../kingdom/src/main/java/kingdom/contracts/AbstractWarElephant.java) | ⚔️ Quest open |
| **Diplomatic Enclave** | [`AbstractDiplomaticEnclave`](../kingdom/src/main/java/kingdom/contracts/AbstractDiplomaticEnclave.java) | ⚔️ Quest open |

## Instructions

1. **Pick an entity** from the table above
2. **Read the contract** — open the corresponding abstract class in `kingdom/contracts/`
3. **Implement the class** in `kingdom/entities/` — extend the contract, implement all methods, add `@JsonProperty`, register with `KingdomRegistry`
4. **Write tests** in `kingdom/src/test/java/kingdom/entities/` — constructor, contract methods, extra methods, Jackson serialization
5. **Test locally:**
   ```bash
   cd kingdom
   mvn clean test
   ```
6. **Boot check:**
   ```bash
   cd kingdom
   mvn exec:java -Dexec.mainClass="kingdom.Main"
   ```
7. **Update [`contributors.json`](../contributors.json):** `"YourClass": "YourGitHubUsername"`
8. **Create a UML diagram** (optional — save in `uml/yourclass.md`, include only directly related classes, earns bonus points during review)
9. **Submit a PR** using the [PR Template](../.github/PULL_REQUEST_TEMPLATE.md)

> **Note:** Issues are **not assigned.** Multiple contributors can work on the same entity. Everyone submits their best design, the community scores them, and the highest-scoring implementation gets merged.

---

## Reference

| Doc | Link |
|-----|------|
| Code Standards | [CODE_STANDARDS.md](../docs/CODE_STANDARDS.md) |
| Build & Test | [BUILD.md](../docs/BUILD.md) |
| Review Rubric | [REVIEW_RUBRIC.md](../docs/REVIEW_RUBRIC.md) |
| Quest Guide | [template.md](../quests/template.md) |
| Contributing Guide | [CONTRIBUTING.md](../.github/CONTRIBUTING.md) |

---

_May the best design win. ⚔️_