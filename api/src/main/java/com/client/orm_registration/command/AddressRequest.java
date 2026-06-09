package com.client.orm_registration.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressRequest {
    private Long clientId;
    private String address;
    private Integer addressTypeId;
    private Integer countryId;
    private Integer divisionId;
    private Integer districtId;
    private Integer thanaId;
    private String city;
    private String zipCode;
    private String mobileNo;
    private String email;
}