package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.ids.CandidateJobId;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.CandidateJob;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.repositories.CandidateJobRepository;
import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import com.thentrees.lab_week5_www.backend.repositories.JobRepository;
import com.thentrees.lab_week5_www.backend.services.ICandidateJobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CandidateJobServiceService implements ICandidateJobService {

    private final CandidateJobRepository candidateJobRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;
    @Override
    public void addCandidateToJob(Long candidateId, Long jobId) {
        Candidate existsCandidate = candidateRepository.findById(candidateId).orElseThrow(
                ()-> new ResourceNotFoundException("Candidate with id: "+candidateId+" not found")
        );

        Job existsJob = jobRepository.findById(jobId).orElseThrow(
                ()-> new ResourceNotFoundException("Job with id: "+jobId+" not found")
        );

        CandidateJobId candidateJobId = new CandidateJobId();
        candidateJobId.setCandidateId(candidateId);
        candidateJobId.setJobId(jobId);

        CandidateJob candidateJob = new CandidateJob();
        candidateJob.setId(candidateJobId);
        candidateJob.setCandidate(existsCandidate);
        candidateJob.setJob(existsJob);

        candidateJobRepository.save(candidateJob);
    }

    @Override
    public void removeCandidateFromJob(Long candidateId, Long jobId) {
        CandidateJob candidateJob = candidateJobRepository.findById(CandidateJobId.builder()
                .candidateId(candidateId)
                .jobId(jobId).build()).orElseThrow(
                ()-> new ResourceNotFoundException("CandidateJob with candidateId: "+candidateId+" and jobId: "+jobId+" not found")
        );
        candidateJobRepository.delete(candidateJob);
    }

    @Override
    public List<CandidateJob> getAllCandidateJobByCandidateId(Long candidateId) {
        return candidateJobRepository.getAllCandidateJobByCandidateId(candidateId);
    }

    @Override
    public List<CandidateJob> getAllCandidatesByJobId(Long jobId) {
        return candidateJobRepository.getAllCandidateJobByJobId(jobId);
    }
}
