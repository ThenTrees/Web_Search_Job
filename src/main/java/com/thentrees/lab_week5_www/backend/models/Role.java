package com.thentrees.lab_week5_www.backend.models;
import com.thentrees.lab_week5_www.backend.enums.RoleType;
import jakarta.persistence.*;

import lombok.*;
@Entity
@Table(name = "role")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_type", unique = true)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
}