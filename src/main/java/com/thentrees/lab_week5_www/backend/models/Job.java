package com.thentrees.lab_week5_www.backend.models;

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

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    @OneToMany(mappedBy = "job")
    private List<JobSkill> jobSkills;

    @OneToMany(mappedBy = "job")
    private Set<CandidateJob> candidateJobs;

    @Column(name = "job_desc",columnDefinition = "varchar(2000)")
    private String description;
}
