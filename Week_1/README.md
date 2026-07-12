# Java Fundamentals Practice — Week 1

Practicing core Java that Spring relies on, working toward Spring Boot.
This week: `Map`/`Set` collections, custom exceptions (checked vs.
unchecked), and the `equals()`/`hashCode()` contract — built as a small
inventory + order system rather than isolated toy exercises.

## What's here
- `Ex_Week1.java` — inventory/order system:
  - `Inventory` — stores `Product`s in a `Map<String, Product>`, supports
    restocking and a low-stock report (`Set<Product>`)
  - `OrderService` — validates and places orders with a two-pass
    validate-then-mutate design, plus a frequency counter for units sold
  - Custom exceptions: `ProductNotFoundException`, `InsufficientStockException`
- `week1-notes.md` — decisions made, bugs hit, and open questions

## How to run
```bash
javac Ex_Week1.java
java Ex_Week1
```

Runs a `main()` covering: a valid order, an insufficient-stock order, an
unknown-SKU order, a restock (success + failure), a low-stock report, and
a frequency count across multiple orders.

## Next up
Week 2 — moving into Spring fundamentals (DI, `@Component`/`@Service`,
building on the manual constructor-injection pattern used here for
`OrderService(Inventory inv)`).
