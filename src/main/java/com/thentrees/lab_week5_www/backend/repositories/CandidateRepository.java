package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {
    Optional<Candidate> findByPhone(String phoneNumber);
    Candidate findByEmail(String email);
}
