package com.thentrees.lab_week5_www.backend.services.impl;

import com.thentrees.lab_week5_www.backend.dto.request.JobRequestDto;
import com.thentrees.lab_week5_www.backend.exception.ResourceNotFoundException;
import com.thentrees.lab_week5_www.backend.ids.JobSkillId;
import com.thentrees.lab_week5_www.backend.mapper.JobMapper;
import com.thentrees.lab_week5_www.backend.models.Company;
import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.models.JobSkill;
import com.thentrees.lab_week5_www.backend.models.Skill;
import com.thentrees.lab_week5_www.backend.repositories.CompanyRepository;
import com.thentrees.lab_week5_www.backend.repositories.JobRepository;
import com.thentrees.lab_week5_www.backend.repositories.JobSkillRepository;
import com.thentrees.lab_week5_www.backend.repositories.SkillRepository;
import com.thentrees.lab_week5_www.backend.repositories.specification.JobSpecification;
import com.thentrees.lab_week5_www.backend.services.IJobService;
import com.thentrees.lab_week5_www.backend.services.IJobSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class JobService implements IJobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;
    private final IJobSkillService jobSkillService;
    private final JobSkillRepository jobSkillRepository;


    @Override
    public void addJob(JobRequestDto jobRequestDto) {
        // Chuyển đổi từ JobRequestDto thành Job entity
        Job job = jobMapper.toJob(jobRequestDto);

        // Tìm kiếm công ty từ repository và gán cho job
        Company company = companyRepository.findById(jobRequestDto.getCompanyId()).orElseThrow(
                () -> new ResourceNotFoundException("Company with id: " + jobRequestDto.getCompanyId() + " not found"));
        job.setCompany(company);

        // Lấy danh sách các skill từ request
        List<JobSkill> jobSkills = new ArrayList<>();
        List<String> skills = jobRequestDto.getSkills();

        // Lưu job vào cơ sở dữ liệu trước
        jobRepository.save(job);

        // Lặp qua danh sách skill và tạo các JobSkill
        for (String skillName : skills) {
            // Tìm kiếm skill từ repository
            Skill skill = skillRepository.findBySkillName(skillName).orElseThrow(
                    () -> new ResourceNotFoundException("Skill with name: " + skillName + " not found"));

            // Tạo JobSkillId cho JobSkill
            JobSkillId jobSkillId = new JobSkillId();
            jobSkillId.setJobId(job.getId());
            jobSkillId.setSkillId(skill.getId());

            // Kiểm tra nếu JobSkill đã tồn tại trong cơ sở dữ liệu
            Optional<JobSkill> existingJobSkill = jobSkillRepository.findById(jobSkillId);
            if (existingJobSkill.isEmpty()) {
                // Nếu JobSkill chưa tồn tại, tạo mới JobSkill
                JobSkill jobSkill = JobSkill.builder()
                        .id(jobSkillId)
                        .job(job)
                        .skill(skill)
                        .build();
                jobSkills.add(jobSkill);
            }
        }

        // Lưu tất cả JobSkill vào cơ sở dữ liệu
        if (!jobSkills.isEmpty()) {
            jobSkillRepository.saveAll(jobSkills);
        }

        // Gán danh sách JobSkill cho job và lưu lại
        job.setJobSkills(jobSkills);
        jobRepository.save(job);  // Lưu lại job với danh sách JobSkill đã được gán
    }



    @Override
    public void updateJob(Long id, JobRequestDto jobRequestDto) {
        // Tìm job theo id từ cơ sở dữ liệu
        Job job = jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found job"));
        // Cập nhật các trường thông tin của job từ jobRequestDto (Giữ lại các mối quan hệ cũ)
        job.setName(jobRequestDto.getName());
        job.setDescription(jobRequestDto.getDescription());
        job.setSalary(jobRequestDto.getSalary());
        // Lưu lại job vào cơ sở dữ liệu
        jobRepository.save(job);
    }



    @Override
    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found job"));
        jobRepository.delete(job);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Job with id: "+id+" not found"));
    }

    @Override
    public Page<Job> getAllJobs(Pageable pageable, String name, String city) {

        // Xử lý giá trị city
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
        // Tạo Specification
        Specification<Job> specification = Specification.where(null);
        // Thêm điều kiện tìm theo tên nếu có
        if (name != null && !name.isEmpty()) {
            specification = specification.and(JobSpecification.hasTitle(name));
        }
        // Thêm điều kiện tìm theo thành phố nếu có
        if (!city.isEmpty()) {
            specification = specification.and(JobSpecification.hasCity(city));
        }
        // Tạo Pageable mới với Sort theo thời gian tạo (giả sử trường là createdAt)
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));
        // Trả về các kết quả tìm kiếm đã được sắp xếp
        return jobRepository.findAll(specification, sortedPageable);
    }



    @Override
    public List<Job> getAllJobByCompanyId(Long companyId) {
        return jobRepository.findAllByCompanyId(companyId);
    }
}
