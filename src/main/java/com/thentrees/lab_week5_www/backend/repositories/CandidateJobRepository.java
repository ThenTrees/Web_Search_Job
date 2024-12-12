package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.ids.CandidateJobId;
import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateJobRepository extends JpaRepository<CandidateJob, CandidateJobId> {
    List<CandidateJob> getAllCandidateJobByCandidateId(Long candidateId);
    List<CandidateJob> getAllCandidateJobByJobId(Long jobId);
}
