# game-library

## JUST CONCEPTING PHASE RIGHT NOW

A backend-focused Java project designed to practice clean application architecture using a simple database-backed system.

Unlike my Criteria API project, which focuses on advanced query construction and dynamic filtering logic, this project focuses on **application structure, CRUD operations, and separation of concerns**.

## Goal

To build a simple but well-structured backend application that manages a personal game library using a database.

## Core Features

- Add a game
- Update game details (e.g. status, hours played)
- Delete a game
- View all games
- Filter games by:
  - status (BACKLOG / COMPLETED)
  - genre
  - platform
- Sort games by title, release year, or hours played

## Architecture Focus

- Clear separation of layers:
  - Controller / input layer (CLI)
  - Service layer (business logic)
  - Repository layer (database access)
- Persistent storage using H2 database
- CRUD-based application design

## Purpose

This project is intended to strengthen backend application design skills rather than advanced query construction.
