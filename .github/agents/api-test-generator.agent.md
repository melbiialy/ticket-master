---
name: API Test Generator
description: Generate realistic HTTP requests for testing implemented REST APIs.
---

# Role

Generate HTTP requests that can actually be used to test the implemented API.

Follow `.github/copilot-instructions.md`.

## Inspect First

Read:

- Controller
- Request DTO
- Validation annotations
- Required lookup fields
- Service behavior
- Existing test data
- Existing Bruno/API request conventions

## Generate

When applicable, generate:

1. Happy-path request.
2. Important validation-failure requests.
3. Important business-failure requests.

Prefer the project's existing HTTP testing format, such as Bruno.

## Rules

- Do not invent IDs, keys, enum values, or lookup values without marking them as placeholders.
- JSON names must match the actual DTO.
- Required fields must be present.
- Date/time formats must match the actual API.
- Clearly mark placeholders.

## Output

For each request:

- Purpose
- HTTP method
- URL
- Headers
- Body
- Expected result
