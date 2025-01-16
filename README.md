# Daily Project

Daily Project is a backend application based on **Spring Boot**, designed to manage projects, tasks, collaborators, and permissions. It implements a RESTful API with JWT authentication, version control in the database using Flyway, unit testing, and automatic documentation with OpenAPI. The application is containerized using Docker for easier deployment and includes a robust roles and permissions system.

## Technologies Used
- **Spring Boot 3.3.4**
- **Java 21**
- **PostgreSQL** as the database.
- **Flyway** for database migrations.
- **Spring Security** with JWT authentication.
- **Spring Data JPA** for the persistence layer.
- **ModelMapper** for DTO mapping.
- **Springdoc OpenAPI** for API documentation.
- **Jacoco** for test coverage.
- **Docker** to containerize the application and database.

**Note:** The frontend of the application is developed using Angular.

## Key Features
1. **Authentication and Authorization:**
   - JWT-based authentication.
   - User roles and permissions.
2. **Project and Task Management:**
   - CRUD for projects, tasks, collaborators, and tags.
   - Role assignment for project collaborators.
   - Task change tracking.
3. **API Documentation:**
   - Automatically generated documentation with OpenAPI.
4. **Testing:**
   - Unit tests with JUnit and Mockito.
   - Test coverage with Jacoco.
5. **Deployment:**
   - Application and database containerization with Docker.
   - Environment configuration with Docker Compose.

## Requirements to Run the Project
- Java 21.
- PostgreSQL.
- Docker (optional for local deployment).

## Usage Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/rafaelgalvezg/daily-project-api.git
   ```
2. Start the database using Docker Compose:
   ```bash
   docker-compose -f docker-compose.local.yaml up -d
   ```
3. Run the application using Spring Boot (Flyway will run the migrations automatically):
   ```bash
   ./gradlew bootRun
   ```
4. Access the API documentation at `http://localhost:9090/swagger-ui.html`

## How to Add and Run Migrations with Flyway
To create a new migration:
```bash
./gradlew createMigration -Pdesc="migration_description"
```
This will generate an SQL file in the `src/main/resources/db/migration` directory with a timestamp and the specified description.

To manually run the migrations (if necessary):
```bash
./gradlew flywayMigrate
```

## How to Create and Execute Rollbacks for Migrations
To create a rollback file for the last executed migration:
```bash
./gradlew createRollback
```
This will generate a rollback file in the same directory as the migrations, based on the last migration file.

**Note:** Rollbacks must be executed manually, as the `undo` feature of Flyway is a premium feature not available in the free version.

## Frontend Repository
The frontend repository for this project can be found at the following link: [`https://github.com/rafaelgalvezg/daily-project-web.git`]

## Pending Tasks (Backlog)
- Implement integration tests for controllers.
- Add custom logs.
- Implement notifications (email, websockets).
- Create a metrics dashboard using Spring Boot Actuator.
- Improve security by annotating controllers with `@PreAuthorize` and respective roles.
- Complete the `change tracking` feature.

## License
This project is licensed under the MIT license. See the `LICENSE` file for more details.

