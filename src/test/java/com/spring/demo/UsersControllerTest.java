package com.spring.demo;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class UsersControllerTest extends AbstractBaseTest {

	public static final String BASE_URL = "http://localhost:8080/v1/api";

	@Test
	void GET_UsersUserId_200_OK() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<UserDto> response = restTemplate.getForEntity(String.format(BASE_URL + "/users/%d", 1), UserDto.class);

		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(response.getBody()).isEqualTo(new UserDto(1,
			Role.ADMIN,
			"admin",
			"P@ssw0rd",
			"test@gmail.com",
			"Ihor",
			"Dynka",
			"test",
			"test")
		);
	}
}
