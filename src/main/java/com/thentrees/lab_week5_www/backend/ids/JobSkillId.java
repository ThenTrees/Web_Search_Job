package com.thentrees.lab_week5_www.backend.ids;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@EqualsAndHashCode
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobSkillId implements Serializable {
    private Long jobId;
    private Long skillId;
}
