package com.thentrees.lab_week5_www.backend.dto.request;

import com.thentrees.lab_week5_www.backend.enums.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSkillRequestDto {
    private Long candidateId;
    private Long skillId;
    private String moreInfo;
    private SkillLevel skillLevel;
}
