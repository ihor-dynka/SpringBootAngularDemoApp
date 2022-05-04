package com.spring.demo.unit_test.repository;

import com.spring.demo.AbstractBaseTest;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.persistence.entities.RoleEntity;
import com.spring.demo.persistence.entities.UserEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import com.spring.demo.test_data.UserEntitiesFactory;
import org.assertj.core.api.Assertions;
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

	private UserEntity user;

	@BeforeEach
	void beforeEachTest() {
		user = testEntityManager.persistAndFlush(UserEntitiesFactory.getUserUserEntity());
	}

	@Nested
	class FindUserTest {

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
			var user = testEntityManager.persistAndFlush(UserEntitiesFactory.getAdminUserEntity());

			Assertions.assertThat(userRepository.existsById(user.getUserId())).isTrue();
		}
	}

	@Nested
	class UpdateUser {

		@Test
		void testUpdateUser() {
			var newUser = userRepository.findById(user.getUserId())
				.orElseThrow(() -> new UserNotFoundException(user.getUserId()));

			newUser.setRole(new RoleEntity(Role.GUEST));
			newUser.setUsername("updatedUsername");
			newUser.setFirstname("updatedFirstname");
			newUser.setLastname("updatedLastname");

			UserEntity updatedUser = userRepository.save(newUser);
			Assertions.assertThat(updatedUser).usingRecursiveComparison().isEqualTo(newUser);
		}
	}

	@Nested
	class RemoveUser {

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
	}
}
