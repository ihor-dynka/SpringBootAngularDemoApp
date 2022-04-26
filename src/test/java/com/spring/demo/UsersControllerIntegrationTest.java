package com.spring.demo;

import com.spring.demo.dto.UserDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Test
	void GET_Users_UserId_200_OK() {
		UserDto user = RestAssured.given()
			.contentType(ContentType.JSON)
			.baseUri("http://localhost:"+ port)
			.when()
			.get("/v1/api/users/1")
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract()
			.as(UserDto.class);

		Assertions.assertThat(user.getId()).isEqualTo(1);
	}
}
