package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.SkillRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.SkillMapper;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.repositories.SkillRepository;
import com.thentrees.lab_week5_www.backend.services.ISkillService;
import lombok.RequiredArgsConstructor;
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
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
}