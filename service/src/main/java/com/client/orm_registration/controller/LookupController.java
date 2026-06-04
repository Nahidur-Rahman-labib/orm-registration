package com.client.orm_registration.controller;

import com.client.orm_registration.contract.LookupService;
import com.client.orm_registration.query.LookupResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lookup")
public class LookupController {

    private final LookupService lookupService;

    public LookupController(LookupService lookupService) {
        this.lookupService = lookupService;
    }

    @GetMapping("/countries")
    public ResponseEntity<List<LookupResponse>> getCountries() {
        return ResponseEntity.ok(lookupService.getAllCountries());
    }

    @GetMapping("/divisions/{countryId}")
    public ResponseEntity<List<LookupResponse>> getDivisions(@PathVariable Integer countryId) {
        return ResponseEntity.ok(lookupService.getDivisionsByCountryId(countryId));
    }

    @GetMapping("/districts/{divisionId}")
    public ResponseEntity<List<LookupResponse>> getDistricts(@PathVariable Integer divisionId) {
        return ResponseEntity.ok(lookupService.getDistrictsByDivisionId(divisionId));
    }

    @GetMapping("/thanas/{districtId}")
    public ResponseEntity<List<LookupResponse>> getThanas(@PathVariable Integer districtId) {
        return ResponseEntity.ok(lookupService.getThanasByDistrictId(districtId));
    }

    @GetMapping("/address-types")
    public ResponseEntity<List<LookupResponse>> getAddressTypes() {
        return ResponseEntity.ok(lookupService.getAllAddressTypes());
    }
}