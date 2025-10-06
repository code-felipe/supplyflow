# Architecture

## Domain
- **User** — employee who places supply requests.
- **CustomerAccount** — client company (e.g., NTS).
- **CustomerSite** — user's current location where shipTo takes place.
- **Request** — a supply order placed by a User to a specific CustomerSite and CustomerAccount as its derivative.
- **RequestItem** — a list of items  within a Request; references a SupplyItem and a quantity.
- **Role** — a list roles to authenticate during log phase and authorize end points based on role - active - email and password.


**Key relations (ER sketch)**

# Core domain

User 1 ── * Request   ↔   (BI-DIRECTIONAL)
  • In User:    @OneToMany(mappedBy="user", cascade=ALL, orphanRemoval=true)
  • In Request: @ManyToOne(fetch=LAZY)

Request 1 ── * RequestItem   →   (UNI-DIRECTIONAL from Request)
  • In Request:     @OneToMany(cascade=ALL, orphanRemoval=true) @JoinColumn("request_id")
  • In RequestItem: (no Request field)

Request * ── 1 CustomerSite (shipTo)   →   (UNI-DIRECTIONAL from Request)
  • In Request: @ManyToOne(fetch=LAZY) @JoinColumn("site_id")
  • In CustomerSite: (no Request collection)

User * ── 1 CustomerSite (assignedSite)   →   (UNI-DIRECTIONAL from User)
  • In User: @ManyToOne(fetch=LAZY, optional=true) @JoinColumn("site_id")

CustomerSite * ── 1 CustomerAccount   →   (UNI-DIRECTIONAL from CustomerSite)
  • In CustomerSite: @ManyToOne(fetch=LAZY) @JoinColumn("customer_id")

User 1 ── * Role   →   (UNI-DIRECTIONAL from User)
  • In User: @OneToMany(cascade=ALL, orphanRemoval=true) @JoinColumn("user_id")
  • In Role: (no User field)


# Inventory / catalog

SupplyItem 1 ── 1 Product   →   (UNI-DIRECTIONAL from SupplyItem)
  • In SupplyItem: @OneToOne(cascade=ALL, orphanRemoval=true) @JoinColumn("product_id")
  • In Product:    (no back-reference)

RequestItem * ── 1 SupplyItem   →   (UNI-DIRECTIONAL from RequestItem)
  • In RequestItem: @ManyToOne(fetch=LAZY) @JoinColumn("supply_item_id")

- `User` persists **only** `site_id` (assignedSite).  
- `CustomerAccount` is **derived** via `user.assignedSite.customer` (and via `request.shipTo.customer`).  

## Implemented use cases
- Create/Edit user.
- Assign site to user:
  - **Select existing** `CustomerSite`.
  - **Inline create** `CustomerAccount` + `CustomerSite` (upsert by codes).
  - **UI toggle**: a small JS script switches between modes via **radio buttons**, disabling hidden inputs to prevent mixing.
- User detail with requests (paged, table projections).
- List users:
  - Null-safe DTO labels show **“No assigned”** when fields are empty.

## Technical patterns
- **DTOs:** `UserForm` (input/validation), `UserDetailView` (null-safe view), `RequestRow` (table form projection).
- **Validation:** Bean Validation + service-level rules (server is the source of truth).
- **Queries:** `@EntityGraph` to load `assignedSite.customer` and avoid N+1; JPQL projections for request lists/aggregates.
- **Transactions:** service boundary with `@Transactional`; `readOnly` for queries.
- **Security:** passwords encoded; set on create, update only when a non-blank value is provided.
- **DB/Schema:** UNIQUE codes, composite UNIQUE for `(customer_id, site_code)`, FKs enforced, indexes on search fields; `orphanRemoval` for request items.

## Why this architecture
- **Simplicity:** persist only `users.site_id`; derive the customer → single source of truth.
- **Flexibility:** inline create enables operations without separate CRUD screens.
- **Performance:** EntityGraph + projections minimize queries and payloads.

## Domain Diagram
![Domain diagram](./diagrams/domain-v1.png)
