package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.enums.SkillLevel;
import com.thentrees.lab_week5_www.backend.models.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public Job toJob(JobRequestDto requestDto) {
        return Job.builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .salary(requestDto.getSalary())
                .candidateJobs(requestDto.getCandidateJobs())
                .level(SkillLevel.fromValue(requestDto.getLevel()))
                .build();
    }
}
