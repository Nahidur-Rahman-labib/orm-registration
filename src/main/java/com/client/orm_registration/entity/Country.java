package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MD_COUNTRY")
@Getter
@Setter
@NoArgsConstructor
public class Country {

    @Id
    @Column(name = "COUNTRY_ID")
    private Integer countryId;

    @Column(name = "COUNTRY_NAME")
    private String countryName;
}