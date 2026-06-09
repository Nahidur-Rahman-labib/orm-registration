package com.client.orm_registration.controller;

import jakarta.validation.Valid;
import com.client.orm_registration.contract.ClientDetailsService;
import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.query.ClientDetailsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clients")
public class ClientDetailsController {

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsController(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/{id}/details")
    public ResponseEntity<?> saveDetails(@PathVariable Long id,
                                         @Valid @RequestBody ClientDetailsRequest request,
                                         BindingResult bindingResult) {
        // Return validation errors if any
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getAllErrors()
                            .stream()
                            .map(err -> err.getDefaultMessage())
                            .collect(Collectors.toList()));
        }

        request.setClientId(id);
        ClientDetailsResponse response = clientDetailsService.saveClientDetails(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<ClientDetailsResponse> getDetails(@PathVariable Long id) {
        return ResponseEntity.ok(clientDetailsService.getClientDetailsByClientId(id));
    }

    @PutMapping("/{id}/details")
    public ResponseEntity<?> updateDetails(@PathVariable Long id,
                                           @Valid @RequestBody ClientDetailsRequest request,
                                           BindingResult bindingResult) {
        // Return validation errors if any
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(bindingResult.getAllErrors()
                            .stream()
                            .map(err -> err.getDefaultMessage())
                            .collect(Collectors.toList()));
        }

        ClientDetailsResponse response = clientDetailsService.updateClientDetails(id, request);
        return ResponseEntity.ok(response);
    }
}