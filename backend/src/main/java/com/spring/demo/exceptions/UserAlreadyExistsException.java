package com.spring.demo.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	public UserAlreadyExistsException() {
		super("User already exists");
	}

	public UserAlreadyExistsException(String message) {
		super("User already has " + message);
	}
}
