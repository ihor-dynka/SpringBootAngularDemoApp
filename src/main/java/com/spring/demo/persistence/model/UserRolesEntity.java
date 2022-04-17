package com.spring.demo.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "user_roles")
public class UserRolesEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;
}
