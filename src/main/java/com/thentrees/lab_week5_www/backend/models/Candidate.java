package com.thentrees.lab_week5_www.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cadidate")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Candidate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "can_id")
    private Long id;

    @Column(columnDefinition = "varchar(15)", nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String email;
    private String password;
    private LocalDate dob;

    @Column(columnDefinition = "varchar(7)", name="full_name")
    private String fullName;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private List<CandidateSkill> candidateSkills;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private List<Experience> experiences;
}
