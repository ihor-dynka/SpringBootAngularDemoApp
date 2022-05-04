package com.spring.demo.persistence.repositories;

import com.spring.demo.persistence.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RoleEntity, Integer> {
}
