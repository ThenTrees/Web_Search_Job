package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.SkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.SkillResponseDto;
import com.thentrees.lab_week5_www.backend.models.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISkillService {
    Skill addSkill(SkillRequestDto skillRequestDto);
    void updateSkill(Long id, SkillRequestDto skillRequestDto);
    void deleteSkill(Long id);
    Skill getSkillById(Long id);
    Page<Skill> getAllSkills(Pageable pageable, String search);
    List<Skill> getAllSkills();

    // get all skills by job id
    List<Skill> getAllSkillsByJobId(Long jobId);
}
