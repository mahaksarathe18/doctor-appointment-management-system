####################################################################################
# 🏥 Doctor Appointment Management System
#############################################################################

**Microservices Architecture | Spring Boot | JWT | Stripe | Resilience4j**

_____________________________
## 📌 Overview
----------------------------------
This project is a Doctor Appointment Management System built using Microservices Architecture with Spring Boot. It demonstrates how real-world distributed systems are designed with secure APIs, service-to-service communication, fault tolerance, and payment integration.

The application is divided into independent services to ensure scalability, modularity, and maintainability. Each service handles a specific responsibility such as booking appointments, managing doctors and patients, authentication, and payments.
---
________________________
## 🏗️ Architecture
-------------------------------
It consists of the following services:

🔐 Authentication Service – Handles user registration, login, and JWT-based authentication & authorization.
👨‍⚕️ Doctor Service – Manages doctor profiles, availability, and related information.
🧑 Patient Service – Manages patient records and details.
📅 Booking Service – Handles appointment scheduling and communicates with other services.
💳 Payment Service – Processes payments using Stripe integration.
🌐 API Gateway – Acts as a single entry point for all client requests and routes them to appropriate services.
🧭 Eureka Server – Service discovery mechanism for dynamic service registration and communication.

________________________________
🔄 Communication Flow
---------------------------
- All client requests go through the API Gateway.
- Services register themselves with Eureka Server.
- Inter-service communication is handled using OpenFeign.
- APIs are secured using Spring Security with JWT tokens.
- Resilience4j Circuit Breaker ensures fault tolerance in service communication.
---
_____________________________________
## 🔐 Security Implementation
-----------------------------------

- Implemented **Spring Security**
- JWT-based Authentication & Authorization
- Stateless session management
- Token validation for protected endpoints

_________________________________
## ⚙️ Tech Stack
----------------------------

| Category                           | Technology |
|------------------------------------|------------|
| Backend                            | Spring Boot |
| Security                           | Spring Security + JWT |
| Service Discovery                  | Netflix Eureka |
| Inter-Service Communication        | OpenFeign |
| Resilience                         | Resilience4j Circuit Breaker |
| Payment Integration                | Stripe |
| ORM                                | Hibernate JPA |
| Database                           | MySQL |
| Testing                            | JUnit |
| Build Tool                         | Maven |

___________________________________
## 🧠 Key Features
--------------------------------------

- Microservices-based architecture  
- JWT-secured REST APIs  
- Service discovery using Eureka  
- Feign client for inter-service communication  
- Circuit Breaker with fallback mechanisms  
- Stripe payment integration  
- MySQL database with Hibernate JPA  
- Unit testing using JUnit  

______________________________
## 📂 Project Structure
----------------------

doctor-appointment-system/
│
├── eureka-server/        # Service Discovery (Eureka Server)
├── api-gateway/          # API Gateway (Single entry point for all requests)
├── auth-service/         # Authentication & JWT Security Service
├── doctor-service/       # Doctor Management Microservice
├── patient-service/      # Patient Management Microservice
├── booking-service/      # Appointment Booking Microservice
├── payment-service/      # Payment Processing Service (Stripe Integration)
└── README.md             # Project Documentation
