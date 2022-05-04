package com.spring.demo.test_data;

import com.spring.demo.enums.Role;
import com.spring.demo.persistence.entities.UserEntity;

public class UserEntitiesFactory {

	public static UserEntity getAdminUserEntity() {
		return new UserEntity(
			Role.ADMIN,
			"test_admin_username",
			"P@ssw0rd",
			"test_admin_user@gmail.com",
			"test_admin_firstname",
			"test_admin_user_lastname",
			"test",
			"test");
	}

	public static UserEntity getGuestUserEntity() {
		return new UserEntity(
			Role.GUEST,
			"test_guest_username",
			"P@ssw0rd",
			"test_guest@gmail.com",
			"test_guest_firstname",
			"test_guest_lastname",
			"test",
			"test");
	}

	public static UserEntity getUserUserEntity() {
		return new UserEntity(
			Role.GUEST,
			"test_user_username",
			"P@ssw0rd",
			"test_user@gmail.com",
			"test_user_firstname",
			"test_user_lastname",
			"test",
			"test");
	}
}
