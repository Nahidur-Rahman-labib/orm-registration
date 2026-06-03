package com.client.orm_registration.controller;

import com.client.orm_registration.dto.CreateClientRequest;
import com.client.orm_registration.dto.CreateClientResponse;
import com.client.orm_registration.dto.GetClientResponse;
import com.client.orm_registration.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<CreateClientResponse> createClient(@RequestBody CreateClientRequest request) {
        CreateClientResponse response = clientService.createClient(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> getClientById(@PathVariable Long clientId) {
        GetClientResponse response = clientService.getClientById(clientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetClientResponse>> getAllClients() {
        List<GetClientResponse> response = clientService.getAllClients();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clientId}")
    public ResponseEntity<GetClientResponse> updateClient(
            @PathVariable Long clientId,
            @RequestBody CreateClientRequest request
    ) {
        GetClientResponse response = clientService.updateClient(clientId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.noContent().build();
    }
}