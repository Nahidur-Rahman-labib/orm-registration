package com.client.orm_registration.controller;

import com.client.orm_registration.dto.AddressRequest;
import com.client.orm_registration.dto.AddressResponse;
import com.client.orm_registration.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{clientId}/addresses")
    public ResponseEntity<AddressResponse> addAddress(
            @PathVariable Long clientId,
            @RequestBody AddressRequest request
    ) {
        request.setClientId(clientId);
        AddressResponse response = addressService.addAddress(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{clientId}/addresses")
    public ResponseEntity<List<AddressResponse>> getAddressesByClientId(@PathVariable Long clientId) {
        List<AddressResponse> response = addressService.getAddressesByClientId(clientId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{clientId}/addresses/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(
            @PathVariable Long clientId,
            @PathVariable Long addressId,
            @RequestBody AddressRequest request
    ) {
        request.setClientId(clientId);
        AddressResponse response = addressService.updateAddress(addressId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{clientId}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long clientId,
            @PathVariable Long addressId
    ) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}