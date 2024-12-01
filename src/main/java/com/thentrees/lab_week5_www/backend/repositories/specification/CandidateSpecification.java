package com.thentrees.lab_week5_www.backend.repositories.specification;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import org.springframework.data.jpa.domain.Specification;

public class CandidateSpecification {

    public static Specification<Candidate> hasPhoneLike(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"),  phone );
    }

    public static Specification<Candidate> hasPassword(String password) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("password"), "%" + password + "%");
    }
}
