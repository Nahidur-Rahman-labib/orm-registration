package com.client.orm_registration.mapper;

import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.entity.*;
import com.client.orm_registration.query.AddressResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AddressMapper {

    public void updateEntity(Address address,
                             AddressRequest request,
                             Client client,
                             AddressType addressType,
                             Country country,
                             Division division,
                             District district,
                             Thana thana) {

        if (address == null || request == null) return;

        address.setClient(client);
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setMobileNo(request.getMobileNo());
        address.setEmail(request.getEmail());

        address.setAddressType(addressType);
        address.setCountry(country);
        address.setDivision(division);
        address.setDistrict(district);
        address.setThana(thana);

        address.setRecordDt(new Date());
    }


    public AddressResponse toResponse(Address address) {

        if (address == null) return null;

        AddressResponse response = new AddressResponse();

        response.setAddressId(address.getAddressId());
        response.setAddress(address.getAddress());

        if (address.getAddressType() != null) {
            response.setAddressTypeId(address.getAddressType().getAddrTypeId());
            response.setAddressTypeName(address.getAddressType().getAddrTypeNm());
        }

        if (address.getCountry() != null) {
            response.setCountryId(address.getCountry().getCountryId());
            response.setCountryName(address.getCountry().getCountryName());
        }

        if (address.getDivision() != null) {
            response.setDivisionId(address.getDivision().getDivisionId());
            response.setDivisionName(address.getDivision().getDivisionName());
        }

        if (address.getDistrict() != null) {
            response.setDistrictId(address.getDistrict().getDistrictId());
            response.setDistrictName(address.getDistrict().getDistrictName());
        }

        if (address.getThana() != null) {
            response.setThanaId(address.getThana().getThanaId());
            response.setThanaName(address.getThana().getThanaName());
        }

        response.setCity(address.getCity());
        response.setZipCode(address.getZipCode());
        response.setMobileNo(address.getMobileNo());
        response.setEmail(address.getEmail());

        return response;
    }
}