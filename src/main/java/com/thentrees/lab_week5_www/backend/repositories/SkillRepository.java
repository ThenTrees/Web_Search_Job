package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    Optional<Skill> findBySkillName(String skillName);
}
