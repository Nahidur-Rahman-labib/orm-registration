package com.client.orm_registration.command;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class ClientDetailsRequest {
    private Long clientId;

    @NotBlank(message = "Father Name is required")
    private String fatherName;

    @NotBlank(message = "Mother Name is required")
    private String motherName;
    private String gender;

    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;

    private String maritalStatus;
    private String spouseName;

    @Pattern(regexp="\\d{5,20}", message="NID must be numeric with 5-20 digits")
    private String nid;
}