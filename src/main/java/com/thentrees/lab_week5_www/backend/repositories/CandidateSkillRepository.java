package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.ids.CandidateSkillId;
import com.thentrees.lab_week5_www.backend.models.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
}
