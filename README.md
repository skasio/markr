# Markr - Marking as a Service

**Szymon Szukalski's Submission to the Stile Coding Challenge - July 2024**

## Running the Project

This project uses Docker for containerization.

### Docker CLI Version

The project uses the latest version of the Docker CLI (version 27.1), which includes the integrated `docker compose`
command for managing multi-container Docker applications.

### Build

To build the Docker image for the application, run the following command:

```shell
docker compose build
```

This command will build the application image using the Dockerfile defined in the project.

### Test

To run the tests using Docker, use the following command:

```shell
docker compose run --rm tests
```

This command will build the Docker image (if not already built), start a container for testing, and run the tests. The
--rm flag ensures that the test container is removed after the tests complete.

### Run

To run the application, use the following command:

```shell
docker compose up postgres service
```

This command will start the application and its dependencies (like PostgreSQL) as defined in the docker-compose.yml
file.

### Cleanup

To stop the running containers and remove them along with associated volumes, use:

```shell
docker compose down -v
```

This command will stop and remove the containers and any associated volumes.