# DECISION DOCUMENT — INTRODUCTION

This document exists to make our architectural and security decisions **explicit, reviewable, and durable**. By writing down *what we decided, why I decided it, and what I rejected*, I reduce ambiguity, prevent regressions, and make it easier for anyone new to the codebase to understand the system’s guardrails.

# Key Decisions On User - CustomerAccount and CustomerSite

1. Persist only `users.site_id`; derive `CustomerAccount` from `CustomerSite`.
2. Two assignment modes: select existing site OR inline create (upsert).
3. DTO separation:
   - `UserFormDTO` for input/validation.
   - `UserViewDTO` for presentation (null-safe),
   - `RequestViewDTO` for table lists (projection).
   - `SupplyItemFormDTO` for input/validation.
   - `Mapper` a static class for each DTO that transform entity to DTO.
4. Server-side validation (Bean Validation + groups); client-side only for UX.
5. Query optimization:
   - `@EntityGraph` for `assignedSite.customer`,
   - JPQL projections with aggregates for tables.
6. Transactions at service layer; no `cascade` on `@ManyToOne`; `orphanRemoval` for items.
7. Password handling: encoded; update only if provided on edit.
8. Migrations & constraints: UNIQUE codes; FKs; indices; repeatable Flyway/Liquibase migrations.

# Validation: SupplyItem & User

### Class-level uniqueness validation (`@UniqueProductCode`)
- Applied at **DTO class level** (`SupplyItemFormDTO`) instead of field-level.
- The validator receives **both `id` and `code`**, enabling correct behavior across flows:
  - **Create:** reject if `code` already exists (`existsByCode`).
  - **Edit:** allow keeping the same `code` for the same product; reject only if that `code` belongs to a **different** product (`existsByCodeAndIdNot`).
- Error messages are **bound to `product.code`** via `addPropertyNode("code")`, only feedback shows directly under the view input.
- Defense in depth:
  - Bean Validation in DTO (`@UniqueSupplyCode`),
  - Repository checks (`existsByCode`, `existsByCodeAndIdNot`),
  - **Database unique constraint** on `supply.code` (last line of defense vs. race conditions).

