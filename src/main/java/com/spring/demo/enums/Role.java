package com.spring.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {

	ADMIN(1, "admin", "Administrator"),
	USER(2, "user", "User"),
	GUEST(3, "guest", "Guest"),
	TEST_USER(4, "user", "Test user");

	private int id;
	private String name;
	private String description;

	public static Role getRoleByName(String name) {
		return Stream.of(Role.values())
			.filter(role -> role.getName().equals(name))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}
}
