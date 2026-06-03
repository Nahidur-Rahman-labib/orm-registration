package com.client.orm_registration.service.impl;

import com.client.orm_registration.dto.CreateClientRequest;
import com.client.orm_registration.dto.CreateClientResponse;
import com.client.orm_registration.dto.GetClientResponse;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.repository.ClientRepository;
import com.client.orm_registration.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public CreateClientResponse createClient(CreateClientRequest request) {
        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setApproveFlag(0);
        client.setRecordUserId("SYSTEM");
        client.setRecordDt(new Date());

        Client savedClient = clientRepository.save(client);

        return new CreateClientResponse(
                savedClient.getClientId(),
                "Client created successfully"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public GetClientResponse getClientById(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        return new GetClientResponse(
                client.getClientId(),
                client.getClientName(),
                "SUCCESS"
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetClientResponse> getAllClients() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new GetClientResponse(
                        client.getClientId(),
                        client.getClientName(),
                        "SUCCESS"
                ))
                .toList();
    }

    @Override
    @Transactional
    public GetClientResponse updateClient(Long clientId, CreateClientRequest request) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        client.setClientName(request.getClientName());
        client.setRecordDt(new Date());

        Client updatedClient = clientRepository.save(client);

        return new GetClientResponse(
                updatedClient.getClientId(),
                updatedClient.getClientName(),
                "Client updated successfully"
        );
    }

    @Override
    @Transactional
    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new RuntimeException("Client not found with ID: " + clientId);
        }

        clientRepository.deleteById(clientId);
    }
}