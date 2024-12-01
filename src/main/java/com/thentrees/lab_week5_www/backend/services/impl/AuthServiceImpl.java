package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.mapper.CandidateMapper;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.repositories.AuthRepository;
import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import com.thentrees.lab_week5_www.backend.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final AuthRepository authRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateRepository candidateRepository;

    @Override
    public CandidateResponseDto login(String phone, String password) {
        Candidate candidate = candidateRepository.findByPhone(phone);
        if(candidate != null && candidate.getPassword().equals(password)){
            return candidateMapper.toCandidateResponseDto(candidate);
        }else {
            throw  new RuntimeException("Invalid email or password");
        }
    }

    @Override
    public Candidate signUp(Candidate candidate) {
        return null;
    }
}
