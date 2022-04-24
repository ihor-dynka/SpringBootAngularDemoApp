package com.spring.demo.dto;

import com.spring.demo.enums.Role;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
	@NotBlank
	private Integer id;
	@NotBlank
	private Role role;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String securityQuestion;
	@NotBlank
	private String securityAnswer;
}
