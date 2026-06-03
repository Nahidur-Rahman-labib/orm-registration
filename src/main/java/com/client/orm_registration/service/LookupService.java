package com.client.orm_registration.service;

import com.client.orm_registration.dto.LookupResponse;

import java.util.List;

public interface LookupService {

    List<LookupResponse> getAllCountries();

    List<LookupResponse> getDivisionsByCountryId(Integer countryId);

    List<LookupResponse> getDistrictsByDivisionId(Integer divisionId);

    List<LookupResponse> getThanasByDistrictId(Integer districtId);

    List<LookupResponse> getAllAddressTypes();
}