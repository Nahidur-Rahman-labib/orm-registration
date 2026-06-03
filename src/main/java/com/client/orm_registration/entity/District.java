package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MD_DISTRICT")
@Getter
@Setter
@NoArgsConstructor
public class District {

    @Id
    @Column(name = "DISTRICT_ID")
    private Integer districtId;

    @Column(name = "DISTRICT_NAME")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "DIVISION_ID")
    private Division division;
}