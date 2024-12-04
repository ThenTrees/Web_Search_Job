package com.thentrees.lab_week5_www.backend.ids;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode
@Embeddable
public class JobSkillId implements Serializable {
    private Long jobId;
    private Long skillId;
}