**Benefits**
- Avoid false positives when editing (if you don't change the code).
- Clear error messages in the correct field.
- Reusable when `Product` is used outside of `SupplyItem`.
- Aligned with application logic: **unique and consistent** catalog identifiers.

### User Identity Parity ("@UniqueEmail");
- `User.email` follows the **same conceptual pattern** as `Product.code`:
- Validation at the DTO level (ideally `UserForm`), with access to `id` and `email`.
- **Create:** `existsByEmail(email)` must be **false**.
- **Edit:** `existsByEmailAndIdNot(email, id)` must be **false** (allowing the user's email to remain the same).
- Error message linked to the `email` field with `addPropertyNode("email")`.
- **Unique index/constraint** in the DB on `users.email`.
- Result: Consistent, collision-free user identity, with the same defense-in-depth strategy.

# Decision: Use jQuery UI Autocomplete for SupplyItem Search and Quantity Updates

## Context
At this stage of the project, I needed a lightweight and fast solution to allow housekeeping employees to search and add products efficiently.  
Implementing a modern SPA framework (e.g., React or Vue) would have added unnecessary complexity to the MVP.

## Decision
I implemented product search and dynamic quantity updates using **jQuery UI Autocomplete** connected to the backend endpoint `/request/load-items`.

## Rationale
- Simple integration with existing backend.  
- Provides a responsive and familiar user experience.  
- Avoids overengineering since the rest of the system is still server-side rendered.

## Consequences
- Quick to implement and maintain in the short term.  
- Future iterations may migrate this feature to a React or Vue frontend for more flexibility and scalability.  
- Detailed behavior (like updating product quantities dynamically) is documented through inline code comments rather than high-level project documentation.  

**API Contract (as implemented)**  
- `GET /request/load-items{term}?term={string}`  
  - Response: `application/json` → `[{ id, name, quantity? }]`  
- The inclusion of the item into `RequestItems` is completed during form submission or a later action (not in the autocomplete `select`).  

**Consequences**  
- ✔ Faster UX when searching for items (no page reloads).  
- ✔ Compatible with server-side validation and binding.  
- ➖ The autocomplete selection does **not** immediately add the item; it only prepares the input. Addition happens on the next step (form submission or other action).  
- Must maintain server-side validation and consider rate limiting if traffic grows.  

**Related Patterns**  
- **Class-level uniqueness validation**: `@UniqueSupplyCode` in `ProductForm` (validates `code` considering `id` in create/update), with error messages bound to the field (`addPropertyNode("code")`) and a unique database constraint.  
- **Parity with user identity (`@UniqueEmail`)**: applies the same *defense in depth* strategy (DTO validation + repository checks + unique index) to enforce uniqueness of `User.email`.  

**Rationale**  
The choice of incremental autocomplete prioritizes delivery speed, simplicity, and alignment with the existing architecture. Deferring the final inclusion of the item to a submit/post action reduces client-side complexity and keeps transactional control on the server.

# User Activation Toggle — Design Note
## Summary
- I don’t hard-delete users. I activate/deactivate accounts via a toggle. When inactive, users are blocked from protected flows, while data remains intact. The UI visually disables the entire table row and keeps only the toggle cell usable.

## Why
- **Integrity & auditability**: Preserve records while preventing actions.

- **Simplicity**: A single boolean (isActive) drives behavior and UI.

- **Consistent UX**: Visual state and flash messages reflect permissions.

## Behavior (What it does)
- Toggling flips the user’s active state and shows a flash message.

- Controllers/guards redirect away from view/edit/create flows when the user is inactive or missing, adding an appropriate flash.

- In lists, inactive users render as a disabled row; only the toggle remains interactive.

## Implementation (Simplified)
- **Endpoint**: Receives the toggle action, updates the active state, sets a flash, and redirects to a safe page (e.g., the list).

- **Controllers/guards**: On access, verify existence and active status; on failure, set flash and redirect.

- **Template (Thymeleaf)**: Apply a row-level “disabled” state on <tr> when inactive; links keep their normal URLs (no per-button duplication).

- **CSS**: One state class visually dims the row and blocks pointer interaction; a companion class exempts the toggle cell.

- **Flash messages**: Communicate the outcome (Activated / Disabled) after toggling and when access is blocked.

## UX & Accessibility
- **Visual cues**: Muted/opaque row for inactive users.

- **Interaction model**: Entire row is non-clickable; the toggle cell remains active.

-- **Keyboard support (optional)**: Remove tab focus from disabled rows (except the toggle) and mark rows as aria-disabled for assistive tech.

## Benefits to the Application
-- **Consistency**: Same rule enforced server-side (guards + flashes) and client-side (row state).

-- **Maintainability**: No duplicated anchors or scattered conditionals; one CSS state controls behavior.

-- **Safety**: Prevents unintended edits/requests; provides immediate, clear feedback.

# Why `LEFT JOIN` when loading `User` + `Requests`

## Context
In the **User detail view**, I need to display:
- The **user’s own fields** (name, email, etc.), and
- The **list of `Request`** made by that user (if any).

## Problem with `INNER JOIN`
Using `INNER JOIN` (or `JOIN FETCH`) on `u.requests` **drops the user row** when there are **zero** requests.  
Result: the **user detail view does not render** for users without `Request`.

## Decision
Use **`LEFT JOIN FETCH`** on `u.requests` to:
- **Return the `User` even when they have no `Request`** (cardinality 0..*),
- **Avoid the N+1** by eager-loading the collection, and
- Let the view **render user details**, and if the list is empty, show a **flash**: “This user has no Requests yet.”

## Query (JPQL)
- **jpql**
`select distinct u from User u left join fetch u.requests r where u.id = :id `

# ADR: Role-Based Security with Annotations (Spring Security)


## 1) Decision
Adopt **role-based, declarative method security** using annotations (e.g., `@PreAuthorize`, `@Secured`, `@RolesAllowed`) and enable method security in configuration. Apply guards at controller and service boundaries.

## 2) Why Role-Based Access Control (RBAC)
- Mirrors business roles (`ADMIN`, `SUPERVISOR`, `CONCIERGE`) and permissions.
- Centralizes authorization rules; easier to reason about and audit.
- Reduces accidental privilege exposure across features.

## 3) Why Annotations (vs programmatic checks)
- **Separation of concerns:** Authorization policy sits with the method it protects, not inside request-handling logic.
- **Web-layer independent:** Works in services and other layers, not only in controllers.
- **Auditable & readable:** One can scan annotations to understand policy quickly.
- **Less boilerplate / fewer bugs:** Avoids repeated `if (hasRole...)` paths and missed checks.
- **Expressive:** Supports fine-grained conditions (ownership, resource state) when needed.

## 4) Role-based login + ID-scoped access
**Why**  
- Prevents **IDOR** (Insecure Direct Object Reference): a **CONCIERGE** must not open `/user/view/{id}` for someone else by changing the URL.  
- Enforces **least privilege**: ADMIN/SUPERVISOR can audit broadly; CONCIERGE is limited to their own profile.

**Positive outcomes**  
- Protects **personal data** and user-specific requests.  
- Improves **traceability/auditing** (who viewed what).  
- Reduces **attack surface** from accidental data exposure.

## 5) Controller annotations + 403 handling
**Why**  
- Method-level rules (e.g., `@PreAuthorize`) deny access even if someone **forces** a URL.  
- A **403** makes it clear the identity is valid but **not authorized** (vs. a confusing 404).

**Positive outcomes**  
- **Defense in depth**: filter chain rules + method annotations.  
- **Clear UX** on access denial.  
- **Cleaner logging** for unauthorized attempts.

## 6) `isActive` as a guard (soft-disable) to strengthen auth by role & ID
**Why**  
- Lets us **deactivate** users without deleting them, preserving historical links (Requests, Items, etc.).  
- Stops access from **inactive** accounts even if credentials are known.

**Positive outcomes**  
- Keeps **historical integrity** for reports and audits.  
- **Reversible** lifecycle (reactivate without re-creating).  
- Extra **security** if credentials leak.

## 7) Query design: email/username + password + active state
**Why**  
- Authentication must check **email (username)** and **active**; password is validated via the **password encoder** (never plain text).  
- All subsequent lookups (profile load, redirects) should consider **email/id** and, when applicable, **active**.

**Positive outcomes**  
- Avoids **inconsistencies** (e.g., logging in a disabled user).  
- Predictable **performance** with indexes on email and active flags.  
- **Consistency** across security and data access layers.

## 8) `UserSecurity` helper for ID-aware annotations
**Why**

- Centralize the check “is the **path ID** the same as the authenticated user?” for SpEL (e.g., `@PreAuthorize`).
- Enables mixed rules like: admins/supervisors can access anything, **concierge** only if it’s **their own** resource.

**Example usage (Controller and Component Security)**

```java
@PreAuthorize(
  "hasAnyRole('ADMIN','SUPERVISOR') " +
  "or (hasRole('CONCIERGE') and @userSecurity.isSelf(#id, principal.username))"
)
@GetMapping("/user/view/{id}")
public String viewUser(@PathVariable Long id, Model model) {
    // ...
    return "user/view";
}

@Component("userSecurity") 
public class UserSecurity {
	
	private final IUserService userService;

    public UserSecurity(IUserService userService) {
        this.userService = userService;
    }

    public boolean isSelf(Long pathId, String usernameOrEmail) {
        User u = userService.findByEmail(usernameOrEmail);
        return u != null && u.getId().equals(pathId);
    }

}
```

**Positive outcomes**  
- **Maintainable**: single source of truth for ID checks.  
- **Reusable & testable** across endpoints that need identity scoping.  
- **Consistent** enforcement without duplicating logic.

## 9) `onAuthenticationSuccess` for role/ID-based post-login redirects
**Why**  
- After login, each role lands on its **natural destination** (e.g., CONCIERGE → `/user/view/{ownId}`), improving flow and reducing guesswork.  
- Complements (does not replace) controller authorization.

**Positive outcomes**  
- Smoother **UX** post-login.  
- Fewer misguided attempts to access forbidden pages.  
- Perceived **cohesion** between authentication and authorization.

## 10) Role-aware UI in views (Thymeleaf) to hide critical actions
**Why**  
- Aligns the **frontend** with backend authorization so users only see actions they’re allowed to perform.  
- Prevents accidental or tempting clicks on **critical** actions (Edit/Create/Deactivate) by roles without permission.

**How**  
- Use Thymeleaf + Spring Security dialect (e.g., `sec:authorize`) and/or conditional checks:
  - Show **Edit/Create/Deactivate** only to `ADMIN`/`SUPERVISOR`.
  - For **CONCIERGE**, only show self-scoped actions when `me.id == user.id`.

**Positive outcomes**  
- **Cleaner UX** with least-privilege visibility.  
- **Defense in depth**: even if someone crafts a URL, the UI doesn’t expose dangerous controls.  
- Reduces **support noise** and user confusion around unavailable operations.

# Single-use Invitation Codes for Public Registration

## Context
I need a controlled public registration. Admins generate codes and share them with new users. Each code must be unique and single-use. When used, it must bind to the created user.

## Decision
- `InvitationCode` has fields: `code` (unique), `createdBy (User)`, `createdAt`, `used (boolean)`, `usedBy (User)`, `usedAt`.
- Public endpoint `/register` requires a valid, unused code.
- Registration service flow:
  1) Normalize and find code (`trim().toUpperCase()`).
  2) If not found → reject.
  3) If used → reject.
  4) Create `User` with default role `ROLE_CONCIERGE`.
  5) Mark code as used, set `usedBy = newUser`.
