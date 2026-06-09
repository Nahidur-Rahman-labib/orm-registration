package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MD_DIVISION")
@Getter
@Setter
public class Division {

    @Id
    @Column(name = "DIVISION_ID")
    private Integer divisionId;

    @Column(name = "DIVISION_NAME")
    private String divisionName;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;
}