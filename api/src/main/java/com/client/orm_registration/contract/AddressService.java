package com.client.orm_registration.contract;

import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.query.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse addAddress(AddressRequest request);

    AddressResponse updateAddress(Long addressId, AddressRequest request);

    void deleteAddress(Long addressId);

    List<AddressResponse> getAddressesByClientId(Long clientId);
}