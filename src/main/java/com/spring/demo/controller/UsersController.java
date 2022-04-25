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
@RequestMapping("/v1/api/users")
public class UsersController {

	@Autowired
	private UserService userService;

	@Operation(summary = "Get user details by its ID")
	@GetMapping(value = "/{userId}", consumes = APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public UserDto getUserById(@PathVariable int userId) {
		return userService.getUserById(userId);
	}

	@GetMapping()
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/role/{role}")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDto> getUsersByRole(@PathVariable String role) {
		return userService.getUsersByRole(Role.getRoleByName(role));
	}

	@PutMapping("{userId}")
	public void updateUser(@RequestBody UserDto userDto, @PathVariable int userId) {
	}

	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.OK)
	public void createNewUser(@PathVariable int userId) {
		userService.deleteUserById(userId);
	}
}
