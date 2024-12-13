package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateSkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.ExperienceRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.candidate.CandidateUpdateRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.CandidateResponseDto;
import com.thentrees.lab_week5_www.backend.enums.RoleType;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.ids.CandidateSkillId;
import com.thentrees.lab_week5_www.backend.ids.JobSkillId;
import com.thentrees.lab_week5_www.backend.mapper.CandidateMapper;
import com.thentrees.lab_week5_www.backend.models.*;
import com.thentrees.lab_week5_www.backend.repositories.*;
import com.thentrees.lab_week5_www.backend.repositories.specification.CandidateSpecification;
import com.thentrees.lab_week5_www.backend.repositories.specification.JobSpecification;
import com.thentrees.lab_week5_www.backend.services.ICandidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CandidateServiceImpl implements ICandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillRepository skillRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Candidate createCandidate(CandidateRequestDto candidateRequestDto) {
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

        Set<String> skills = candidateRequestDto.getSkills();
        for (String skillName : skills) {
            Skill skill = skillRepository.findBySkillName(skillName).orElseThrow(
                    ()-> new ResourceNotFoundException("Skill with name: "+skillName+" not found"));
            //chac la loi cho nay
            CandidateSkillId candidateSkillId = new CandidateSkillId();
            candidateSkillId.setCandidateId(candidate.getId());
            candidateSkillId.setSkillId(skill.getId());
            CandidateSkill candidateSkill = CandidateSkill.builder()
                    .id(candidateSkillId)
                    .candidate(candidate)
                    .skill(skill)
                    .build();
            skillsOfCandidate.add(candidateSkill);
        }
        log.info("Create candidate:::{}",candidate.toString());
        candidateRepository.save(candidate);
        candidateSkillRepository.saveAll(skillsOfCandidate);
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
    public Page<Candidate> getAllCandidates(Pageable pageable, String name, String city) {
        switch (city){
            case "1":
                city = "Ha Noi";
                break;
            case "2":
                city = "Da Nang";
                break;
            case "3":
                city = "Ho Chi Minh";
                break;
            case "4":
                city = "";
                break;
        }
        // Tạo Specification
        Specification<Candidate> specification = Specification.where(null);
        // Thêm điều kiện tìm theo tên nếu có
        if (name != null && !name.isEmpty()) {
            specification = specification.and(CandidateSpecification.hasName(name)).or(CandidateSpecification.hasSkill(name));
        }
        // Thêm điều kiện tìm theo thành phố nếu có
        if (!city.isEmpty()) {
            specification = specification.and(CandidateSpecification.hasCity(city));
        }
        // Tạo Pageable mới với Sort theo thời gian tạo (giả sử trường là createdAt)
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return candidateRepository.findAll(specification, sortedPageable);
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
