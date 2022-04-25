package com.spring.demo.unit_tests.repository;

import com.spring.demo.unit_tests.AbstractBaseTest;
import com.spring.demo.enums.Role;
import com.spring.demo.persistence.entities.UserEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import com.spring.demo.test_data.UserEntitiesFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class UsersRepositoryTest extends AbstractBaseTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Nested
	class FindUserTest {

		private UserEntity user;

		@BeforeEach
		void beforeEachTest() {
			user = UserEntitiesFactory.getAdminUserEntity();

			testEntityManager.persistAndFlush(user);
		}

		@AfterEach
		void afterEachTest() {
			testEntityManager.remove(user);
		}

		@Test
		void testExistsById() {
			var existsById = userRepository.existsById(user.getUserId());

			Assertions.assertThat(existsById).isTrue();
		}

		@Test
		void testGetUsersAll() {
			var userEntities = userRepository.findAll();

			Assertions.assertThat(userEntities).contains(user);
		}

		@Test
		void testGetUserById() {
			var actualUser = userRepository.findById(user.getUserId());

			Assertions.assertThat(actualUser).get().usingRecursiveComparison().isEqualTo(user);
		}

		@Test
		void testFindUserByUsername() {
			var actualUser = userRepository.findByUsername(user.getUsername());

			Assertions.assertThat(actualUser).get().usingRecursiveComparison().isEqualTo(user);
		}

		@Test
		void testFindUserByFirstNameAndLastName() {
			var actualUser = userRepository.findByFirstnameAndLastname(user.getFirstname(), user.getLastname());

			Assertions.assertThat(actualUser).get().usingRecursiveComparison().isEqualTo(user);
		}

		@Test
		void testFindAllByRole() {
			var usersList = userRepository.findAllByRole_RoleName(Role.ADMIN.getName());

			Assertions.assertThat(usersList).allMatch(user -> user.getRole().getRoleName().equals(Role.ADMIN.getName()));
		}
	}

	@Nested
	class SaveUser {

		@Test
		void testSaveUser() {
			var user = UserEntitiesFactory.getGuestUserEntity();

			testEntityManager.persistAndFlush(user);

			Assertions.assertThat(userRepository.existsById(user.getUserId())).isTrue();
		}
	}

	@Nested
	class RemoveUser {

		private UserEntity user;

		@BeforeEach
		void beforeEachTest() {
			user = UserEntitiesFactory.getUserUserEntity();

			testEntityManager.persistAndFlush(user);
		}

		@Test
		void testRemoveUser() {
			testEntityManager.remove(user);

			Assertions.assertThat(userRepository.existsById(user.getUserId())).isFalse();
		}

		@Test
		void testRemoveUserById() {
			userRepository.deleteUserEntityByUserId(user.getUserId());

			Assertions.assertThat(userRepository.existsById(user.getUserId())).isFalse();
		}

		@Test
		void testRemoveUserByUsername() {
			userRepository.deleteUsersEntityByUsername(user.getUsername());

			Assertions.assertThat(userRepository.existsById(user.getUserId())).isFalse();
		}
	}
}