- Security: `permitAll()` for GET/POST `/register`. Invitation generation restricted to `ADMIN/SUPERVISOR`.

## Alternatives considered
- Reusable org-level codes (one-to-many): rejected to prevent uncontrolled signups.
- Allow registration without codes: rejected; we need controlled onboarding.

## Consequences
- Simple onboarding with traceability (who created the code, who used it).
- Requires unique index on `invitation_code.code`.

## Data Model / Migration
- Unique index: `ALTER TABLE invitation_code ADD CONSTRAINT uk_invitation_code_code UNIQUE (code);`
- Optional index: `(created_by_user_id)`, `(used_by_user_id)`.

## Security considerations
- Validate inputs and normalize code.
- Do not expose code generation endpoints to the public.
- Keep audit fields (`createdAt`, `usedAt`, `createdBy`, `usedBy`).



# Future Implementations

## Password ✔ DONE
- **Hash Password Encryption**: User passwords will be encrypted on both ends: registration and form.

## Invitation Code ✔ DONE
- **Invitation Code**: Generates a random, one-time code from the administrator to validate on the user registration end-point.
- **New attribute for User**: When the user authenticates with the invitation code, the code is set to true in **isUsed**.

## PDF Generation
- **PDF File Generated**: When the order is completed, a PDF is generated and sent to the customer's `CustomerAccount` email.

## Role-Based Validation ✔ DONE
- **The Concierge role** only allows ordering supply items and viewing their own page based on their ID.
- **The Admin role** has access to the entire system and data manipulation.

## Disable and Enable User ✔ DONE
- **New User attribute** instead of remove an user, is best to disable an User that does not longer work in the company, to keep with historical records for Request and RequestItems due to cascade.

## Complete a SupplyItem validation to all fields.
- **Define** which attributes will be necessary for SupplyItem and validate each of one.