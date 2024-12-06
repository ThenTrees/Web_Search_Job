package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICandidateJobService {
    @Transactional
    void addCandidateToJob(Long candidateId, Long jobId);

    @Transactional
    void removeCandidateFromJob(Long candidateId, Long jobId);

    List<CandidateJob> getAllCandidateJobByCandidateId(Long candidateId);
}
