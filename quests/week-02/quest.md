# ⚔️ Week 02 Quest: The Velfire Discovery

The kingdom has survived its first months. Basic amenities stand. But resources are thinning, and a mysterious traveler has revealed the true value of the purple stones beneath the valley. He calls it **Velfire** — a rare mineral that can save the kingdom from poverty — if extracted and traded wisely.

## Available Entities

| Entity | Contract | Status |
|--------|----------|--------|
| **Mine** | [`AbstractMine`](../kingdom/src/main/java/kingdom/contracts/AbstractMine.java) | ⚔️ Quest open |
| **Ore Processor** | [`AbstractOreProcessor`](../kingdom/src/main/java/kingdom/contracts/AbstractOreProcessor.java) | ⚔️ Quest open |
| **Trading Post** | [`AbstractTradingPost`](../kingdom/src/main/java/kingdom/contracts/AbstractTradingPost.java) | ⚔️ Quest open |
| **Storehouse** | [`AbstractStorehouse`](../kingdom/src/main/java/kingdom/contracts/AbstractStorehouse.java) | ⚔️ Quest open |
| **Library** | [`AbstractLibrary`](../kingdom/src/main/java/kingdom/contracts/AbstractLibrary.java) | ⚔️ Quest open — carried over from Week 1 |

## Instructions

1. **Pick an entity** — Mine, Ore Processor, Trading Post, Storehouse, or Library
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

> **Note:** No pre-booking — multiple contributors can implement the same entity. The best design wins the merge.

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