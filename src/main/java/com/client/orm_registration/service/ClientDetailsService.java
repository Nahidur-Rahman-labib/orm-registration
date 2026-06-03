package com.client.orm_registration.service;

import com.client.orm_registration.dto.ClientDetailsRequest;
import com.client.orm_registration.dto.ClientDetailsResponse;

public interface ClientDetailsService {

    ClientDetailsResponse saveClientDetails(ClientDetailsRequest request);

    ClientDetailsResponse getClientDetailsByClientId(Long clientId);

    ClientDetailsResponse updateClientDetails(Long clientId, ClientDetailsRequest request);
}