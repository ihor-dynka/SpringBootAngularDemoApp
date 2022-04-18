###Build Docker Image
- `gradle bootBuildImage --imageName=spring-boot-docker`

###Start App with DB in Docker containers
- `docker-compose up`

###Swagger UI
- `http://localhost:8080/swagger-ui/index.html#/`

###Test
- `gradlew clean test`
- Jacoco HTML report -> `build/jacoco/index.html`