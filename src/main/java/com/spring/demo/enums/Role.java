package com.spring.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Role {

	ADMIN("admin"),
	USER("user"),
	GUEST("guest");

	private String name;

	public static Role getRoleByName(String name) {
		return Stream.of(Role.values())
			.filter(role -> role.getName().equals(name))
			.findFirst()
			.orElseThrow(IllegalAccessError::new);
	}
}
