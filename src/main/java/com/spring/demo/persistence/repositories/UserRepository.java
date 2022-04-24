package com.spring.demo.persistence.repositories;

import com.spring.demo.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	Optional<UserEntity> findByUsername(String username);
	Optional<UserEntity> findByFirstnameAndLastname(String firstName, String lastName);
	List<UserEntity> findAllByRole_RoleName(String roleName);

	void deleteUserEntityByUserId(Integer userId);
	void deleteUsersEntityByUsername(String username);
}
