package com.client.orm_registration.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDetailsResponse {
    private Long clientId;
    private String fatherName;
    private String motherName;
    private String gender;
    private Date dateOfBirth;
    private String maritalStatus;
    private String spouseName;
    private String nid;
}