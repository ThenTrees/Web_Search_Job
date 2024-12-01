package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateSkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.ExperienceRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.mapper.CandidateMapper;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.CandidateSkill;
import com.thentrees.lab_week5_www.backend.models.Experience;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.repositories.*;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements ICandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final AddressRepository addressRepository;
    @Override
    public Candidate createCandidate(CandidateRequestDto candidateRequestDto) {
        if (checkPhoneAndEmailExists(candidateRequestDto.getPhone(), candidateRequestDto.getEmail())) {
            throw new RuntimeException("Phone or email already exists");
        }
        addressRepository.save(candidateRequestDto.getAddress());
        Candidate candidate = candidateMapper.toCandidate(candidateRequestDto);
        // skill
        List<CandidateSkill> skillsOfCandidate = new ArrayList<>();
        if(candidateRequestDto.getCandidateSkills()!=null) {
            for (CandidateSkillRequestDto candidateSkillRequestDto : candidateRequestDto.getCandidateSkills()) {
                Skill skill = skillRepository.findById(candidateSkillRequestDto.getSkillId()).orElseThrow(
                        () -> new RuntimeException("Skill not found")
                );
                CandidateSkill candidateSkill = new CandidateSkill();
                candidateSkill.setCandidate(candidate);
                candidateSkill.setSkill(skill);
                candidateSkill.setMoreInfo(candidateSkillRequestDto.getMoreInfo());
                candidateSkill.setSkillLevel(candidateSkillRequestDto.getSkillLevel());
                skillsOfCandidate.add(candidateSkill);
            }
        }
        List<Experience> experiences = new ArrayList<>();
        if (candidateRequestDto.getExperiences() != null) {
            for (ExperienceRequestDto experienceRequestDto : candidateRequestDto.getExperiences()) {
                Experience experience = new Experience();
                experience.setCandidate(candidate);
                experience.setCompanyName(experienceRequestDto.getCompanyName());
                experience.setFromDate(experienceRequestDto.getFromDate());
                experience.setToDate(experienceRequestDto.getToDate());
                experience.setRole(experienceRequestDto.getRole());
                experience.setWorkDescription(experienceRequestDto.getWorkDescription());
                experiences.add(experience);
            }
        }

        candidateRepository.save(candidate);
        candidateSkillRepository.saveAll(skillsOfCandidate);
        experienceRepository.saveAll(experiences);
        return candidate;
    }

    @Override
    public void updateCandidate(Long id, CandidateRequestDto candidateRequestDto) {

    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    public List<CandidateResponseDto> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        List<CandidateResponseDto> candidateResponseDtos = new ArrayList<>();
        for (Candidate candidate : candidates) {
            candidateResponseDtos.add(candidateMapper.toCandidateResponseDto(candidate));
        }
        return candidateResponseDtos;
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Candidate not found")
        );
    }

    private boolean checkPhoneAndEmailExists(String phone, String email) {
        return candidateRepository.findByPhone(phone) != null || candidateRepository.findByEmail(email) != null;
    }


}
