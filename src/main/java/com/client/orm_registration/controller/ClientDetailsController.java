package com.client.orm_registration.controller;

import com.client.orm_registration.dto.ClientDetailsRequest;
import com.client.orm_registration.dto.ClientDetailsResponse;
import com.client.orm_registration.service.ClientDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/{clientId}/details")
    public ResponseEntity<ClientDetailsResponse> saveClientDetails(
            @PathVariable Long clientId,
            @RequestBody ClientDetailsRequest request
    ) {
        request.setClientId(clientId);
        ClientDetailsResponse response = clientDetailsService.saveClientDetails(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}/details")
    public ResponseEntity<ClientDetailsResponse> getClientDetails(@PathVariable Long clientId) {
        ClientDetailsResponse response = clientDetailsService.getClientDetailsByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clientId}/details")
    public ResponseEntity<ClientDetailsResponse> updateClientDetails(
            @PathVariable Long clientId,
            @RequestBody ClientDetailsRequest request
    ) {
        ClientDetailsResponse response = clientDetailsService.updateClientDetails(clientId, request);
        return ResponseEntity.ok(response);
    }
}