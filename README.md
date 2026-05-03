# Game Library
Spring Boot REST API modeling a game marketplace. Users can register,
browse games, make purchases, and manage their library.

---

## Current Features

### Users
- Create, read, update, delete users
- Buy games
- Transaction history

### Games
- Basic CRUD operations

---

## Planned Features
- Authentication (login / registration)
- Transaction system improvements
- Expanded filtering & sorting
- Grid-based UI with cover art — tabs for store, library, and transaction history

---

## Architecture
- **Controller layer** – REST endpoints
- **Service layer** – business logic
- **Repository layer** – database access
- **Database** – H2 (in-memory, for local development)

---

## How to Run
```bash
./mvnw spring-boot:run
```
Then access `http://localhost:8080/users`
