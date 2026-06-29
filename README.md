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

## Author

**Shekhar Molaj**

Java Backend Developer

LinkedIn:
https://www.linkedin.com/in/shekhar-molaj-7107ab1b2

GitHub:
https://github.com/Shekhar3783
