# Spring Boot Skill

Use this skill when implementing or reviewing Spring Boot application code.

## Principles

- Follow the project's layered architecture.
- Keep HTTP concerns in controllers.
- Keep business logic in concrete `*Service` classes.
- Use `Result<T>` for expected application/business outcomes.
- Extend `BaseController` for REST controllers.
- Use Spring dependency injection.
- Follow existing project patterns before introducing new abstractions.
- Prefer simple, maintainable code over speculative architecture.
