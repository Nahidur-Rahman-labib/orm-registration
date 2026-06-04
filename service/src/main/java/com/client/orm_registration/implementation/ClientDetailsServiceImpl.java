package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.ClientDetailsService;
import com.client.orm_registration.command.ClientDetailsRequest;
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

    public ClientDetailsServiceImpl(ClientDetailsRepository clientDetailsRepository,
                                    ClientRepository clientRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public ClientDetailsResponse saveClientDetails(ClientDetailsRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + request.getClientId()));

        ClientDetails details = new ClientDetails();
        details.setClient(client);
        details.setFatherName(request.getFatherName());
        details.setMotherName(request.getMotherName());
        details.setGender(request.getGender());
        details.setDateOfBirth(request.getDateOfBirth());
        details.setMaritalStatus(request.getMaritalStatus());
        details.setSpouseName(request.getSpouseName());
        details.setNid(request.getNid());
        details.setApproveFlag(0);
        details.setRecordUserId("SYSTEM");
        details.setRecordDt(new Date());

        ClientDetails savedDetails = clientDetailsRepository.save(details);

        return mapToResponse(savedDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDetailsResponse getClientDetailsByClientId(Long clientId) {
        ClientDetails details = clientDetailsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client details not found with client ID: " + clientId));

        return mapToResponse(details);
    }

    @Override
    @Transactional
    public ClientDetailsResponse updateClientDetails(Long clientId, ClientDetailsRequest request) {
        ClientDetails details = clientDetailsRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client details not found with client ID: " + clientId));

        details.setFatherName(request.getFatherName());
        details.setMotherName(request.getMotherName());
        details.setGender(request.getGender());
        details.setDateOfBirth(request.getDateOfBirth());
        details.setMaritalStatus(request.getMaritalStatus());
        details.setSpouseName(request.getSpouseName());
        details.setNid(request.getNid());
        details.setRecordDt(new Date());

        ClientDetails updatedDetails = clientDetailsRepository.save(details);

        return mapToResponse(updatedDetails);
    }

    private ClientDetailsResponse mapToResponse(ClientDetails details) {
        return new ClientDetailsResponse(
                details.getClientId(),
                details.getFatherName(),
                details.getMotherName(),
                details.getGender(),
                details.getDateOfBirth(),
                details.getMaritalStatus(),
                details.getSpouseName(),
                details.getNid()
        );
    }
}