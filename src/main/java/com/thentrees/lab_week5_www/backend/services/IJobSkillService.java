package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.models.JobSkill;

import java.util.List;

public interface IJobSkillService {
    List<JobSkill> getAllSkillsByJobId(Long jobId);

    JobSkill add(JobSkill jobSkill);
}
