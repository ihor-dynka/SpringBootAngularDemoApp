package com.spring.demo.controller;

import com.spring.demo.dto.UserDto;
import com.spring.demo.enums.Role;
import com.spring.demo.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(UsersController.V_1_API_USERS)
@CrossOrigin
public class UsersController {

	public static final String V_1_API_USERS = "/v1/api/users";
	public static final String USER_ID = "/{userId}";
	public static final String ROLE_ROLE = "/role/{role}";

	@Autowired
	private UserService userService;

	@Operation(summary = "Get user details by its ID")
	@GetMapping(value = USER_ID, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserById(@PathVariable int userId) {
		return userService.getUserById(userId);
	}

	@Operation(summary = "Get all users")
	@GetMapping(produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@Operation(summary = "Get all user roles")
	@GetMapping(value = ROLE_ROLE, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getUsersByRole(@PathVariable String role) {
		return userService.getUsersByRole(Role.getRoleByName(role));
	}

	@Operation(summary = "Update user details")
	@PutMapping(value = USER_ID, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(@RequestBody UserDto userDto, @PathVariable int userId) {
	}

	@Operation(summary = "Delete user by its id")
	@DeleteMapping(value = USER_ID, produces = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void createNewUser(@PathVariable int userId) {
		userService.deleteUserById(userId);
	}
}
