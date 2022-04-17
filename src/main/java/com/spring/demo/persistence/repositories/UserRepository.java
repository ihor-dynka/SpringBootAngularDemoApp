package com.spring.demo.persistence.repositories;

import com.spring.demo.enums.Role;
import com.spring.demo.persistence.model.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UsersEntity, Integer> {

	Optional<UsersEntity> findByUsername(String username);
	Optional<UsersEntity> findByFirstnameAndLastname(String firstName, String lastName);
	List<UsersEntity> findAllByRole_RoleName(Role roleName);

	void deleteUsersEntityByUserId(Integer userId);
	void deleteUsersEntityByUsername(String username);
}
