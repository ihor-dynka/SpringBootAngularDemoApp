package com.spring.demo.test_data;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;

public class UserDtoFactory {

	public static UserDto getUser() {
		return UserDto.builder()
			.username("test_user_username")
			.password("test_user_password")
			.email("test_user_email@gmail.com")
			.role(Role.USER)
			.firstName("test_user_firstname")
			.lastName("test_user_lastname")
			.securityQuestion("test")
			.securityAnswer("test")
			.build();
	}

	public static UserDto getAdmin() {
		return UserDto.builder()
			.username("test_admin_username")
			.password("test_admin_password")
			.email("test_admin_email@gmail.com")
			.role(Role.ADMIN)
			.firstName("test_admin_firstname")
			.lastName("test_admin_lastname")
			.securityQuestion("test")
			.securityAnswer("test")
			.build();
	}
}
