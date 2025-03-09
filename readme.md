# Fetch-Receipt Proccessor by Patrick Dugan

## Overview

This github repository includes my (Patrick Dugan) submission for Fetch's [receipt-processor-challenge](https://github.com/fetch-rewards/receipt-processor-challenge/tree/main).

### Code

I chose to use the Spring Boot framework to build this REST service.  The database used is an H2 in memory application.  Database setup and maintenance is done using the tool Liquibase.

#### Noteable directories

##### src/main/java/com/dugan/fetchreceiptprocessor

Contains java code

- `/aop` exception handling
- `/entity` base entities
- `/jpa` database access repositories
- `/mapper` data and object mapping support classes
- `/service` business logic and supporting code
- `/web/rest` endpoint implementations
- `/web/dto` data transfer objects for endpoints

##### main/resources

- `applicaion.yml` project properties
- `api.yml` endpoint definitions provided by Fetch
- `/db/changelog` liquibase changesets for initializing database

##### src/test/java/com/dugan/fetchreceiptprocessor

Contains all current unit and integration tests.

### Build

The project build tool used is gradle.  Gradle is not required on the users machine as the dockerfile provides a 2 stage process building with gradle and packaging the image with a java jre.  The tool required is docker.

To build and run the application are the two following commands (run in order):

- `docker build -t fetch-receipt-processor .`
- `docker run -d -p 8080:8080 fetch-receipt-processor`

The application and endpoints defined in api.yml provided by fetch are now reachable on port 8080.


