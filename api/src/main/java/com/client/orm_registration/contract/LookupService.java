package com.client.orm_registration.contract;

import com.client.orm_registration.query.LookupResponse;

import java.util.List;
public interface LookupService {

    List<LookupResponse> getAllCountries();

    List<LookupResponse> getDivisionsByCountryId(Integer countryId);

    List<LookupResponse> getDistrictsByDivisionId(Integer divisionId);

    List<LookupResponse> getThanasByDistrictId(Integer districtId);

    List<LookupResponse> getAllAddressTypes();
}