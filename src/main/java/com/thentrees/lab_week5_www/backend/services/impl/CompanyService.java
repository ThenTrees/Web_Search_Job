package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.CompanyMapper;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.repositories.CompanyRepository;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Company addCompany(CompanyRequestDto companyRequestDto) {
        Company company = companyMapper.toCompany(companyRequestDto);
        companyRepository.save(company);
        return company;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found"));
    }

    @Override
    public void updateCompany(Long id, CompanyRequestDto companyRequestDto) {
        Company company = companyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found"));
        company.setName(companyRequestDto.getName());
        company.setAddress(companyRequestDto.getAddress());
        company.setAbout(companyRequestDto.getAbout());
        company.setEmail(companyRequestDto.getEmail());
        company.setPhone(companyRequestDto.getPhone());
        company.setWebURL(companyRequestDto.getWebURL());
        companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
