package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
