package com.client.orm_registration.controller;

import com.client.orm_registration.dto.LookupResponse;
import com.client.orm_registration.service.LookupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lookup")
@CrossOrigin(origins = "*")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<LookupResponse>> getAllCountries() {
        return ResponseEntity.ok(lookupService.getAllCountries());
    }

    @GetMapping("/divisions/{countryId}")
    public ResponseEntity<List<LookupResponse>> getDivisionsByCountryId(@PathVariable Integer countryId) {
        return ResponseEntity.ok(lookupService.getDivisionsByCountryId(countryId));
    }

    @GetMapping("/districts/{divisionId}")
    public ResponseEntity<List<LookupResponse>> getDistrictsByDivisionId(@PathVariable Integer divisionId) {
        return ResponseEntity.ok(lookupService.getDistrictsByDivisionId(divisionId));
    }

    @GetMapping("/thanas/{districtId}")
    public ResponseEntity<List<LookupResponse>> getThanasByDistrictId(@PathVariable Integer districtId) {
        return ResponseEntity.ok(lookupService.getThanasByDistrictId(districtId));
    }

    @GetMapping("/address-types")
    public ResponseEntity<List<LookupResponse>> getAllAddressTypes() {
        return ResponseEntity.ok(lookupService.getAllAddressTypes());
    }
}