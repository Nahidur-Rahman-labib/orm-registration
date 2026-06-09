package com.client.orm_registration.controller;

import com.client.orm_registration.contract.AddressService;
import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.query.AddressResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/{id}/addresses")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable Long id, @RequestBody AddressRequest request) {
        request.setClientId(id);
        return ResponseEntity.ok(addressService.addAddress(request));
    }

    @GetMapping("/{id}/addresses")
    public ResponseEntity<List<AddressResponse>> getAddresses(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.getAddressesByClientId(id));
    }

    @PutMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @PathVariable Long addressId, @RequestBody AddressRequest request) {
        request.setClientId(id);
        return ResponseEntity.ok(addressService.updateAddress(addressId, request));
    }

    @DeleteMapping("/{id}/addresses/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id, @PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}