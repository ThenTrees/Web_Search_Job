package com.thentrees.lab_week5_www.backend.models;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"candidate", "company"})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    private Long id;

    @Column(columnDefinition = "varchar(50)")
    private String city;

    @Column(columnDefinition = "varchar(7)", name = "zipcode")
    private String zipCode;

    @Column(columnDefinition = "smallint(6)")
    private Short country;

    @Column(columnDefinition = "varchar(150)")
    private String street;

    @Column(columnDefinition = "varchar(20)")
    private String number;

    @OneToOne(mappedBy = "address")
    private Candidate candidate;

    @OneToOne(mappedBy = "address")
    private Company company;

    @Override
    public String toString() {
        return String.format("%s %s %s %s", number, street, city, CountryCode.getByCode(country).getName());
    }

}
