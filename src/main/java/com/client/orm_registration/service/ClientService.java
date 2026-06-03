package com.client.orm_registration.service;

import com.client.orm_registration.dto.CreateClientRequest;
import com.client.orm_registration.dto.CreateClientResponse;
import com.client.orm_registration.dto.GetClientResponse;

import java.util.List;

public interface ClientService {

    CreateClientResponse createClient(CreateClientRequest request);

    GetClientResponse getClientById(Long clientId);

    List<GetClientResponse> getAllClients();

    GetClientResponse updateClient(Long clientId, CreateClientRequest request);

    void deleteClient(Long clientId);
}