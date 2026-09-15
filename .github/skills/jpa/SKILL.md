# JPA / Hibernate Skill

Use this skill for persistence-related work.

Before modifying persistence:

1. Inspect entity mappings.
2. Inspect repositories and existing queries.
3. Check fetch/cascade/orphan-removal behavior.
4. Check transaction boundaries.
5. Consider generated SQL and N+1 behavior.
6. Check whether a Liquibase migration is required.

Prefer existing Spring Data JPA patterns.

Avoid unnecessary native SQL, eager fetching, cascades, and entity loading.
