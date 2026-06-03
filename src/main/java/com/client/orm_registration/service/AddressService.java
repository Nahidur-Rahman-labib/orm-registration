package com.client.orm_registration.service;

import com.client.orm_registration.dto.AddressRequest;
import com.client.orm_registration.dto.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse addAddress(AddressRequest request);

    AddressResponse updateAddress(Long addressId, AddressRequest request);

    void deleteAddress(Long addressId);

    List<AddressResponse> getAddressesByClientId(Long clientId);
}