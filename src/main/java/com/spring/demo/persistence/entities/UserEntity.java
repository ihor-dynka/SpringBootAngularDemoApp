package com.spring.demo.persistence.entities;

import com.spring.demo.enums.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer userId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns
		= @JoinColumn(name = "user_id",
		referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "role_id",
			referencedColumnName = "id"))
	private RoleEntity role;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "security_question", nullable = false)
	private String securityQuestion;

	@Column(name = "security_answer", nullable = false)
	private String securityAnswer;

	public UserEntity(Role role, String username, String password, String email, String firstname, String lastname, String securityQuestion, String securityAnswer) {
		this.role = new RoleEntity(role.getId(), role.getName(), role.getDescription());
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.securityQuestion = securityQuestion;
		this.securityAnswer = securityAnswer;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		UserEntity that = (UserEntity) o;
		return userId != null && Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
