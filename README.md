# Blackjack API

**Description**: Reactive REST API for managing Blackjack games, players, bets, and player ranking, built with Spring Boot WebFlux, MongoDB, and MySQL.
The project follows a ports-and-adapters architecture with separated domain, application, and infrastructure layers.

## Exercise Statement

Develop a REST API for a Blackjack game. The application must:
- Create a new Blackjack game for a player
- Allow player actions during a game: BET, HIT, and STAND
- Retrieve the current state of a game
- Delete an existing game
- Track player statistics and deposits
- Provide a ranking of players based on game results
- Store game state in MongoDB
- Store player data and statistics in MySQL
- Implement validation, exception handling, and API documentation

## Features

- **Reactive REST API** built with Spring WebFlux
- **Blackjack game flow** with betting, hitting, standing, dealer hand resolution, and round outcomes
- **Player ranking** ordered by performance score
- **Bean Validation** for request validation
- **Global Exception Handler** for centralized API error responses
- **DTO Pattern** to separate web requests/responses from application and domain models
- **Ports and Adapters architecture**:
    - Use cases as input ports
    - Repositories and integrations as output ports
    - Infrastructure adapters for MongoDB, MySQL, and web controllers
- **Domain Events** for player resolution and round finalization
- **MongoDB persistence** for Blackjack games
- **MySQL persistence** for players, deposits, and statistics
- **Swagger/OpenAPI documentation**
- **Docker support** with Docker Compose for app, MongoDB, and MySQL
- **Test coverage** for controllers, services, and MongoDB persistence adapter

## Technologies

**Backend Framework:**
- Java 21
- Spring Boot 3.5.13
- Spring WebFlux
- Spring Validation

**Database:**
- MongoDB 7.0
- MySQL 8.0
- Spring Data MongoDB Reactive
- Spring Data R2DBC
- R2DBC MySQL

**Build Tool:**
- Maven

**Testing:**
- JUnit 5
- Mockito
- Reactor Test
- Spring Boot Test

**API Documentation:**
- Springdoc OpenAPI
- Swagger UI

**DevOps:**
- Docker
- Docker Compose
- Layered Spring Boot Docker image

**Development Tools:**
- IntelliJ IDEA
- Git/GitHub
- Postman/curl
- Lombok
- Spring Boot DevTools

## Installation and Execution

### Prerequisites

- Docker and Docker Compose installed
- Java 21 for local execution without Docker
- Maven 3.9+ or use included wrapper `./mvnw`

### 1. Clone the repository

**Using SSH:**

```bash
git clone git@github.com:federicopratico/blackjack.git
cd blackjack
```

**Using HTTPS:**

```bash
git clone https://github.com/federicopratico/blackjack.git
cd blackjack
```

### 2. Run with Docker Compose (recommended)

```bash
docker compose up --build
```

The API will be available at:

```text
http://localhost:8080
```

Docker Compose starts:
- Spring Boot application on `localhost:8080`
- MongoDB on `localhost:27017`
- MySQL on `localhost:3306`

### 3. Environment variables

When running with Docker Compose, these variables are already configured:

```env
SPRING_DATA_MONGODB_URI=mongodb://blackjack-mongodb:27017/blackjack
SPRING_R2DBC_URL=r2dbc:mysql://blackjack-mysql:3306/blackjack
SPRING_R2DBC_USERNAME=blackjack_user
SPRING_R2DBC_PASSWORD=blackjack_pass
```

For local execution, the application uses these default values:

```env
SPRING_DATA_MONGODB_URI=mongodb://localhost:27017/blackjack
SPRING_R2DBC_URL=r2dbc:mysql://localhost:3306/blackjack
SPRING_R2DBC_USERNAME=blackjack_user
SPRING_R2DBC_PASSWORD=blackjack_pass
```

### 4. Run locally without Docker for the app

Start MongoDB and MySQL first, then run:

```bash
./mvnw spring-boot:run
```

Or, if Maven is installed globally:

```bash
mvn spring-boot:run
```

### 5. Run tests with Maven

```bash
./mvnw clean test
```

Or:

```bash
mvn clean test
```

## Demo

### Game Endpoints

#### Create Game (POST)

