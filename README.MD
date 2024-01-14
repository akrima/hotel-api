
```markdown
# Hotel Management System BY Abderrahim KRIMA

A comprehensive hotel management system for handling reservations, rooms, customers, and employees within a hotel. The project aims to streamline daily hotel operations, providing a user-friendly interface for users.

## Introduction

This project is a hotel management system designed to handle various aspects of hotel operations, including reservations, room management, and user information. The system is built using Spring Boot and WebFlux, providing a reactive and scalable solution.

## Features

- **Reservation Management:** Allows users to create, update, and delete reservations.
- **Room Management:** Provides functionality for managing rooms, including fetching room details and availability.
- **User Management:** Manages user information, enabling the creation, update, and deletion of user profiles.

## Technologies Used

- **Spring Boot:** The project is built using the Spring Boot framework, providing a robust and modular structure.
- **Spring WebFlux:** Utilizes the reactive programming model for scalable and efficient handling of requests.
- **R2DBC:** Implements reactive database access, allowing for non-blocking database interactions.
- **Swagger/OpenAPI:** Documentation is generated using Swagger/OpenAPI, making it easy to understand and test APIs.
- **Testcontainers:** Uses Testcontainers for integration testing with containerized PostgreSQL instances.
- **Gson:** Utilizes Gson for JSON serialization and deserialization.
- **Java 17:** The project is developed using Java 17.

### Prerequisites

Make sure you have the following installed:

- Java 17
- Maven
- Docker (for running PostgreSQL in a container)

### Installation

1. Clone this repository locally.

```bash
git clone https://github.com/your-username/hotel-management-system.git
cd hotel-management-system
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
java -jar target/hotel-0.0.1-SNAPSHOT.jar
```

The application should be running on `http://localhost:8080`.

## Usage

The hotel management system provides a set of RESTful APIs for managing reservations, rooms, and users. You can explore the APIs using Swagger UI, which is accessible at `http://localhost:8080/swagger-ui.html`.

## Endpoints

- **Reservations:**
  - `GET /reservations/{id}` - Get reservation by ID
  - `GET /reservations` - Get all reservations
  - `GET /reservations/user/{userId}` - Get reservations by user ID
  - `POST /reservations` - Create a new reservation
  - `PUT /reservations` - Update a reservation
  - `DELETE /reservations/{id}` - Delete a reservation

- **Rooms:**
  - `GET /rooms/{id}` - Get room by ID
  - `GET /rooms` - Get all rooms
  - `GET /rooms/available` - Get all available rooms
  - `POST /rooms` - Create a new room
  - `PUT /rooms` - Update a room
  - `DELETE /rooms/{id}` - Delete a room

- **Users:**
  - `GET /users/{id}` - Get user by ID
  - `GET /users` - Get all users
  - `POST /users` - Create a new user
  - `PUT /users` - Update a user
  - `DELETE /users/{id}` - Delete a user