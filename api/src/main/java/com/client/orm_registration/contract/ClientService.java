package com.client.orm_registration.contract;

import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.query.GetClientResponse;

import java.util.List;

public interface ClientService {

    GetClientResponse createClient(CreateClientRequest request);

    GetClientResponse getClientById(Long clientId);

    List<GetClientResponse> getAllClients();

    GetClientResponse updateClient(Long clientId, CreateClientRequest request);

    void deleteClient(Long clientId);
}