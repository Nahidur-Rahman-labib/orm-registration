package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "MREG_CLIENT_DETAILS")
@Getter
@Setter
@NoArgsConstructor
public class ClientDetails {

    @Id
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @OneToOne
    @JoinColumn(name = "CLIENT_ID")
    @MapsId
    private Client client;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "MOTHER_NAME")
    private String motherName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "MARITAL_STATUS")
    private String maritalStatus;

    @Column(name = "SPOUSE_NAME")
    private String spouseName;

    @Column(name = "NID")
    private String nid;

    @Column(name = "APPROVE_FLAG")
    private Integer approveFlag = 0;

    @Column(name = "RECORD_USER_ID")
    private String recordUserId;

    @Column(name = "RECORD_DT")
    private Date recordDt;
}