package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.repositories.JobSkillRepository;
import com.thentrees.lab_week5_www.backend.services.IJobSkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class JobSkillServiceImpl implements IJobSkillService {
    private final JobSkillRepository jobSkillRepository;
    @Override
    public List<JobSkill> getAllSkillsByJobId(Long jobId) {
        return jobSkillRepository.findAllByJobId(jobId);
    }

    @Override
    public JobSkill add(JobSkill jobSkill) {
        return jobSkillRepository.save(jobSkill);
    }
}
