package com.spring.demo.unit_test.repository;

import com.spring.demo.AbstractBaseTest;
import com.spring.demo.enums.Role;
import com.spring.demo.persistence.entities.RoleEntity;
import com.spring.demo.persistence.repositories.RolesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class RolesRepositoryTest extends AbstractBaseTest {

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void testSaveRole() {
		var role = testEntityManager.persistAndFlush(new RoleEntity(Role.TEST_USER));

		Assertions.assertThat(rolesRepository.existsById(role.getRoleId())).isTrue();
	}

	@Test
	void testRemoveRole() {
		var role = testEntityManager.persistAndFlush(new RoleEntity(Role.TEST_USER));

		testEntityManager.remove(role);

		Assertions.assertThat(rolesRepository.existsById(role.getRoleId())).isFalse();
	}

	@Test
	void testFindAllRoles() {
		var roleEntityList = rolesRepository.findAll();
		var role = testEntityManager.persistAndFlush(new RoleEntity(Role.TEST_USER));

		Assertions.assertThat(rolesRepository.findAll())
			.hasSize(roleEntityList.size() + 1)
			.contains(role);
	}
}
