package com.client.orm_registration.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long addressId;
    private String address;
    private Integer addressTypeId;
    private String addressTypeName;   // ADD
    private Integer countryId;
    private String countryName;       // ADD
    private Integer divisionId;
    private String divisionName;      // ADD
    private Integer districtId;
    private String districtName;      // ADD
    private Integer thanaId;
    private String thanaName;         // ADD
    private String city;
    private String zipCode;
    private String mobileNo;
    private String email;
}