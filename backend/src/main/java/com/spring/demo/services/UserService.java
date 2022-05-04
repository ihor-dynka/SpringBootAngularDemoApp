package com.spring.demo.services;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.exceptions.UserAlreadyExistsException;
import com.spring.demo.exceptions.UserNotFoundException;
import com.spring.demo.persistence.entities.UserEntity;
import com.spring.demo.persistence.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Cacheable("users")
	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
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
		if (userRepository.findByUsername(userEntity.getUsername()).isPresent()) {
			throw new UserAlreadyExistsException(userEntity.getUsername());
		}
		userRepository.saveAndFlush(userEntity);
		return UserMapper.convertToUserDto(userEntity);
	}

	public UserDto updateUser(int userId, UserDto userDto) {
		var userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

		if (userEntity.getUsername().equals(userDto.getUsername())) {
			throw new UserAlreadyExistsException(userDto.getUsername());
		} else if (userEntity.getEmail().equals(userDto.getEmail())) {
			throw new UserAlreadyExistsException(userDto.getEmail());
		} else {
			userEntity = userRepository.saveAndFlush(UserMapper.convertToUserEntity(userDto));
		}
		return UserMapper.convertToUserDto(userEntity);
	}

	public void deleteUserById(int userId) {
		if (userRepository.existsById(userId)) {
			userRepository.deleteUserEntityByUserId(userId);
		} else {
			throw new UserNotFoundException(userId);
		}
	}
}
