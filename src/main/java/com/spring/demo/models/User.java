package com.spring.demo.models;

import com.spring.demo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
	private Integer id;
	private Role role;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String securityQuestion;
	private String securityAnswer;
}
