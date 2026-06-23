# ⚔️ Week 03 Quest: The Watchers

The Velfire trade has brought prosperity — and with it, crime. Traders report stolen goods, travelers lose their purses, and fights break out in the streets. The city has no system of law and order. It needs builders to create one — from the smallest patrol to the highest court.

## Available Entities

| Entity | Contract | Status |
|--------|----------|--------|
| **Guard Post** | [`AbstractGuardPost`](../kingdom/src/main/java/kingdom/contracts/AbstractGuardPost.java) | ⚔️ Quest open |
| **Guard Station** | [`AbstractGuardStation`](../kingdom/src/main/java/kingdom/contracts/AbstractGuardStation.java) | ⚔️ Quest open |
| **Court House** | [`AbstractCourtHouse`](../kingdom/src/main/java/kingdom/contracts/AbstractCourtHouse.java) | ⚔️ Quest open |
| **Prison** | [`AbstractPrison`](../kingdom/src/main/java/kingdom/contracts/AbstractPrison.java) | ⚔️ Quest open |
| **Gatehouse** | [`AbstractGatehouse`](../kingdom/src/main/java/kingdom/contracts/AbstractGatehouse.java) | ⚔️ Quest open |
| **Town Crier** | [`AbstractTownCrier`](../kingdom/src/main/java/kingdom/contracts/AbstractTownCrier.java) | ⚔️ Quest open |

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

## 📚 Reference

| Doc | Link |
|-----|------|
| Code Standards | [CODE_STANDARDS.md](../docs/CODE_STANDARDS.md) |
| Build & Test | [BUILD.md](../docs/BUILD.md) |
| Review Rubric | [REVIEW_RUBRIC.md](../docs/REVIEW_RUBRIC.md) |
| Quest Guide | [template.md](../quests/template.md) |
| Contributing Guide | [CONTRIBUTING.md](../.github/CONTRIBUTING.md) |

---

_May the best design win. ⚔️_