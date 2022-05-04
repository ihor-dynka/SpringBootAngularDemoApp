package com.spring.demo.services;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.persistence.entities.UserEntity;
import org.modelmapper.ModelMapper;

public class UserMapper {

	public static UserDto convertToUserDto(UserEntity userEntity) {
		return new UserDto(userEntity.getUserId(),
			Role.getRoleByName(userEntity.getRole().getRoleName()),
			userEntity.getUsername(),
			userEntity.getPassword(),
			userEntity.getEmail(),
			userEntity.getFirstname(),
			userEntity.getLastname(),
			userEntity.getSecurityQuestion(),
			userEntity.getSecurityAnswer());
	}

	public static UserEntity convertToUserEntity(UserDto userDto) {
		var modelMapper = new ModelMapper();
		modelMapper.typeMap(UserDto.class, UserEntity.class)
			.addMappings(mapper -> mapper.map(UserDto::getId, UserEntity::setUserId));

		return modelMapper.map(userDto, UserEntity.class);
	}
}
