package com.thentrees.lab_week5_www.backend.repositories.specification;

import com.thentrees.lab_week5_www.backend.models.Job;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobSpecification {

    public static Specification<Job> hasCity(String city) {
        log.info("city: {}", city);
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                root.join("company").join("address").get("city"),
                city
        );
    }

    public static Specification<Job> hasTitle(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }

}
