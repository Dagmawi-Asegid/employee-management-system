# Employee Management System

A RESTful API for full CRUD management of employee records, built with
Spring Boot and MySQL, using a layered controller → service → repository
architecture.

## Stack

- Java 21 (compiled/run on a Java 25 JDK)
- Spring Boot 3.5 (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `spring-boot-starter-validation`)
- MySQL 9 (`mysql-connector-j`)
- springdoc-openapi (Swagger UI)
- Maven

## Architecture

```
src/main/java/com/dagmawiasegid/employeemanagement/
├── controller/   EmployeeController        REST endpoints (/api/employees)
├── service/      EmployeeService (+Impl)    Business logic, validation rules
├── repository/   EmployeeRepository         Spring Data JPA data access
├── model/        Employee                   JPA entity
└── exception/    GlobalExceptionHandler,     Centralized error handling
                   ResourceNotFoundException,
                   DuplicateEmailException,
                   ApiError
```

Requests flow `Controller -> Service -> Repository -> MySQL`, each layer
only talking to the one below it.

## Employee fields

`id`, `firstName`, `lastName`, `email` (unique, validated), `jobTitle`,
`department`, `salary`, `hireDate`.

## API

Base path: `/api/employees`

| Method | Path                  | Description             | Success | Failure cases |
|--------|-----------------------|--------------------------|---------|----------------|
| GET    | `/api/employees`      | List all employees      | 200     | -              |
| GET    | `/api/employees/{id}` | Get one employee         | 200     | 404 if not found |
| POST   | `/api/employees`      | Create an employee       | 201     | 400 invalid body, 409 duplicate email |
| PUT    | `/api/employees/{id}` | Update an employee       | 200     | 400 invalid body, 404 not found, 409 duplicate email |
| DELETE | `/api/employees/{id}` | Delete an employee       | 204     | 404 if not found |

Validation (`@NotBlank`, `@Email`, `@NotNull`, `@DecimalMin`,
`@PastOrPresent`) is enforced on create/update; failures return `400`
with a `fieldErrors` map naming each invalid field.

Interactive API docs: **http://localhost:8080/swagger-ui.html**
(springdoc also serves it at `/swagger-ui/index.html`, and the raw
OpenAPI JSON at `/v3/api-docs`).

## Running it locally

### 1. Prerequisites

- JDK 17+ (developed against JDK 25)
- Maven
- MySQL running locally

### 2. Database

```bash
brew install mysql        # if not already installed
brew services start mysql
mysql -u root -e "CREATE DATABASE IF NOT EXISTS employee_db;"
```

`src/main/resources/application.properties` points at
`jdbc:mysql://localhost:3306/employee_db` with user `root` and an empty
password, which matches a stock Homebrew MySQL install (no root
password set by default). Adjust `spring.datasource.username` /
`spring.datasource.password` if your local MySQL differs. Hibernate is
set to `ddl-auto=update`, so the `employees` table is created
automatically on first run — no manual schema/migration step needed.

### 3. Run the app

```bash
mvn spring-boot:run
```

The API starts on **http://localhost:8080**.

### 4. Try it

```bash
# Create
curl -X POST http://localhost:8080/api/employees \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Abebe","lastName":"Kebede","email":"abebe.kebede@example.com","jobTitle":"Backend Engineer","department":"Engineering","salary":95000.00,"hireDate":"2023-03-15"}'

# List
curl http://localhost:8080/api/employees

# Get one
curl http://localhost:8080/api/employees/1

# Update
curl -X PUT http://localhost:8080/api/employees/1 \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Abebe","lastName":"Kebede","email":"abebe.kebede@example.com","jobTitle":"Senior Backend Engineer","department":"Engineering","salary":110000.00,"hireDate":"2023-03-15"}'

# Delete
curl -X DELETE http://localhost:8080/api/employees/1
```

Or run tests:

```bash
mvn test
```

## Status

This is a genuine, from-scratch implementation with real CRUD behavior
against a real local MySQL database — every endpoint above has been
exercised end-to-end with curl, and the Spring context test
(`EmployeeManagementSystemApplicationTests`) boots the full application
against the live database. It is a solo learning/portfolio project, not
a production deployment (no auth, no pagination, no migrations tool).
