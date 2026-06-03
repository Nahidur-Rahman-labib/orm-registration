package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MD_ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(name = "address_seq", sequenceName = "SEQ_CL_ACC_SL", allocationSize = 1)
    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_TYPE_ID")
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "DIVISION_ID")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @ManyToOne
    @JoinColumn(name = "THANA_ID")
    private Thana thana;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "APPROVE_FLAG")
    private Integer approveFlag = 0;

    @Column(name = "RECORD_USER_ID")
    private String recordUserId;

    @Column(name = "RECORD_DT")
    private Date recordDt;
}