# SmartPay Transaction Service

AI-powered payment transaction processing microservice built using Java and Spring Boot.

## Overview

The Transaction Service is the core microservice of the SmartPay platform. It manages payment transactions by validating requests, storing transaction details in PostgreSQL, publishing transaction events to Kafka, and exposing REST APIs for CRUD operations.

---

## Features

- Create new transactions
- Retrieve transaction details
- Update existing transactions
- Delete transactions
- Publish transaction events to Kafka
- Input validation
- Global exception handling
- RESTful APIs
- JPA/Hibernate integration

---

## Tech Stack

| Technology | Version |
|------------|----------|
| Java | 21 |
| Spring Boot | 3.x |
| Spring Data JPA | Latest |
| PostgreSQL | Latest |
| Apache Kafka | Latest |
| Redis | Planned |
| Docker | Planned |
| Maven | Latest |

---

## Architecture

```
Client
   │
   ▼
REST Controller
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
PostgreSQL
   │
   ▼
Kafka Producer
   │
   ▼
Notification Service
```

---

## APIs

| Method | Endpoint | Description |
|---------|----------|-------------|
| POST | /api/transactions | Create Transaction |
| GET | /api/transactions | Get All Transactions |
| GET | /api/transactions/{id} | Get Transaction |
| PUT | /api/transactions/{id} | Update Transaction |
| DELETE | /api/transactions/{id} | Delete Transaction |

---

## Project Structure

```
src
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── exception
 └── util
```

---

## Future Enhancements

- JWT Authentication
- Redis Caching
- Swagger Documentation
- Docker Compose
- Kubernetes Deployment
- Unit & Integration Tests
- CI/CD Pipeline

---
## How to Run Locally

### Prerequisites
- Java 21
- Maven 3.9+
- Docker Desktop

### Step 1 — Start Infrastructure
git clone https://github.com/Shekhar3783/smartpay-transaction-service

cd smartpay-transaction-service

docker-compose up -d

### Step 2 — Run the Service
mvn spring-boot:run

### Step 3 — Test the API
POST http://localhost:8081/api/v1/transactions

Body:
{
  "userId": "USR-001",
  "amount": 15000,
  "merchant": "The Great Kabab Factory",
  "category": "DINING",
  "channel": "UPI"
}

Expected: 201 Created

## Author

**Shekhar Molaj**

Java Backend Developer

LinkedIn:
https://www.linkedin.com/in/shekhar-molaj-7107ab1b2

GitHub:
https://github.com/Shekhar3783
