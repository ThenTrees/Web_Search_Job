package com.thentrees.lab_week5_www.backend.models;

import com.thentrees.lab_week5_www.backend.enums.SkillLevel;
import com.thentrees.lab_week5_www.backend.ids.JobSkillId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_skill")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSkill {
    @EmbeddedId
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private JobSkillId id;

    @Column(columnDefinition = "varchar(1000)", name = "more_infos")
    private String moreInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "skill_level")
    private SkillLevel skillLevel;

    @ManyToOne
    @MapsId("skillId")
    @JoinColumn(name = "skill_id")
    private Skill skill;


    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("jobId")
    @JoinColumn(name = "job_id")
    private Job job;
}
