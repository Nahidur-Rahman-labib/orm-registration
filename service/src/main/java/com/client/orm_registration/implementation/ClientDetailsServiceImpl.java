package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.ClientDetailsService;
import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.mapper.ClientDetailsMapper;
import com.client.orm_registration.query.ClientDetailsResponse;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.entity.ClientDetails;
import com.client.orm_registration.repository.ClientDetailsRepository;
import com.client.orm_registration.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    private final ClientDetailsRepository clientDetailsRepository;
    private final ClientRepository clientRepository;
    private final ClientDetailsMapper clientDetailsMapper;

    public ClientDetailsServiceImpl(
            ClientDetailsRepository clientDetailsRepository,
            ClientRepository clientRepository,
            ClientDetailsMapper clientDetailsMapper) {

        this.clientDetailsRepository = clientDetailsRepository;
        this.clientRepository = clientRepository;
        this.clientDetailsMapper = clientDetailsMapper;
    }

    @Override
    @Transactional
    public ClientDetailsResponse saveClientDetails(ClientDetailsRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + request.getClientId()));

        ClientDetails details = clientDetailsMapper.toEntity(request, client);

        ClientDetails savedDetails = clientDetailsRepository.save(details);

        return clientDetailsMapper.toResponse(savedDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDetailsResponse getClientDetailsByClientId(Long clientId) {

        ClientDetails details = clientDetailsRepository.findByClient_ClientId(clientId)
                .orElse(null);

        if (details == null) {
            return new ClientDetailsResponse();
        }

        return clientDetailsMapper.toResponse(details);
    }

    @Override
    @Transactional
    public ClientDetailsResponse updateClientDetails(Long clientId, ClientDetailsRequest request) {

        ClientDetails details = clientDetailsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client details not found with client ID: " + clientId));

        clientDetailsMapper.updateEntity(details, request);

        ClientDetails updated = clientDetailsRepository.save(details);

        return clientDetailsMapper.toResponse(updated);
    }
}