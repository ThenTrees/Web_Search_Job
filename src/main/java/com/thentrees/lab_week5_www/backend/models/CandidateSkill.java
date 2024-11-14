package com.thentrees.lab_week5_www.backend.models;

import com.thentrees.lab_week5_www.backend.enums.SkillLevel;
import com.thentrees.lab_week5_www.backend.ids.CandidateSkillId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CandidateSkill {

    @EmbeddedId
    private CandidateSkillId id;

    @Column(name = "more_infos", columnDefinition = "varchar(1000)")
    private String moreInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    @ManyToOne
    @MapsId("candidateId")
    @JoinColumn(name = "can_id")
    private Candidate candidate;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;

}
