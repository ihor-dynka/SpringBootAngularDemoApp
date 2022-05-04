##Backend
###Build Docker Image
- `cd backend`
- `./gradlew clean build bootBuildImage --imageName=spring-boot-docker:latest`

It includes:
- compilation
- unit test run
- integration test run
- building the app
- building the docker image

###Swagger UI
- `http://localhost:8080/swagger-ui/index.html#/`

###Test
- `gradlew clean test`
- Jacoco HTML report -> `build/jacoco/index.html`

##Frontend
- `cd frontend`
- `npm i`
- `npm serve`

###Start and stop App with DB in Docker containers
- `docker-compose up`
- `docker-compose down -v`

