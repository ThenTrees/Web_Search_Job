package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.SkillRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.SkillMapper;
import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.repositories.SkillRepository;
import com.thentrees.lab_week5_www.backend.repositories.specification.CandidateSpecification;
import com.thentrees.lab_week5_www.backend.repositories.specification.SkillSpecification;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService implements ISkillService {

    private final SkillRepository skillRepository;
    private final SkillMapper skillMapper;


    @Override
    public Skill addSkill(SkillRequestDto skillRequestDto) {
        Skill skill = skillMapper.toSkill(skillRequestDto);
        skillRepository.save(skill);
        return skill;
    }

    @Override
    public void updateSkill(Long id, SkillRequestDto skillRequestDto) {

    }

    @Override
    public void deleteSkill(Long id) {
        skillRepository.delete(skillRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill not found")));
    }

    @Override
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
    }

    @Override
    public Page<Skill> getAllSkills(Pageable pageable, String search) {
        // Tạo Specification
        Specification<Skill> specification = Specification.where(null);
        // Thêm điều kiện tìm theo tên nếu có
        if (search != null && !search.isEmpty()) {
            specification = specification.and(SkillSpecification.hasName(search));
        }
        // Tạo Pageable mới với Sort theo thời gian tạo (giả sử trường là createdAt)
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        return skillRepository.findAll(specification, sortedPageable);
    }

    @Override
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    @Override
    public List<Skill> getAllSkillsByJobId(Long jobId) {
        return List.of();
    }
}
