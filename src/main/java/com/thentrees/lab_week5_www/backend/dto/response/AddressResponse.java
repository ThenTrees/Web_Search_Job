package com.thentrees.lab_week5_www.backend.dto.response;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private String city;
    private String zipCode;
    private Short country;
    private String street;
    private String number;
    private User user;
}
