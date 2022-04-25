package com.spring.demo.unit_tests.controllers;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.services.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UsersControllerTest {

	private static final UserDto USER_DTO = UserDto.builder()
		.id(1)
		.username("test_username")
		.password("test_password")
		.email("test_email@gmail.com")
		.role(Role.USER)
		.firstName("test_firstname")
		.lastName("test_lastname")
		.securityQuestion("test")
		.securityAnswer("test")
		.build();

	private static final UserDto ADMIN_DTO = UserDto.builder()
		.id(1)
		.username("test_username")
		.password("test_password")
		.email("test_email@gmail.com")
		.role(Role.ADMIN)
		.firstName("test_firstname")
		.lastName("test_lastname")
		.securityQuestion("test")
		.securityAnswer("test")
		.build();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Nested
	class GetUser {
		@Test
		void GET_Users_UserId_200_OK() throws Exception {
			given(userService.getUserById(anyInt())).willReturn(USER_DTO);

			mockMvc.perform(get("/v1/api/users/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(USER_DTO.getId()))
				.andExpect(jsonPath("username").value(USER_DTO.getUsername()))
				.andExpect(jsonPath("password").value(USER_DTO.getPassword()))
				.andExpect(jsonPath("email").value(USER_DTO.getEmail()))
				.andExpect(jsonPath("role").value(USER_DTO.getRole().getName().toUpperCase()))
				.andExpect(jsonPath("firstName").value(USER_DTO.getFirstName()))
				.andExpect(jsonPath("lastName").value(USER_DTO.getLastName()))
				.andExpect(jsonPath("securityQuestion").value(USER_DTO.getSecurityQuestion()))
				.andExpect(jsonPath("securityAnswer").value(USER_DTO.getSecurityAnswer()));
		}

		@Test
		void GET_Users_UserId_NonExistingUser_404_NOT_FOUND() throws Exception {
			given(userService.getUserById(anyInt())).willThrow(UserNotFoundException.class);

			mockMvc.perform(get("/v1/api/users/100").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		}
	}

	@Nested
	class GetUsers{

		@Test
		void GET_Users_200_OK() throws Exception {
			given(userService.getAllUsers()).willReturn(List.of(ADMIN_DTO, USER_DTO));

			mockMvc.perform(get("/v1/api/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}

		@Test
		void GET_Users_Role_RoleId_200_OK() throws Exception {
			given(userService.getUsersByRole(ADMIN_DTO.getRole())).willReturn(List.of(ADMIN_DTO));

			mockMvc.perform(get("/v1/api/users/role/admin").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}
	}
}