```bash
curl -X POST http://localhost:8080/games \
  -H "Content-Type: application/json" \
  -d '{"playerName":"Federico"}'

# Response: 201 Created
{
  "gameId": "665f6d8b1f4a4c2b9a123456",
  "gameStatus": "CREATED"
}
```

#### Make a Bet (POST)

```bash
curl -X POST http://localhost:8080/games/665f6d8b1f4a4c2b9a123456 \
  -H "Content-Type: application/json" \
  -d '{"playType":"BET","betAmount":10}'

# Response: 200 OK
{
  "gameId": "665f6d8b1f4a4c2b9a123456",
  "gameStatus": "IN_PROGRESS",
  "roundOutcome": null,
  "bet": 10,
  "playerStatus": "PLAYING",
  "playerHandValue": 14,
  "dealerHandValue": 10
}
```

#### Hit (POST)

```bash
curl -X POST http://localhost:8080/games/665f6d8b1f4a4c2b9a123456 \
  -H "Content-Type: application/json" \
  -d '{"playType":"HIT","betAmount":10}'

# Response: 200 OK
{
  "gameId": "665f6d8b1f4a4c2b9a123456",
  "gameStatus": "IN_PROGRESS",
  "roundOutcome": null,
  "bet": 10,
  "playerStatus": "PLAYING",
  "playerHandValue": 18,
  "dealerHandValue": 10
}
```

#### Stand (POST)

```bash
curl -X POST http://localhost:8080/games/665f6d8b1f4a4c2b9a123456 \
  -H "Content-Type: application/json" \
  -d '{"playType":"STAND","betAmount":10}'

# Response: 200 OK
{
  "gameId": "665f6d8b1f4a4c2b9a123456",
  "gameStatus": "FINISHED",
  "roundOutcome": "WIN",
  "bet": 10,
  "playerStatus": "STAND",
  "playerHandValue": 18,
  "dealerHandValue": 22
}
```

#### Get Game Details (GET)

```bash
curl http://localhost:8080/games/665f6d8b1f4a4c2b9a123456

# Response: 200 OK
{
  "gameId": "665f6d8b1f4a4c2b9a123456",
  "gameStatus": "FINISHED",
  "bet": 10,
  "playerStatus": "STAND",
  "playerHandValue": 18,
  "dealerHandValue": 22,
  "roundOutcome": "WIN"
}
```

#### Delete Game (DELETE)

```bash
curl -X DELETE http://localhost:8080/games \
  -H "Content-Type: application/json" \
  -d '{"gameId":"665f6d8b1f4a4c2b9a123456"}'

# Response: 204 No Content
```

### Player Endpoints

#### Get Player Ranking (GET)

```bash
curl http://localhost:8080/players

# Response: 200 OK
[
  {
    "position": 1,
    "playerId": "8f4c7c2d-1d32-4a5b-a2f5-123456789abc",
    "name": "Federico",
    "gamesPlayed": 5,
    "gamesWon": 3,
    "gamesLost": 1,
    "gamesDrawn": 1,
    "score": 60.00,
    "deposit": 120.00
  }
]
```

### API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI JSON is available at:

```text
http://localhost:8080/v3/api-docs
```

## Diagrams and Technical Decisions

### Ports and Adapters Architecture

```text
┌─────────────────────────────────────────────────────┐
│                   Client (HTTP)                     │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                 WEB / INFRASTRUCTURE                │
│  - GameController                                   │
│  - PlayerController                                 │
│  - Web DTOs and Web Mappers                         │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                  APPLICATION LAYER                  │
│  - CreateGameUseCase                                │
│  - MakePlayUseCase                                  │
│  - GetGameUseCase                                   │
│  - DeleteGameUseCase                                │
│  - GetPlayersRankingUseCase                         │
│  - Application services and handlers                │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                    DOMAIN LAYER                     │
│  - Game                                             │
│  - Player                                           │
│  - Card, Hand, Shoe, Dealer, Seat                   │
│  - Blackjack rules and payout calculation           │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│               PERSISTENCE ADAPTERS                  │
│  - MongoGameRepositoryAdapter                       │
│  - PlayerRepositoryAdapter                          │
│  - PlayerReservationAdapter                         │
└──────────────────────┬──────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────┐
│                 DATABASES                           │
│  - MongoDB: games                                   │
│  - MySQL: players and statistics                    │
└─────────────────────────────────────────────────────┘
```

### Persistence Model

