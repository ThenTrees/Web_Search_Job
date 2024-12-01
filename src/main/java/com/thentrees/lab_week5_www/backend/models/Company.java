package com.thentrees.lab_week5_www.backend.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "com_id")
    private Long id;

    @Column(name = "comp_name", columnDefinition = "varchar(255)")
    private String name;

    @Column( columnDefinition = "varchar(2000)")
    private String about;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address", nullable = false)
    private Address address;

    @Column( columnDefinition = "varchar(255)")
    private String phone;

    @Column( columnDefinition = "varchar(2000)", name = "web_url")
    private String webURL;

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;
    @Column( columnDefinition = "varchar(255)")
    private String email;

}
