# Spring Boot Development Workflow

## Standard

```text
Task
 ↓
Planner
 ↓
Coder
 ↓
Reviewer
 ↓
 ├── Changes requested → Coder → Reviewer
 ↓
Verifier
 ↓
 ├── Failed → Coder → Reviewer → Verifier
 ↓
API Test Generator
 ↓
API Documenter
 ↓
Git Agent
 ↓
PR
```

## Principles

- Planning and implementation are separate responsibilities.
- Review is independent from implementation.
- Verification is evidence-based.
- API requests come from the actual DTO/controller contract.
- API documentation comes from the actual implementation.
- Commit/PR text comes from the actual final diff.
- Project instructions are the source of truth for architecture.
