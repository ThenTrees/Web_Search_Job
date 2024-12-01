package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.SkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.SkillResponseDto;
import com.thentrees.lab_week5_www.backend.models.Skill;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
    public Skill toSkill(SkillRequestDto request){
        return Skill.builder()
                .type(request.getSkillType())
                .skillDescription(request.getSkillDescription())
                .skillName(request.getSkillName())
                .build();
    }


    public SkillResponseDto toSkillResponseDto(Skill skill){
        return SkillResponseDto.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .skillDescription(skill.getSkillDescription())
                .type(skill.getType())
                .jobSkills(skill.getJobSkills())
                .build();
    }
}
