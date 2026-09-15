# Project Engineering Instructions

## Technology Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- Liquibase
- REST APIs
- Maven

## Architecture

The application follows a layered architecture:

Controller → Service → Repository → Database

Controllers handle HTTP concerns and delegate business logic to services.

Services contain business logic and use concrete service classes only.

## Controller Layer

- Controllers must extend `BaseController`.
- Controllers return `ResponseEntity<Result<T>>`.
- HTTP status must be resolved through `BaseController.resolveStatus(result)`.
- Do not duplicate error-code-to-HTTP-status mapping inside controllers.
- Controllers should not contain business logic.
- Do not expose JPA entities directly unless explicitly required.

Typical response:

```java
return ResponseEntity
        .status(resolveStatus(result))
        .body(result);
```

## Service Layer

- Services have NO interfaces.
- Use concrete service implementation classes directly.
- Service classes use the `<DomainName>Service` naming convention.
- Do NOT create `*Service` + `*ServiceImpl` pairs unless explicitly required.
- Business logic belongs in services.
- Services return `Result<T>` for expected application/business outcomes.

## Result Pattern

Success:

```java
Result.Success(data)
```

Failure:

```java
Result.Failure(apiError)
```

Do not introduce another application-level result/error pattern without explicit justification.

Expected business failures should use `ApiError` + `Result.Failure(...)`.

Unexpected technical failures may use exceptions and the project's existing global exception-handling mechanism.

Never silently swallow exceptions.

## DTOs

- DTOs are used at API boundaries.
- Do not expose entities directly from REST APIs unless explicitly required.
- Simple mappings may use static `of(...)` / `from(...)` methods on the DTO.
- Use a custom mapper when mapping is complex, involves many parameters/source objects, contains substantial logic, or is reused broadly.
- Do not introduce MapStruct unless explicitly requested.

## Mappers

The project uses custom mappers.

Before creating a mapper:

1. Search for an existing mapper for the domain.
2. Follow its conventions.
3. Reuse existing mapping utilities where appropriate.

## Spring Data JPA / Hibernate

- Use Spring Data JPA.
- Inspect existing entity relationships before changing them.
- Consider ownership, fetch strategy, cascade, orphan removal, and generated SQL.
- Avoid unnecessary `EAGER` relationships.
- Avoid N+1 queries.
- Prefer existing repository/query patterns.
- Use derived queries, JPQL, Specifications, EntityGraph, Criteria APIs, etc. according to existing project conventions.
- Do not introduce native SQL when an appropriate JPA/Spring Data solution exists.

## Transactions

- Transactions belong at appropriate service boundaries.
- Do not add `@Transactional` blindly.
- Inspect existing transaction conventions before changing them.
- Do not put business transactions in controllers.

## Database / Liquibase

- Database schema changes must use Liquibase migrations.
- Never modify the schema directly as a substitute for a migration.
- Never modify an already-applied migration unless explicitly instructed.
- Inspect current migrations and follow existing naming/versioning conventions.
- Consider foreign keys, indexes, existing data, ordering, and deployment implications.

## REST API

- Follow existing endpoint naming and HTTP semantics.
- Use request/response DTOs.
- Use Jakarta Bean Validation where appropriate.
- Avoid duplicating validation responsibilities.

## Testing

When behavior changes:

- Update relevant existing tests.
- Add tests for new behavior.
- Cover meaningful failure and edge cases.
- Prefer observable behavior over implementation details.
- Run relevant Maven tests/verification when possible.

## Existing Code First

Before implementing:

1. Search for similar functionality.
2. Inspect neighboring classes.
3. Identify established patterns.
4. Reuse existing utilities.
5. Follow existing naming and architectural conventions.

Do not introduce a new pattern simply because it is personally preferred.

## Scope Control

- Modify only files necessary for the task.
- Do not perform unrelated refactoring.
- If an unrelated issue is discovered, report it instead of silently changing it.

## Truthfulness

Agents must never claim that code was:

- compiled,
- tested,
- reviewed,
- committed,
- pushed,
- or merged

unless that action was actually performed or verified.
