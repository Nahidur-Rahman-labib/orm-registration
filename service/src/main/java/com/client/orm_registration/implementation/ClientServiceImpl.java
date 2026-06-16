package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.ClientService;
import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.mapper.ClientMapper;
import com.client.orm_registration.query.GetClientResponse;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    @Transactional
    public GetClientResponse createClient(CreateClientRequest request) {

        Client client = clientMapper.toEntity(request);
        Client savedClient = clientRepository.save(client);

        return clientMapper.toResponse(savedClient, "Client created successfully");
    }

    @Override
    @Transactional(readOnly = true)
    public GetClientResponse getClientById(Long clientId) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        return clientMapper.toResponse(client, "SUCCESS");
    }

    @Override
    @Transactional(readOnly = true)
    public List<GetClientResponse> getAllClients() {

        return clientMapper.toResponseList(clientRepository.findAll());
    }

    @Override
    @Transactional
    public GetClientResponse updateClient(Long clientId, CreateClientRequest request) {

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));

        clientMapper.updateEntity(client, request);

        Client updatedClient = clientRepository.save(client);

        return clientMapper.toResponse(updatedClient, "Client updated successfully");
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
