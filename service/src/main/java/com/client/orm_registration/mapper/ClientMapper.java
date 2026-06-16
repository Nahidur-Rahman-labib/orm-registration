package com.client.orm_registration.mapper;

import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.query.GetClientResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ClientMapper {


    public Client toEntity(CreateClientRequest request) {
        if (request == null) return null;

        Client client = new Client();
        client.setClientName(request.getClientName());
        client.setApproveFlag(0);
        client.setRecordUserId("SYSTEM");
        client.setRecordDt(new Date());

        return client;
    }


    public GetClientResponse toResponse(Client client, String message) {
        if (client == null) return null;

        return new GetClientResponse(
                client.getClientId(),
                client.getClientName(),
                message
        );
    }


    public List<GetClientResponse> toResponseList(List<Client> clients) {
        if (clients == null) return List.of();

        return clients.stream()
                .map(c -> toResponse(c, "SUCCESS"))
                .toList();
    }


    public void updateEntity(Client client, CreateClientRequest request) {
        if (client == null || request == null) return;

        client.setClientName(request.getClientName());
        client.setRecordDt(new Date());
    }
}