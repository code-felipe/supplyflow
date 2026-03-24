# Builder Design Pattern – Technical Report

## 1. System Context

The **SupplyFlow** application manages supply-related entities such as `SupplyItem`, which contain multiple nested and optional attributes — category, specifications, dimensions, and packaging details.
For the web layer, two main Data Transfer Objects (DTOs) are used:

- **`SupplyItemFormDTO`** – handles form input for create and edit operations.
- **`SupplyItemViewDTO`** – renders flattened, human-readable data for lists and views.

As the system evolved, the mapping and construction of these DTOs became increasingly complex and error-prone.

---

## 2. Problem

### 2.1. Excessive Object Construction Complexity

`SupplyItemViewDTO` and `SupplyItemFormDTO` both contain over ten properties.
Constructors with multiple parameters or repetitive setter calls make object creation verbose, hard to read, and fragile when new attributes are added.

### 2.2. Verbose and Repetitive Mapping Logic

Manual mapping in `SupplyMapper` involves numerous lines such as:

```java
dto.setCode(item.getCode());
dto.setName(item.getName());
dto.setDescription(item.getDescription());
```

This pattern increases maintenance effort and the likelihood of missing assignments.

### 2.3. Invalid Intermediate States

With public setters, DTOs can exist temporarily in an invalid or incomplete state
(for example, missing `name`, `code`, or embedded objects like `dimensions`),
potentially causing logical inconsistencies or rendering errors.

### 2.4. Lack of Centralized Validation and Defaults

Normalization logic (for example, replacing blank `specification` with `"No assigned"`
or turning empty strings into `null`) was spread across different layers,
leading to duplication and inconsistent behavior.

### 2.5. Risk of NullPointerException

Flattened mappings of embedded objects such as `Dimensions` or `Packaging`
could throw `NullPointerException` when nested properties were accessed
without proper null checks.

---

## 3. Solution – Applying the Builder Pattern

### 3.1. Core Idea

The **Builder Pattern** separates the construction of a complex object
from its representation, allowing step-by-step controlled creation
and guaranteeing a valid final state.

### 3.2. Implementation in SupplyFlow

- **Private constructor** inside `SupplyItemViewDTO`, receiving a `Builder` instance.
- **Static nested `Builder` class** providing fluent (chainable) methods for each attribute:

```java
SupplyItemViewDTO dto = SupplyItemViewDTO.builder()
    .code("ACM-123")
    .name("Ultra Towel")
    .specification("High absorbency")
    .build();
```

### 3.3. What the Builder Solves in Practice

| Problem | Builder Solution |
|---|---|
| 10+ parameter constructors | Fluent step-by-step construction |
| Null nested objects (Dimensions, Packaging) | Null checks centralized inside `build()` |
| Blank strings needing defaults | Normalization logic lives in the builder |
| Invalid intermediate states | Object only exists after `build()` validates it |

### 3.4. Tradeoffs

| Benefit | Consideration |
|---|---|
| Readable, self-documenting construction | More boilerplate than plain setters |
| Centralized defaults and null-safety | Builder must be kept in sync with the DTO |
| Immutable or semi-immutable result | Harder to reuse the same object for both create and edit flows |

---

# Service Layer Pattern – Technical Report

## 1. System Context

In **SupplyFlow**, controllers handle HTTP requests and delegate all business logic,
persistence, and mapping to a dedicated service layer.
This separation keeps controllers thin and focused on routing and response handling.

---

## 2. Problem

### 2.1. Anemic Controllers or Bloated Controllers

Without a service layer, controllers either do too little (just passing data through)
or too much (directly calling repositories, running validation, and mapping DTOs).
Both extremes are hard to maintain and test.

### 2.2. Scattered Concerns

Validation, mapping, exception handling, and transaction management leak into controllers
or utility classes, creating inconsistent patterns across the codebase.

### 2.3. Tight Coupling Between UI and Persistence

When controllers depend directly on repository details (queries, paging, filters),
any change in the persistence layer forces controller rewrites.

### 2.4. No Single Transaction Boundary

Without a service layer, transaction boundaries are undefined or inconsistent,
risking partial writes and data integrity issues.

---

## 3. Solution – Service Layer in SupplyFlow

### 3.1. Core Idea

Each domain entity has a dedicated service interface and implementation.
The controller calls the service; the service owns all business logic,
repository calls, mapper coordination, and transaction boundaries.

### 3.2. Implementation

**Interface — the contract the controller sees:**

```java
public interface ISupplyItemService {
    List<SupplyItem> findAll();
    Optional<SupplyItem> findOne(Long id);
    SupplyItem findById(Long id);
    SupplyItem save(SupplyItemFormDTO dto);
    boolean existsByCode(String code);
    boolean existsByCodeAndIdNot(String code, Long id);
    Page<SupplyItemViewDTO> findPageByCategory(Long id, Pageable pageable);
}
```

**Implementation — what the controller never needs to know:**

```java
@Service
public class SupplyItemImpl implements ISupplyItemService {

    @Autowired
    private ISupplyItemDao supplyItemDao;

    @Autowired
    private ICategoryDao categoryDao;

    @Autowired
    private DimensionMapperImpl dimensionMapper;

    @Autowired
    private PackagingMapperImpl packagingMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SupplyItem> findAll() {
        return (List<SupplyItem>) supplyItemDao.findAll();
    }

    @Override
    @Transactional
    public SupplyItem save(SupplyItemFormDTO dto) {
        // 1. Map DTO → entity (coordinating DimensionMapper + PackagingMapper)
        // 2. Apply defaults and normalization
        // 3. Persist via DAO
        // 4. Return saved entity
    }
}
```

### 3.3. What the Service Layer Hides from Controllers

- Repository/DAO calls (`ISupplyItemDao`, `ICategoryDao`)
- Mapper coordination (`DimensionMapperImpl`, `PackagingMapperImpl`)
- Domain validation (`existsByCode`, `existsByCodeAndIdNot`)
- Transaction boundaries (`@Transactional`, `readOnly` for queries)
- Normalization and default value logic

### 3.4. Tradeoffs

| Benefit | Consideration |
|---|---|
| Controllers stay thin and focused | Adds an extra layer to navigate |
| Business logic has a single home | Service can grow large without sub-services |
| Transaction boundary is explicit | Requires discipline to keep logic out of controllers |
| Easy to unit test in isolation | Needs mocking of multiple dependencies |

