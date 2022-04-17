package com.spring.demo.controller;

import com.spring.demo.enums.Role;
import com.spring.demo.models.User;
import com.spring.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/user")
public class UsersController {

	@Autowired
	private UserService userService;

	@GetMapping("/{userId}")
	public User getUserById(@PathVariable int userId){
		return userService.getUserById(userId);
	}

	@GetMapping("/role/{role}")
	public List<User> getUsersByRole(@PathVariable String role){
		return userService.getUsersByRole(Role.getRoleByName(role));
	}
}
