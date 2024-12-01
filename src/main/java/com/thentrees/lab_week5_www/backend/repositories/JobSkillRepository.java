package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.ids.JobSkillId;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
}
