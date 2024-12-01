package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.models.Job;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IJobService {
    @Transactional
    Job addJob(JobRequestDto jobRequestDto);

    @Transactional
    void updateJob(Long id, JobRequestDto jobRequestDto);

    @Transactional
    void deleteJob(Long id);

    Job getJobById(Long id);

    List<Job> getAllJobs();
}
