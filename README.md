ORM Registration System
A full-stack enterprise-style Client Registration System built with Angular + Spring Boot + Hibernate following a clean layered architecture. 
The system manages clients, personal details, addresses, and account information with relational database design and RESTful API integration.
# ORM Registration System

## 📌 Overview
ORM Registration System is a full-stack web application designed to manage client onboarding, personal details, addresses, and account information. It follows a modular architecture with Angular frontend and Spring Boot backend using REST APIs.

---

## 🛠️ Tech Stack

### Frontend
- Angular (Reactive Forms, Routing)
- TypeScript
- HTML5, SCSS

### Backend
- Java 21
- Spring Boot
- Spring Data JPA (Hibernate)
- REST APIs
- Lombok

### Database
- MySQL / PostgreSQL / Oracle (configurable)

---

## 📂 Project Features

- Client registration with auto-generated ID
- Nested client details (personal information)
- Address management with cascading dropdowns
- Account management with validation rules
- Lookup system (Country → Division → District → Thana)
- REST API integration between Angular and Spring Boot
- DTO-based request/response structure
- Layered architecture (Controller → Service → Repository)

---

## 🏗️ Architecture
Angular Frontend
↓
REST API (Spring Boot Controller)
↓
Service Layer (Business Logic)
↓
Mapper Layer (DTO ↔ Entity)
↓
Repository Layer
↓
Database

---

## 🧩 Modules

- Client Module
- Client Details Module
- Address Module
- Account Module
- Lookup Module

---

## ⚙️ Setup Instructions

### Backend
```bash
mvn clean install
mvn spring-boot:run


**Key Highlights**
Clean layered architecture
Reusable service-based design
Angular reactive form validation
Database normalization with relational mapping
RESTful API integration
Scalable modular structure

**Future Improvements**
Full DTO–Mapper separation layer
Global exception handling
JWT authentication
Unit & integration test coverage
UI enhancement with Angular Material

**Author**
Full Stack Developer | Java | Angular | Spring Boot
