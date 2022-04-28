package com.spring.demo.unit_test.service;

import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.persistence.repositories.UserRepository;
import com.spring.demo.services.UserMapper;
import com.spring.demo.services.UserService;
import com.spring.demo.test_data.UserEntitiesFactory;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceTest {

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	void getUserById() {
		var expectedUser = UserEntitiesFactory.getUserUserEntity();
		given(userRepository.findById(anyInt())).willReturn(Optional.of(expectedUser.setUserId(anyInt())));

		assertThat(userService.getUserById(expectedUser.getUserId()))
			.usingRecursiveComparison()
			.isEqualTo(UserMapper.convertToUserDto(expectedUser));
	}

	@Test
	void getUserById_whenMissingUser_UserNotFoundException() {
		var userId = RandomUtils.nextInt();
		given(userRepository.findById(userId)).willThrow(new UserNotFoundException(userId));

		Assertions.assertThatExceptionOfType(UserNotFoundException.class)
			.isThrownBy(() -> userService.getUserById(userId))
			.withMessage("User with ID " + userId + " not found.");
	}

	@Test
	void testGetUsersByRole() {
		var role = Role.USER;
		var expectedUsersList = List.of(
			UserEntitiesFactory.getUserUserEntity().setUserId(RandomUtils.nextInt()),
			UserEntitiesFactory.getUserUserEntity().setUserId(RandomUtils.nextInt()),
			UserEntitiesFactory.getUserUserEntity().setUserId(RandomUtils.nextInt())
		);

		given(userRepository.findAllByRole_RoleName(role.getName())).willReturn(expectedUsersList);

		assertThat(userService.getUsersByRole(role))
			.hasSameElementsAs(expectedUsersList.stream().map(UserMapper::convertToUserDto).collect(Collectors.toList()));
	}


	@Test
	void testCreateNewUser() {
		var expectedUser = UserEntitiesFactory.getAdminUserEntity();

		given(userRepository.saveAndFlush(any())).willReturn(expectedUser);

		assertThat(userService.createNewUser(UserMapper.convertToUserDto(expectedUser)))
			.usingRecursiveComparison()
			.isEqualTo(UserMapper.convertToUserDto(expectedUser));
	}
}
