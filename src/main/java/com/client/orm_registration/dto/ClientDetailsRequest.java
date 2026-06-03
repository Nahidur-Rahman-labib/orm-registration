package com.client.orm_registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ClientDetailsRequest {
    private Long clientId;
    private String fatherName;
    private String motherName;
    private String gender;
    private Date dateOfBirth;
    private String maritalStatus;
    private String spouseName;
    private String nid;
}