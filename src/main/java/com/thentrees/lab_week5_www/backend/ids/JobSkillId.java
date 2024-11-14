package com.thentrees.lab_week5_www.backend.ids;

import com.thentrees.lab_week5_www.backend.models.Job;
import com.thentrees.lab_week5_www.backend.models.Skill;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class JobSkillId implements Serializable {
    private Long jobId;
    private Long skillId;
}
