package com.spring.demo.unit_test.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.security.AuthService;
import com.spring.demo.services.UserService;
import com.spring.demo.test_data.UserDtoFactory;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@WebMvcTest
public class UsersControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private AuthService userDetailsService;

	@BeforeEach
	void setUp() {
		given(userDetailsService.loadUserByUsername(anyString()))
			.willReturn(new User("admin", "P@ssw0rd", List.of(new SimpleGrantedAuthority(Role.ADMIN.getName()))));
	}

	@Nested
	class GetUser {
		@Test
		void GET_Users_UserId_200_OK() throws Exception {
			var userId = RandomUtils.nextInt();

			var expectedUser = UserDtoFactory.getUser();
			given(userService.getUserById(anyInt())).willReturn(expectedUser.setId(anyInt()));

			mockMvc.perform(
					get("/v1/api/users/" + userId)
						.contentType(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(expectedUser.getId()))
				.andExpect(jsonPath("username").value(expectedUser.getUsername()))
				.andExpect(jsonPath("password").value(expectedUser.getPassword()))
				.andExpect(jsonPath("email").value(expectedUser.getEmail()))
				.andExpect(jsonPath("role").value(expectedUser.getRole().getName().toUpperCase()))
				.andExpect(jsonPath("firstName").value(expectedUser.getFirstName()))
				.andExpect(jsonPath("lastName").value(expectedUser.getLastName()))
				.andExpect(jsonPath("securityQuestion").value(expectedUser.getSecurityQuestion()))
				.andExpect(jsonPath("securityAnswer").value(expectedUser.getSecurityAnswer()));
		}

		@Test
		void GET_Users_UserId_NonExistingUser_404_NOT_FOUND() throws Exception {
			var userId = RandomUtils.nextInt();

			given(userService.getUserById(anyInt())).willThrow(UserNotFoundException.class);

			mockMvc.perform(get("/v1/api/users/" + userId).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		}
	}

	@Nested
	class GetUsers {

		@Test
		void GET_Users_200_OK() throws Exception {
			given(userService.getAllUsers()).willReturn(
				List.of(UserDtoFactory.getAdmin().setId(RandomUtils.nextInt()), UserDtoFactory.getUser().setId(RandomUtils.nextInt()))
			);

			mockMvc.perform(get("/v1/api/users").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}

		@Test
		void GET_Users_Role_RoleId_200_OK() throws Exception {
			var expectedUser = UserDtoFactory.getAdmin().setId(RandomUtils.nextInt());
			given(userService.getUsersByRole(expectedUser.getRole())).willReturn(List.of(expectedUser));

			mockMvc.perform(get("/v1/api/users/role/admin").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		}
	}

	@Nested
	class CreateUser {

		@Test
		void POST_users_user_201_CREATED() throws Exception {
			var user = UserDtoFactory.getUser().setId(RandomUtils.nextInt());

			given(userService.createNewUser(any())).willReturn(user);

			mockMvc.perform(
					post("/v1/api/users")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(new ObjectMapper().writeValueAsString(user))
				)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id").value(user.getId()))
				.andExpect(jsonPath("username").value(user.getUsername()))
				.andExpect(jsonPath("password").value(user.getPassword()))
				.andExpect(jsonPath("email").value(user.getEmail()))
				.andExpect(jsonPath("role").value(user.getRole().getName().toUpperCase()))
				.andExpect(jsonPath("firstName").value(user.getFirstName()))
				.andExpect(jsonPath("lastName").value(user.getLastName()))
				.andExpect(jsonPath("securityQuestion").value(user.getSecurityQuestion()))
				.andExpect(jsonPath("securityAnswer").value(user.getSecurityAnswer()));
		}
	}

	@Nested
	class UpdateUser {
		@Test
		void PUT_users_userId_204_NOT_CONTENT() throws Exception {
			var user = UserDtoFactory.getUser().setId(RandomUtils.nextInt());
			var updatedUser = UserDtoFactory.getAdmin();
			given(userService.updateUser(user.getId(), updatedUser)).willReturn(updatedUser);

			mockMvc.perform(put("/v1/api/users/" + user.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(new ObjectMapper().writeValueAsString(updatedUser))
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").value(updatedUser.getId()))
				.andExpect(jsonPath("username").value(updatedUser.getUsername()))
				.andExpect(jsonPath("password").value(updatedUser.getPassword()))
				.andExpect(jsonPath("email").value(updatedUser.getEmail()))
				.andExpect(jsonPath("role").value(updatedUser.getRole().getName().toUpperCase()))
				.andExpect(jsonPath("firstName").value(updatedUser.getFirstName()))
				.andExpect(jsonPath("lastName").value(updatedUser.getLastName()))
				.andExpect(jsonPath("securityQuestion").value(updatedUser.getSecurityQuestion()))
				.andExpect(jsonPath("securityAnswer").value(updatedUser.getSecurityAnswer()));
		}
	}
}
