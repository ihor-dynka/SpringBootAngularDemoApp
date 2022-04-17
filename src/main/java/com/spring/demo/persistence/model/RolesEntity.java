package com.spring.demo.persistence.model;

import com.spring.demo.enums.Role;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter
public class RolesEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", nullable = false)
	private Long roleId;

	@Column(name = "role_name", nullable = false)
	private Role roleName;

	@Column(name = "description", nullable = false)
	private String description;
}
