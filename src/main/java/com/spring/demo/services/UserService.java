package com.spring.demo.services;

import com.spring.demo.enums.Role;
import com.spring.demo.models.User;
import com.spring.demo.persistence.model.UsersEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User getUserById(int id) {
		Optional<UsersEntity> userEntity = userRepository.findById(id);
		return convertToUserDto(userEntity);
	}

	public List<User> getUsersByRole(Role role) {
		List<UsersEntity> usersEntities = userRepository.findAllByRole_RoleName(role);
		return usersEntities.stream().map(usersEntity -> convertToUserDto(Optional.ofNullable(usersEntity))).collect(Collectors.toList());
	}

	private User convertToUserDto(Optional<UsersEntity> userEntity) {
		return new User(
			userEntity.get().getUserId(),
			userEntity.get().getRole().getRoleName(),
			userEntity.get().getUsername(),
			userEntity.get().getPassword(),
			userEntity.get().getFirstname(),
			userEntity.get().getLastname(),
			userEntity.get().getSecurityQuestion(),
			userEntity.get().getSecurityAnswer());
	}
}
