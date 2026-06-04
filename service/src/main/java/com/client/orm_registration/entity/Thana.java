package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MD_THANA")
@Getter
@Setter
public class Thana {

    @Id
    @Column(name = "THANA_ID")
    private Integer thanaId;

    @Column(name = "THANA_NAME")
    private String thanaName;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;
}