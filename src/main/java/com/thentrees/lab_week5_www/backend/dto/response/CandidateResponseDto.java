package com.thentrees.lab_week5_www.backend.dto.response;

import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.CandidateSkill;
import com.thentrees.lab_week5_www.backend.models.Experience;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"experiences", "skills"})
public class CandidateResponseDto implements Serializable {
    private Long id;
    private String phone;
    private String email;
    private LocalDate dob;
    private String fullName;
    private Address address;
    private List<Experience> experiences;
    private List<CandidateSkill> skills;
}
