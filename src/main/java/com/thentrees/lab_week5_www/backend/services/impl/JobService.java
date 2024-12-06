package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.mapper.JobMapper;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.repositories.CompanyRepository;
import com.thentrees.lab_week5_www.backend.repositories.JobRepository;
import com.thentrees.lab_week5_www.backend.repositories.specification.JobSpecification;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyRepository companyRepository;

    @Override
    public Job addJob(JobRequestDto jobRequestDto) {
        Job job = jobMapper.toJob(jobRequestDto);
        Company company = companyRepository.findById(jobRequestDto.getCompanyId()).orElseThrow(
                ()-> new ResourceNotFoundException("Company with id: "+jobRequestDto.getCompanyId()+" not found"));
        job.setCompany(company);
        return jobRepository.save(job);
    }

    @Override
    public void updateJob(Long id, JobRequestDto jobRequestDto) {

    }

    @Override
    public void deleteJob(Long id) {

    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job with id: "+id+" not found"));
    }

    @Override
    public Page<Job> getAllJobs(Pageable pageable, String name, String city) {

        switch (city){
            case "1":
                city = "Ha Noi";
                break;
            case "2":
                city = "Da Nang";
                break;
            case "3":
                city = "Ho Chi Minh";
                break;
            case "4":
                city = "";
                break;
        }

        Specification<Job> specification = Specification.where(null);
        // Thêm điều kiện tìm theo tên nếu có
        if (name != null && !name.isEmpty()) {
            specification = specification.and(JobSpecification.hasTitle(name));
        }
        // Thêm điều kiện tìm theo thành phố nếu có
        if (!city.isEmpty()) {
            specification = specification.and(JobSpecification.hasCity(city));
        }
        return jobRepository.findAll(specification, pageable);

    }


    @Override
    public List<Job> getAllJobByCompanyId(Long companyId) {
        return jobRepository.findAllByCompanyId(companyId);
    }
}
