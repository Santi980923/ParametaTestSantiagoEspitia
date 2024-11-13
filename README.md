# Spring Boot SOAP Web Service

A Spring Boot SOAP Web Service application that implements a REST to SOAP bridge for employee management. The application provides functionality to create and manage employee records, calculate service time, and determine the age of employees.

## Features

- REST to SOAP bridge implementation
- Employee record creation and management
- Automatic calculation of service time and age
- Docker containerization for easy deployment
- MySQL database integration
- Comprehensive error handling

## Prerequisites

Before running the application, make sure you have the following installed:

- Docker (version 20.10 or later)
- Docker Compose (version 2.0 or later)
- Java Development Kit (JDK) 17
- Maven (optional, for local development)

## Tech Stack

**Backend:**
- Java (OpenJDK 17)
- Spring Boot
- SOAP Web Services
- JPA/Hibernate

**Database:**
- MySQL 8

**Build Tools:**
- Maven
- Docker
- Docker Compose

## Getting Started

1. **Clone the repository**
   ```shell
   git clone https://github.com/Santi980923/ParametaTestSantiagoEspitia.git
   ```
2. **Navigate to the root directory:**
   ```shell
   cd SpringBootSoapWebService
   ```
3. **Start the application using Docker**
   ```shell
   docker-compose up --build
   ```
4. **Access the application:**
   Once the application is running, you go to:
   ```shell
   http://localhost:8080/employee
   ```   
   This will allow you to send GET requests to the endpooint


## API Documentation

**Create Employee**
- Endpoint: GET /employee
- Content-Type: application/json

**Request Body Example:**
   ```shell
  {
    "nombre": "John",
    "apellido": "Doe",
    "tipoDocumento": "CC",
    "numeroDocumento": "123456789",
    "fechaNacimiento": "1990-01-01",
    "fechaVinculacion": "2020-01-01",
    "cargo": "Developer",
    "salario": 5000.00
  }
 ```
**Response Example:**
   ```shell
  {
    "employee": {
        "nombre": "John",
        "apellido": "Doe",
        "tipoDocumento": "CC",
        "numeroDocumento": "123456789",
        "fechaNacimiento": "1990-01-01",
        "fechaVinculacion": "2020-01-01",
        "cargo": "Developer",
        "salario": 5000.00
    },
    "tiempoVinculacion": "3 años, 10 meses",
    "edad": "33 años"
}

 ```