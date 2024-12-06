package com.thentrees.lab_week5_www.backend.dto.request;

import com.thentrees.lab_week5_www.backend.models.Address;
import com.thentrees.lab_week5_www.backend.models.Job;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CompanyRequestDto {
    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 255, message = "Company name must be between 2 and 255 characters")
    private String name;
    private String password;
    private String about;
    @NotNull(message = "Address is required")
    private Address address;
    @NotBlank(message = "Phone number is required")
    private String phone;
    private String webURL;
    private String email;
//    private List<Job> jobs;

}
