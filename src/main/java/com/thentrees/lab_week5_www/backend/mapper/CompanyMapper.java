package com.thentrees.lab_week5_www.backend.mapper;

import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.models.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {
    public Company toCompany(CompanyRequestDto request){
        return Company.builder()
                .about(request.getAbout())
                .fullName(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .webURL(request.getWebURL())
                .address(request.getAddress())
                .build();
    }
}
