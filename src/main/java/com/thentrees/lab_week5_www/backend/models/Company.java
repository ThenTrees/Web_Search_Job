package com.thentrees.lab_week5_www.backend.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(exclude = "jobs")
public class Company extends User {
    @Column(columnDefinition = "varchar(255)")
    private String fullName;

    @Column( columnDefinition = "varchar(2000)")
    private String about;

    @Column( columnDefinition = "varchar(2000)", name = "web_url")
    private String webURL;


    @OneToMany(mappedBy = "company")
    private List<Job> jobs;
}
