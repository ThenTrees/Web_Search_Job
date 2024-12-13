package com.thentrees.lab_week5_www.backend.ids;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateJobId implements Serializable {
    private Long candidateId;
    private Long jobId;
}
