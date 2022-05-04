package com.spring.demo.security;

import com.spring.demo.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var userEntity = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException(username + " user not found."));

		return User.withUsername(userEntity.getUsername())
			.password(userEntity.getPassword())
			.roles(userEntity.getRole().getRoleName())
			.build();
	}
}
