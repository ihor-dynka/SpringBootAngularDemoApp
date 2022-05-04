package com.spring.demo.integration_test;

import com.spring.demo.dto.UserDto;
import com.spring.demo.test_data.UserDtoFactory;
import io.restassured.RestAssured;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerTest {

	private static final String BASE_URI = "http://localhost";
	private static final String BASE_PATH = "/v1/api/users";

	@LocalServerPort
	private int port;

	@BeforeEach
	void beforeEachTest() {
		var auth = new BasicAuthScheme();
		auth.setUserName("admin");
		auth.setPassword("P@ssw0rd");

		RestAssured.requestSpecification = new RequestSpecBuilder()
			.setBaseUri(BASE_URI)
			.setBasePath(BASE_PATH)
			.setPort(port)
			.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()))
			.setContentType(ContentType.JSON)
			.setAuth(auth)
			.build();
	}

	@AfterEach
	void tearDown() {
		RestAssured.reset();
	}

	@Test
	void GET_Users_UserId_200_OK() {
		var userId = 1;

		UserDto user = RestAssured.given()
			.when()
			.get(String.valueOf(userId))
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(UserDto.class);

		Assertions.assertThat(user.getId()).isEqualTo(userId);
	}

	@Test
	void POST_Users_User_201_CREATED() {
		var user = UserDtoFactory.getUser();

		var createdUser = postUsersUser(user)
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.as(UserDto.class);

		Assertions.assertThat(createdUser)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(user);
	}

	@Test
	void PUT_Users_UserId_204_OK() {
		var createdUser = createNewUser(UserDtoFactory.getAdmin());

		var newUser = UserDtoFactory.getUser()
			.setId(null)
			.setUsername("updated")
			.setEmail("updated@gmail.com");

		var updatedUser = putUsersUserId(createdUser.getId(), newUser)
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(UserDto.class);

		Assertions.assertThat(updatedUser)
			.usingRecursiveComparison()
			.ignoringFields("id")
			.isEqualTo(newUser);
	}

	private UserDto createNewUser(UserDto user) {
		return postUsersUser(user)
			.then()
			.statusCode(HttpStatus.CREATED.value())
			.extract()
			.as(UserDto.class);
	}

	private Response postUsersUser(UserDto user) {
		return RestAssured
			.given()
			.body(user)
			.post();
	}

	private Response putUsersUserId(int userId, UserDto user) {
		return RestAssured
			.given()
			.body(user)
			.put(String.valueOf(userId));
	}
}
