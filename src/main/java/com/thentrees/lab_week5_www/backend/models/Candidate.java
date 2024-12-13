package com.thentrees.lab_week5_www.backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "cadidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Candidate extends User {
    private LocalDate dob;

    @Column(columnDefinition = "varchar(255)")
    private String fullName;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CandidateSkill> candidateSkills;


    @ToString.Exclude
    @OneToMany(mappedBy = "candidate",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    @ToString.Exclude
    @OneToMany(mappedBy = "candidate",  cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CandidateJob> candidateJobs;
}
