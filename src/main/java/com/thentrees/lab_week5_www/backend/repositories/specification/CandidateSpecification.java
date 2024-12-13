package com.thentrees.lab_week5_www.backend.repositories.specification;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Job;
import org.springframework.data.jpa.domain.Specification;

public class CandidateSpecification {

    public static Specification<Candidate> hasPhoneLike(String phone) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("phone"),  phone );
    }

    public static Specification<Candidate> hasPassword(String password) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("password"), "%" + password + "%");
    }

    public static Specification<Candidate> hasCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.join("address").get("city"),
                city
        );
    }

    public static Specification<Candidate> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("fullName"), "%"+name+"%");
    }

    public static Specification<Candidate> hasSkill(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.join("candidateSkills").join("skill").get("skillName"), "%"+name+"%");
    }
}
