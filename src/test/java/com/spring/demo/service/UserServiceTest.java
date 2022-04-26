package com.spring.demo.service;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.persistence.entities.UserEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import com.spring.demo.services.UserMapper;
import com.spring.demo.services.UserService;
import com.spring.demo.test_data.UserEntitiesFactory;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	private UserDto user;

	@Nested
	class GetUser {
		@BeforeEach
		void beforeEachTest() {
			var userEntity = UserEntitiesFactory.getAdminUserEntity();

			user = UserMapper.convertToUserDto(userRepository.save(userEntity));
			userRepository.save(userEntity);
		}

		@Test
		void testUserExistsById() {
			Assertions.assertThat(userRepository.existsById(user.getId())).isTrue();
		}

		@Test
		void getUserById() {
			Assertions.assertThat(userService.getUserById(user.getId())).usingRecursiveComparison().isEqualTo(user);
		}

		@Test
		void getUserById_whenMissingUser_UserNotFoundException() {
			var id = 1234;

			Assertions.assertThatExceptionOfType(UserNotFoundException.class)
				.isThrownBy(() -> userService.getUserById(id))
				.withMessage("User with ID" + id + " not found.");

		}

		@Test
		void testGetUsersByRole() {
			List<UserDto> usersByRole = userService.getUsersByRole(Role.ADMIN);

			userRepository.findAllByRole_RoleName(Role.ADMIN.getName());
			Assertions.assertThat(usersByRole)
				.allMatch(userDto -> userDto.getRole().equals(Role.ADMIN));
		}
	}

	@Nested
	class CreateNewUser {

		@Test
		void testCreateNewUser() {
			var user = UserDto
				.builder()
				.username("user" + RandomUtils.nextInt(1, 10))
				.password("test")
				.role(Role.USER)
				.email("user" + RandomUtils.nextInt(1, 10) + "@gmail.com")
				.firstName("admin")
				.lastName("admin")
				.securityQuestion("test")
				.securityAnswer("test")
				.build();

			user = userService.createNewUser(user);

			Assertions.assertThat(userRepository.existsById(user.getId())).isTrue();
		}
	}

	@Nested
	class RemoveUser {

		private UserDto user;

		@BeforeEach
		void setUp() {
			var userEntity = UserEntitiesFactory.getAdminUserEntity();

			UserEntity save = userRepository.save(userEntity);
			user = UserMapper.convertToUserDto(save);
		}

		@Test
		void testDeleteUserById() {
			userService.deleteUserById(user.getId());

			Assertions.assertThat(userRepository.existsById(user.getId())).isFalse();
		}

		@Test
		void testDeleteUserByUsername() {
			userService.deleteUserByUsername(user.getUsername());

			Assertions.assertThat(userRepository.existsById(user.getId())).isFalse();
		}
	}
}
