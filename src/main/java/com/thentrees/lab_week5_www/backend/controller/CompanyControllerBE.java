package com.thentrees.lab_week5_www.backend.controller;

import com.thentrees.lab_week5_www.backend.dto.request.CompanyRequestDto;
import com.thentrees.lab_week5_www.backend.dto.response.ResponseObject;
import com.thentrees.lab_week5_www.backend.services.ICompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final ICompanyService companyService;

    @PostMapping
    public ResponseEntity<ResponseObject> addCompany(@Valid @RequestBody CompanyRequestDto companyRequestDto) {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Company added successfully")
                .code(201)
                .data(companyService.addCompany(companyRequestDto))
                .build());
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllCompanies() {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Companies fetched successfully")
                .code(200)
                .data(companyService.getAllCompanies())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getCompanyById(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Company fetched successfully")
                .code(200)
                .data(companyService.getCompanyById(id))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCompany(@PathVariable Long id, @Valid @RequestBody CompanyRequestDto companyRequestDto) {
        companyService.updateCompany(id, companyRequestDto);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Company updated successfully")
                .code(200)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Company deleted successfully")
                .code(200)
                .build());
    }

}
