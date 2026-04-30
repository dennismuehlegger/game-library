# Game Library

**Status: Early development**

A backend-focused Java project built with Spring Boot to practice clean architecture, CRUD operations, and separation of concerns.

---

## Overview

This project models a simple game library system where users can manage games and interact with a basic marketplace (planned).

The focus is on:

* Layered architecture (Controller → Service → Repository)
* Clean separation of concerns
* Maintainable backend design

---

## Current Features

### Users

* Create, read, update, delete users
* Buy games
* Transaction history

### Games

* Basic CRUD operations

---

## Planned Features

* Authentication for users (login / registration)
* Transaction system improvements
* Expanded filtering & sorting:
* Simple grid-based UI with cover art - tabs for transaction history, library and store

---

## Architecture

* **Controller layer** – REST endpoints
* **Service layer** – business logic
* **Repository layer** – database access
* **Database** – H2 (in-memory)

---

## How to Run

```bash
./mvnw spring-boot:run
```

Then access:

```
http://localhost:8080/users
```

---

## Purpose

This project is designed to strengthen backend engineering skills, especially:

* Structuring Spring applications
* Designing clean service layers
* Designing frontend layers in tandem with backend application

---

## Notes

This is a learning project and is actively evolving. Structure and features may change.
