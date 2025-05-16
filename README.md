# Airline Management System

A Spring Boot application for managing airline operations including flight schedules and ticket bookings.

## Features

- Get flight schedules
- Get ticket details
- Create/book tickets
- Cancel tickets
- Input validation and duplication checks

## API Endpoints

- `GET /flights?sort=asc` - Get all flights (sorted)
- `GET /flights/{id}` - Get flight by ID
- `GET /flights/{id}/schedules?dates=` - Get flight schedules
- `POST /tickets/` - Create a ticket
- `GET /tickets/{id}` - Get ticket details
- `DELETE /tickets/{id}` - Cancel a ticket

## Prerequisites

- Java 17
- Maven 3.8+
- Docker
- Jenkins (for CI/CD pipeline)

## Local Development

1. Clone the repository
2. Build the project:
   ```bash
   mvn clean package
   ```
3. Run the application:
   ```bash
   java -jar target/airline-management-system-0.0.1-SNAPSHOT.jar
   ```

## Docker Build

```bash
docker build -t airline-management-system .
docker run -p 8080:8080 airline-management-system
```

## Jenkins Pipeline Setup

1. Install required Jenkins plugins:
   - Docker Pipeline
   - SSH Agent
   - Pipeline

2. Configure Jenkins credentials:
   - Add SSH credentials for EC2 access
   - Add Docker registry credentials if using private registry

3. Create a new Pipeline job in Jenkins:
   - Select "Pipeline script from SCM"
   - Choose Git as SCM
   - Enter repository URL
   - Set branch to build
   - Set script path to "Jenkinsfile"

4. Update Jenkinsfile:
   - Replace `your-ec2-host` with your EC2 instance hostname
   - Replace `ec2-user` with your EC2 instance username
   - Adjust Docker image name and tag if needed

5. Run the pipeline

## AWS EC2 Setup

1. Launch EC2 instance with:
   - Docker installed
   - Java 17 installed
   - Proper security group (open port 8080)
   - SSH key pair configured

2. Configure SSH access from Jenkins to EC2

## Testing

Run tests using:
```bash
mvn test
``` 