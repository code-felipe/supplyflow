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

This pattern increases maintenance effort and the likelihood of missing assignments.

---

## 2.3. Invalid Intermediate States  

With public setters, DTOs can exist temporarily in an invalid or incomplete state  
(for example, missing `name`, `code`, or embedded objects like `dimensions`),  
potentially causing logical inconsistencies or rendering errors.

---

## 2.4. Lack of Centralized Validation and Defaults  

Normalization logic (for example, replacing blank `specification` with `"No assigned"`  
or turning empty strings into `null`) was spread across different layers,  
leading to duplication and inconsistent behavior.

---

## 2.5. Risk of NullPointerException  

Flattened mappings of embedded objects such as `Dimensions` or `Packaging`  
could throw `NullPointerException` when nested properties were accessed  
without proper null checks.

---

## 3. Solution – Applying the Builder Pattern  

### 3.1. Core Idea  

The **Builder Pattern** separates the construction of a complex object  
from its representation, allowing step-by-step controlled creation  
and guaranteeing a valid final state.

---

### 3.2. Implementation in SupplyFlow  

- **Private constructor** inside `SupplyItemViewDTO`, receiving a `Builder` instance.  
- **Static nested `Builder` class** providing fluent (chainable) methods for each attribute, e.g.:

```java
SupplyItemViewDTO.builder()
    .code("ACM-123")
    .name("Ultra Towel")
    .specification("High absorbency")
    .build();
