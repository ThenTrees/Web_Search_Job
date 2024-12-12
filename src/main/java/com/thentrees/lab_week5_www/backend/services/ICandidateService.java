package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateUpdateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICandidateService {
    @Transactional
    Candidate createCandidate(CandidateRequestDto candidateRequestDto);

    @Transactional
    void updateCandidate(CandidateUpdateRequestDto candidateUpdateRequestDto);

    @Transactional
    void deleteCandidate(Long id);

    List<CandidateResponseDto> getAllCandidates();

    Candidate getCandidateById(Long id);

    Address getAddressByCanId(Long id);

    Candidate login(String phone, String password);

}
