package com.thentrees.lab_week5_www.backend.repositories;

import com.thentrees.lab_week5_www.backend.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    List<Job> findAllByCompanyId(Long companyId);
}
