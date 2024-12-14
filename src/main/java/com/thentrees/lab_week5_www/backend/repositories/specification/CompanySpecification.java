package com.thentrees.lab_week5_www.backend.repositories.specification;

import com.thentrees.lab_week5_www.backend.models.Company;
import org.springframework.data.jpa.domain.Specification;

public class CompanySpecification {

    public static Specification<Company> hasFullName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fullName"),  name);
    }

    public static Specification<Company> hasCity(String city) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.join("address").get("city"),
                city
        );
    }

}
