package com.spring.demo.persistence.repositories;

import com.spring.demo.persistence.model.RolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<RolesEntity, Integer> {
}
