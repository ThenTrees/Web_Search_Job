package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateSkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.ExperienceRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateUpdateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.enums.RoleType;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.CandidateMapper;
import com.thentrees.lab_week5_www.backend.models.*;
import com.thentrees.lab_week5_www.backend.repositories.*;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateServiceImpl implements ICandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    @Override
    public Candidate createCandidate(CandidateRequestDto candidateRequestDto) {
        log.info("Create candidate");
        if (checkPhoneAndEmailExists(candidateRequestDto.getPhone(), candidateRequestDto.getEmail())) {
            throw new RuntimeException("Phone or email already exists");
        }

        addressRepository.save(candidateRequestDto.getAddress());

        Candidate candidate = candidateMapper.toCandidate(candidateRequestDto);

        Role role = roleRepository.findById(1L).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        candidate.setRole(role);
        candidate.setPassword(passwordEncoder.encode(candidateRequestDto.getPassword()));
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

        log.info("Create candidate:::{}",candidate);
        candidateRepository.save(candidate);
        candidateSkillRepository.saveAll(skillsOfCandidate);
        experienceRepository.saveAll(experiences);
        log.info("Create candidate success");
        return candidate;
    }

    @Override
    public void updateCandidate(CandidateUpdateRequestDto candidateUpdateRequestDto) {
        Candidate candidate = candidateRepository.findById(candidateUpdateRequestDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("candidate not exists")
        );

        // neu so moi khac so cu va chua co so moi trong db
        if(!Objects.equals(candidate.getPhone(), candidateUpdateRequestDto.getPhone()) && !checkPhone(candidateUpdateRequestDto.getPhone())){
            candidate.setPhone(candidateUpdateRequestDto.getPhone());
        }

        if(!Objects.equals(candidate.getEmail(), candidateUpdateRequestDto.getEmail())){
            candidate.setEmail(candidateUpdateRequestDto.getEmail());
        }

        if(!Objects.equals(candidate.getDob(), candidateUpdateRequestDto.getDob())){
            candidate.setDob(candidateUpdateRequestDto.getDob());
        }

        if(!Objects.equals(candidate.getFullName(), candidateUpdateRequestDto.getFullName())){
            candidate.setFullName(candidateUpdateRequestDto.getFullName());
        }

        candidateRepository.save(candidate);
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
        return candidateRepository.findByPhone(phone).isPresent() || candidateRepository.findByEmail(email) != null;
    }

    @Override
    public Address getAddressByCanId(Long id) {
        Optional<Candidate> candidate = candidateRepository.findById(id);
        if(candidate.isPresent()){
            Optional<Address> address = addressRepository.findById(candidate.get().getAddress().getId());
            if(address.isPresent()){
                return address.get();
            }
        }
        return new Address();
    }

    @Override
    public Candidate login(String phone, String password) {
        Optional<Candidate> candidate = candidateRepository.findByPhone(phone);
        if(candidate.isPresent()){
            if(candidate.get().getPassword().equals(password)){
                log.info("login success");
                return candidate.get();
            }
        }
        return null;
    }

    private boolean checkPhone(String phone){
        Optional<Candidate> candidate = candidateRepository.findByPhone(phone);
        return candidate.isPresent();
    }


}
