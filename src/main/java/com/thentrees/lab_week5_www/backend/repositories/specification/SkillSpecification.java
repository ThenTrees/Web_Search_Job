package com.thentrees.lab_week5_www.backend.repositories.specification;

import com.thentrees.lab_week5_www.backend.models.Candidate;
import com.thentrees.lab_week5_www.backend.models.Skill;
import org.springframework.data.jpa.domain.Specification;

public class SkillSpecification {
    public static Specification<Skill> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("skillName"),  name);
    }

}
