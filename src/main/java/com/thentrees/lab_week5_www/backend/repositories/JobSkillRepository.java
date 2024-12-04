package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.ids.JobSkillId;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
    @Query("SELECT js FROM JobSkill js WHERE js.job.id = ?1")
    List<JobSkill> findAllByJobId(Long jobId);
}
