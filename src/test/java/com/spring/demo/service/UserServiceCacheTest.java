package com.spring.demo.service;

import com.spring.demo.enums.Role;
import com.spring.demo.persistence.repositories.UserRepository;
import com.spring.demo.services.UserService;
import com.spring.demo.test_data.UserEntitiesFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceCacheTest {

	private static final int ID = 123;
	private static final int NUMBER_OF_INVOCATIONS = 10;

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	void getAllUsers_isUsingCache() {
		var usersList = List.of(
			UserEntitiesFactory.getAdminUserEntity(),
			UserEntitiesFactory.getUserUserEntity()
		);

		given(userRepository.findAll()).willReturn(usersList);

		for (int i = 0; i < NUMBER_OF_INVOCATIONS; i++) {
			userService.getAllUsers();
		}

		then(userRepository).should(times(1)).findAll();
	}

	@Test
	void getUsersByRole_isUsingCache() {
		given(userRepository.findAllByRole_RoleName(Role.ADMIN.getName()))
			.willReturn(List.of(UserEntitiesFactory.getAdminUserEntity()));

		for (int i = 0; i < NUMBER_OF_INVOCATIONS; i++) {
			userService.getUsersByRole(Role.ADMIN);
		}

		then(userRepository).should(times(1)).findAllByRole_RoleName(Role.ADMIN.getName());
	}

	@Test
	void getUserById_isUsingCache() {
		given(userRepository.findById(ID)).willReturn(Optional.of(UserEntitiesFactory.getUserUserEntity()));

		for (int i = 0; i < NUMBER_OF_INVOCATIONS; i++) {
			userService.getUserById(ID);
		}

		then(userRepository).should(times(1)).findById(ID);
	}
}
