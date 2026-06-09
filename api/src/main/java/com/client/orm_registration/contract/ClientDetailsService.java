package com.client.orm_registration.contract;

import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.query.ClientDetailsResponse;


public interface ClientDetailsService {

    ClientDetailsResponse saveClientDetails(ClientDetailsRequest request);

    ClientDetailsResponse getClientDetailsByClientId(Long clientId);

    ClientDetailsResponse updateClientDetails(Long clientId, ClientDetailsRequest request);
}