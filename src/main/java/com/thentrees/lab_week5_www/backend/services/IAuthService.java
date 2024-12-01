package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.models.Candidate;

public interface IAuthService {
    CandidateResponseDto login(String phone, String password);
    Candidate signUp(Candidate candidate);
}
