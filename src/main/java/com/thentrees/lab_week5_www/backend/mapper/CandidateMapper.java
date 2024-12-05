package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {
    public Candidate toCandidate(CandidateRequestDto candidateRequestDto) {

        return Candidate.builder()
                .fullName(candidateRequestDto.getFullName())
                .email(candidateRequestDto.getEmail())
                .phone(candidateRequestDto.getPhone())
                .dob(candidateRequestDto.getDob())
                .address(candidateRequestDto.getAddress())
                .password(candidateRequestDto.getPassword())
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
