package com.thentrees.lab_week5_www.backend.services;

import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.models.Company;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICompanyService {
    @Transactional
    void addCompany(CompanyRequestDto companyRequestDto);

    Company getCompanyById(Long id);
    @Transactional

    void updateCompany(Long id, CompanyRequestDto companyRequestDto);
    @Transactional

    void deleteCompany(Long id);
    List<Company> getAllCompanies();
}
