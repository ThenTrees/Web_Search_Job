package com.thentrees.lab_week5_www.backend.dto.request;

import com.thentrees.lab_week5_www.backend.enums.SkillType;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRequestDto {
    private SkillType skillType;
    @NotEmpty(message = "Skill name is required")
    @Size(min = 2, max = 150, message = "Skill name must be between 2 and 150 characters")
    private String skillName;
    @Size(min = 2, max = 300, message = "Skill description must be between 2 and 300 characters")
    private String skillDescription;
    private List<JobSkill> jobSkills;


}
