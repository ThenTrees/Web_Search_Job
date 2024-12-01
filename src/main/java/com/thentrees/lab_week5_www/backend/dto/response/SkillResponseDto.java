package com.thentrees.lab_week5_www.backend.dto.response;

import com.thentrees.lab_week5_www.backend.enums.SkillType;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillResponseDto {
    private Long id;
    private SkillType type;
    private String skillName;
    private String skillDescription;
    private List<JobSkill> jobSkills;

}
