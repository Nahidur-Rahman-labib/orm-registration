package com.client.orm_registration.controller;

import com.client.orm_registration.contract.ClientDetailsService;
import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.query.ClientDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<ClientDetailsResponse> saveDetails(@PathVariable Long id, @RequestBody ClientDetailsRequest request) {
        request.setClientId(id);
        return ResponseEntity.ok(clientDetailsService.saveClientDetails(request));
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ClientDetailsResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(clientDetailsService.getClientDetailsByClientId(id));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<ClientDetailsResponse> updateDetails(@PathVariable Long id, @RequestBody ClientDetailsRequest request) {
        return ResponseEntity.ok(clientDetailsService.updateClientDetails(id, request));
    }
}