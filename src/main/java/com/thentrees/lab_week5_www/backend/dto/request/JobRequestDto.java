package com.thentrees.lab_week5_www.backend.dto.request;

import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import com.thentrees.lab_week5_www.backend.models.CandidateSkill;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobRequestDto {
    private String name;
    private String description;
    private double salary;
    private Long companyId;
    private int level;
//    private List<JobSkill> jobSkills;
    private List<String> skills;
    private List<CandidateJob> candidateJobs;
}
