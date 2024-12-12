package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByPhone(String phone);
}