```text
┌──────────────────────────────────────┐
│                MongoDB               │
├──────────────────────────────────────┤
│ Collection: games                    │
│ - gameId                             │
│ - gameStatus                         │
│ - dealer                             │
│ - seats                              │
│ - hands                              │
│ - shoe                               │
│ - roundOutcome                       │
└──────────────────────────────────────┘

┌──────────────────────────────────────┐
│                 MySQL                │
├──────────────────────────────────────┤
│ Table: players                       │
│ - id                                 │
│ - name                               │
│ - deposit                            │
│ - reserved_deposit                   │
│ - games_played                       │
│ - games_won                          │
│ - games_lost                         │
│ - games_drawn                        │
└──────────────────────────────────────┘
```

### Request/Response Flow - POST /games/{gameId}

```text
POST /games/{gameId}
{"playType":"HIT","betAmount":10}
         │
         ▼
┌────────────────────────┐
│  1. GameController     │
│  @PostMapping          │
│  @Valid input          │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  2. MakePlayService    │
│  Load game             │
│  Execute play          │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  3. Game Domain        │
│  Apply Blackjack rules │
│  Update hand/status    │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  4. Domain Events      │
│  Resolve player/round  │
│  Update statistics     │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│  5. Persistence        │
│  Save game in MongoDB  │
│  Update player in MySQL│
└───────────┬────────────┘
            │
            ▼
  MakePlayResponse
  200 OK
```

### Technical Decisions

**1. MongoDB for Game State**
- Blackjack games contain nested structures such as dealer, seats, hands, cards, and shoe.
- MongoDB fits this aggregate-style persistence model naturally.
- The game can be stored and loaded as a complete document.

**2. MySQL for Players and Ranking**
- Player data is relational and statistics-based.
- MySQL is used for deposits, reserved deposits, and historical counters.
- Ranking queries benefit from structured tabular data.

**3. Reactive Stack**
- Spring WebFlux, Reactive MongoDB, and R2DBC MySQL are used to keep the persistence and API flow non-blocking.
- Controllers return `Mono` and `Flux`.

**4. Ports and Adapters**
- Application use cases define the operations available to the outside world.
- Infrastructure adapters implement persistence and web concerns.
- The domain remains independent from frameworks and databases.

**5. Domain Events**
- Events such as player resolution and round finalization decouple game flow from player/statistics updates.
- Handlers react to domain events without mixing responsibilities into controllers.

**6. DTO and Mapper Separation**
- Web request/response DTOs are separated from application commands and domain objects.
- Mappers translate between layers and avoid leaking persistence or domain internals through the API.

**7. Docker Compose Setup**
- The project provides a complete local environment with the API, MongoDB, and MySQL.
- Environment variables configure the application differently for Docker and local execution.

## Project Structure

```text
src/
├── main/
│   ├── java/.../blackjack/
│   │   ├── config/
│   │   │   └── OpenApiConfig.java
│   │   ├── game/
│   │   │   ├── application/
│   │   │   │   ├── dto/
│   │   │   │   ├── handler/
│   │   │   │   ├── port/
│   │   │   │   └── service/
│   │   │   ├── domain/
│   │   │   │   ├── entity/
│   │   │   │   ├── service/
│   │   │   │   └── valueobject/
│   │   │   └── infrastructure/
│   │   │       ├── persistence/mongo/
│   │   │       └── web/
│   │   ├── player/
│   │   │   ├── application/
│   │   │   │   ├── dto/
│   │   │   │   ├── handler/
│   │   │   │   ├── port/
│   │   │   │   └── service/
│   │   │   ├── domain/
│   │   │   │   ├── entity/
│   │   │   │   └── valueobject/
│   │   │   └── infrastructure/
│   │   │       ├── persistence/mysql/
│   │   │       └── web/
│   │   └── share/
│   │       ├── event/
│   │       └── infrastructure/
│   └── resources/
│       ├── application.properties
│       └── schema.sql
└── test/
    ├── java/.../blackjack/
    │   └── game/
    │       ├── application/service/
    │       ├── infrastructure/persistence/mongo/
    │       └── infrastructure/web/controller/
    └── resources/
```

---

**Author**: Federico Praticò  
**Course**: IT Academy - Spring Framework Specialization  
**Exercise**: Sprint 5 - Blackjack API
