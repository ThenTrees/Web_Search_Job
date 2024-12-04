package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.mapper.CandidateMapper;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.repositories.AuthRepository;
import com.thentrees.lab_week5_www.backend.repositories.CandidateRepository;
import com.thentrees.lab_week5_www.backend.services.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements IAuthService {
    private final CandidateMapper candidateMapper;
    private final CandidateRepository candidateRepository;
    private final AuthenticationManager authenticationManager;
    @Override
    public CandidateResponseDto login(String phone, String password) {
        log.info("Login with phone::::{} - {}", phone, password);
        Candidate candidate = candidateRepository.findByPhone(phone).orElseThrow( () ->
            new RuntimeException("Invalid phone or password")
        );

        if(!candidate.isActive()){
            throw new RuntimeException("Account is not active");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phone,
                password,
                candidate.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return candidateMapper.toCandidateResponseDto(candidate);
    }

    @Override
    public Candidate signUp(Candidate candidate) {
        return null;
    }
}
