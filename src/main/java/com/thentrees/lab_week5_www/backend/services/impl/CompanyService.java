package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.CompanyMapper;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.Role;
import com.thentrees.lab_week5_www.backend.repositories.AddressRepository;
import com.thentrees.lab_week5_www.backend.repositories.CompanyRepository;
import com.thentrees.lab_week5_www.backend.repositories.RoleRepository;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void addCompany(CompanyRequestDto companyRequestDto) {
        addressRepository.save(companyRequestDto.getAddress());
        Company company = companyMapper.toCompany(companyRequestDto);
        company.setPassword(passwordEncoder.encode(companyRequestDto.getPassword()));
        Role role = roleRepository.findById(3L).orElseThrow(
                () -> new RuntimeException("Role not found")
        );
        company.setRole(role);
        companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found"));
    }

    @Override
    public void updateCompany(Long id, CompanyRequestDto companyRequestDto) {
        Company company = companyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Company not found"));
        company.setFullName(companyRequestDto.getName());
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
