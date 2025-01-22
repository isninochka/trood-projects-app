# Project Management and Vacancy API

This is a Spring Boot application designed to manage projects and associated vacancies. 
The application uses PostgreSQL as its database and provides REST APIs for interacting with the data.

## Features
- Manage Projects:
    - Create, Read, Update, and Delete projects.
- Manage Vacancies:
    - Create, Read, Update, and Delete vacancies associated with projects.

## Prerequisites
To run this application, you need the following installed:
- [Docker](https://www.docker.com/)
- [Java 21](https://jdk.java.net/21/)
- [Maven](https://maven.apache.org/)

## Setup and Run Instructions

### Step 1: Clone the Repository
```bash
git clone https://github.com/isninochka/trood-projects-app.git
cd trood-projects-app
```

### Step 2: Configure Environment Variables
Ensure your `application.properties` or `application.yml` file is correctly configured to connect to the 
PostgreSQL database. The default configuration assumes the following:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/troodprojects
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```


### Step 3: Start PostgreSQL Database with Docker
A `docker-compose.yml` file is provided for easy setup of the PostgreSQL database. 
To start the database, run the following command:

```bash
docker-compose up -d
```

This will:
1. Start a PostgreSQL container.
2. Expose the database on port `5432`.

### Step 4: Build and Run the Application
1. Package the application using Maven:
   ```bash
   mvn clean package
   ```

2. Build the Docker image for the application:
   ```bash
   docker build -t  trood-projects-app .
   ```

3. Run the application using Docker:
   ```bash
   docker run -p 8080:8080 --name trood-project-app --network host trood-projects-app
   ```

The application will now be accessible at `http://localhost:8080`.

## API Endpoints

### Projects
- `GET /projects` - Get all projects.
- `GET /projects/{id}` - Get project by Id.
- `POST /projects` - Create a new project.
- `PUT /projects/{id}` - Update a project.
- `DELETE /projects/{id}` - Delete a project.

### Vacancies
- `GET /vacancies/{projectId}` - Get all vacancies for a project.
- `POST /vacancies/{projectId}` - Create a vacancy for a project.
- `PUT /vacancies/{vacancyId}` - Update a vacancy.
- `DELETE /vacancies/{vacancyId}` - Delete a vacancy.

## Testing the Application

### Using Postman
1. Import the provided Postman collection from `postman_collection.json` (if available).
2. Ensure the server is running (`http://localhost:8080`).
3. Execute requests to test the endpoints.

### Running Unit Tests
Run the following command to execute unit tests:
```bash
mvn test
```

## Shutdown Instructions
To stop and remove the Docker containers:
```bash
docker-compose down
```

## Troubleshooting
- Ensure Docker is running before starting the database.
- Check `application.properties` for correct database credentials.
- Verify the database container logs using:
  ```bash
  docker logs <container_id>
  ```

## License
This project is licensed under the MIT License.
