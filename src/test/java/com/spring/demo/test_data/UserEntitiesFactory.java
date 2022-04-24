package com.spring.demo.test_data;

import com.spring.demo.enums.Role;
import com.spring.demo.persistence.entities.UserEntity;

public class UserEntitiesFactory {

	public static UserEntity getAdminUserEntity() {
		return new UserEntity(
			Role.ADMIN,
			"test",
			"P@ssw0rd",
			"test@gmail.com",
			"test",
			"test",
			"test",
			"test");
	}

	public static UserEntity getGuestUserEntity() {
		return new UserEntity(
			Role.GUEST,
			"test",
			"P@ssw0rd",
			"test@gmail.com",
			"test",
			"test",
			"test",
			"test");
	}

	public static UserEntity getUserUserEntity() {
		return new UserEntity(
			Role.GUEST,
			"test",
			"P@ssw0rd",
			"test@gmail.com",
			"test",
			"test",
			"test",
			"test");
	}
}
