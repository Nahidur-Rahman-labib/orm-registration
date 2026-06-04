package com.client.orm_registration.controller;

import com.client.orm_registration.contract.ClientService;
import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.query.GetClientResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<GetClientResponse> createClient(@RequestBody CreateClientRequest request) {
        return ResponseEntity.ok(clientService.createClient(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetClientResponse> getClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public ResponseEntity<List<GetClientResponse>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetClientResponse> updateClient(@PathVariable Long id, @RequestBody CreateClientRequest request) {
        return ResponseEntity.ok(clientService.updateClient(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}