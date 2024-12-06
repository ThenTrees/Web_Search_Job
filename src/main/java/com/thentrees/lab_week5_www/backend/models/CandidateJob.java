package com.thentrees.lab_week5_www.backend.models;

import com.thentrees.lab_week5_www.backend.ids.CandidateJobId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_job")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateJob {

    @EmbeddedId
    private CandidateJobId id;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "can_id")
    private Candidate candidate;

    @ManyToOne
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;
}