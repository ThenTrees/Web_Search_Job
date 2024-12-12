package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {
    public Candidate toCandidate(CandidateRequestDto candidateRequestDto) {
        return Candidate.builder()
                .phone(candidateRequestDto.getPhone())
                .email(candidateRequestDto.getEmail())
                .dob(candidateRequestDto.getDob())
                .fullName(candidateRequestDto.getFullName())
                .address(candidateRequestDto.getAddress())
                .active(true)
                .build();
    }

    public CandidateResponseDto toCandidateResponseDto(Candidate candidate) {
        return CandidateResponseDto.builder()
                .id(candidate.getId())
                .phone(candidate.getPhone())
                .email(candidate.getEmail())
                .dob(candidate.getDob())
                .fullName(candidate.getFullName())
                .address(candidate.getAddress())
                .experiences(candidate.getExperiences())
                .skills(candidate.getCandidateSkills())
                .build();
    }
}
