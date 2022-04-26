package com.spring.demo.services;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.persistence.entities.UserEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Cacheable("users")
	public List<UserDto> getAllUsers() {
		List<UserEntity> all = userRepository.findAll();
		return all
			.stream()
			.map(UserMapper::convertToUserDto)
			.collect(Collectors.toList());
	}

	@Cacheable("users")
	public UserDto getUserById(int id) {
		Optional<UserEntity> userEntity = userRepository.findById(id);
		return UserMapper.convertToUserDto(userEntity.orElseThrow(() -> new UserNotFoundException(id)));
	}

	@Cacheable("users")
	public List<UserDto> getUsersByRole(Role role) {
		List<UserEntity> usersEntities = userRepository.findAllByRole_RoleName(role.getName());
		return usersEntities.stream().map(UserMapper::convertToUserDto).collect(Collectors.toList());
	}

	public UserDto createNewUser(UserDto userDto) {
		UserEntity userEntity = UserMapper.convertToUserEntity(userDto);
		userRepository.saveAndFlush(userEntity);
		return UserMapper.convertToUserDto(userEntity);
	}

	public void deleteUserById(int userId) {
		userRepository.deleteUserEntityByUserId(userId);
	}

	public void deleteUserByUsername(String userName) {
		userRepository.deleteUsersEntityByUsername(userName);
	}
}
