package com.thentrees.lab_week5_www.backend.dto.request.candidate;

import com.thentrees.lab_week5_www.backend.dto.request.CandidateSkillRequestDto;
import com.thentrees.lab_week5_www.backend.dto.request.ExperienceRequestDto;
import com.thentrees.lab_week5_www.backend.models.Address;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateRequestDto {
    @NotEmpty(message = "Phone number is required")
    @Size(min = 10, max = 11, message = "Phone number must be between 10 and 11 characters")
    @Pattern(regexp = "^0\\d{9}$", message = "Phone number must contain only numbers and start with character 0")
    private String phone;
    private String password;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;
    private LocalDate dob;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, max = 255, message = "Full name must be between 2 and 255 characters")
    private String fullName;

    @NotNull(message = "Address is required")
    private Address address;

    private List<CandidateSkillRequestDto> candidateSkills;
    private List<ExperienceRequestDto> experiences;

}
