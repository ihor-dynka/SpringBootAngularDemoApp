package com.spring.demo.persistence.entities;

import com.spring.demo.enums.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer roleId;

	@Column(name = "role_name", nullable = false, unique = true)
	private String roleName;

	@Column(name = "description", nullable = false)
	private String description;

	public RoleEntity(Role role) {
		this.roleName = role.getName();
		this.description = role.getDescription();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		RoleEntity that = (RoleEntity) o;
		return roleId != null && Objects.equals(roleId, that.roleId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
