package com.client.orm_registration.dto;

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
    private Integer countryId;
    private Integer divisionId;
    private Integer districtId;
    private Integer thanaId;

    private String city;
    private String zipCode;
    private String mobileNo;
    private String email;

    public AddressResponse(Long addressId, String address) {
        this.addressId = addressId;
        this.address = address;
    }
}