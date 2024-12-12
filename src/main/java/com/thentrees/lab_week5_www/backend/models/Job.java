package com.thentrees.lab_week5_www.backend.models;

import com.thentrees.lab_week5_www.backend.enums.JobType;
import com.thentrees.lab_week5_www.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @Column(name = "job_name", columnDefinition = "varchar(255)")
    private String name;

    @Column(name = "job_desc",columnDefinition = "varchar(2000)")
    private String description;

    @Column(name = "salary")
    private double salary;

    @Column(name = "level")
    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @OneToMany(mappedBy = "job")
    private Set<JobSkill> jobSkills;

    @OneToMany(mappedBy = "job")
    private Set<CandidateJob> candidateJobs;
}
