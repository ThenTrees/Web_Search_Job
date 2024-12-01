package com.thentrees.lab_week5_www.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "experience")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Experience implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exp_id")
    private Long id;

    @Column(name = "to_date")
    private LocalDate toDate;
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "company", columnDefinition = "varchar(120)")
    private String companyName;

    @Column(columnDefinition = "varchar(100)")
    private String role;

    @Column(columnDefinition = "varchar(400)", name = "work_desc")
    private String workDescription;

    @ManyToOne
    @JoinColumn(name = "can_id")
    private Candidate candidate;
}
