package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICandidateService {
    @Transactional
    Candidate createCandidate(CandidateRequestDto candidateRequestDto);

    @Transactional
    void updateCandidate(Long id, CandidateRequestDto candidateRequestDto);

    @Transactional
    void deleteCandidate(Long id);

    List<CandidateResponseDto> getAllCandidates();

    Candidate getCandidateById(Long id);

}
