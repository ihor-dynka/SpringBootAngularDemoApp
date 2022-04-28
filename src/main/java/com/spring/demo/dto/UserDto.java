package com.spring.demo.dto;

import com.spring.demo.enums.Role;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class UserDto implements Copyable<UserDto> {
	@NotBlank
	private Integer id;
	@NotBlank
	private Role role;
	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String securityQuestion;
	@NotBlank
	private String securityAnswer;

	@Override
	public UserDto copy() {
		return new UserDto(id, role, username, password, email, firstName, lastName, securityQuestion, securityAnswer);
	}
}
