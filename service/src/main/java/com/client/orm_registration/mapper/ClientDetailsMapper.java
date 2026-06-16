package com.client.orm_registration.mapper;

import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.entity.ClientDetails;
import com.client.orm_registration.query.ClientDetailsResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ClientDetailsMapper {


    public ClientDetails toEntity(ClientDetailsRequest request, Client client) {
        if (request == null || client == null) return null;

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

        return details;
    }


    public ClientDetailsResponse toResponse(ClientDetails details) {
        if (details == null) return null;

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


    public void updateEntity(ClientDetails details, ClientDetailsRequest request) {
        if (details == null || request == null) return;

        details.setFatherName(request.getFatherName());
        details.setMotherName(request.getMotherName());
        details.setGender(request.getGender());
        details.setDateOfBirth(request.getDateOfBirth());
        details.setMaritalStatus(request.getMaritalStatus());
        details.setSpouseName(request.getSpouseName());
        details.setNid(request.getNid());

        details.setRecordDt(new Date());
    }
}