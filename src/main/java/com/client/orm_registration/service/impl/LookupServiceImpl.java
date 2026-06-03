package com.client.orm_registration.service.impl;

import com.client.orm_registration.dto.LookupResponse;
import com.client.orm_registration.repository.*;
import com.client.orm_registration.service.LookupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LookupServiceImpl implements LookupService {

    private final CountryRepository countryRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final ThanaRepository thanaRepository;
    private final AddressTypeRepository addressTypeRepository;

    public LookupServiceImpl(
            CountryRepository countryRepository,
            DivisionRepository divisionRepository,
            DistrictRepository districtRepository,
            ThanaRepository thanaRepository,
            AddressTypeRepository addressTypeRepository
    ) {
        this.countryRepository = countryRepository;
        this.divisionRepository = divisionRepository;
        this.districtRepository = districtRepository;
        this.thanaRepository = thanaRepository;
        this.addressTypeRepository = addressTypeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(country -> new LookupResponse(
                        country.getCountryId(),
                        country.getCountryName()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getDivisionsByCountryId(Integer countryId) {
        return divisionRepository.findByCountryCountryId(countryId)
                .stream()
                .map(division -> new LookupResponse(
                        division.getDivisionId(),
                        division.getDivisionName()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getDistrictsByDivisionId(Integer divisionId) {
        return districtRepository.findByDivisionDivisionId(divisionId)
                .stream()
                .map(district -> new LookupResponse(
                        district.getDistrictId(),
                        district.getDistrictName()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getThanasByDistrictId(Integer districtId) {
        return thanaRepository.findByDistrictDistrictId(districtId)
                .stream()
                .map(thana -> new LookupResponse(
                        thana.getThanaId(),
                        thana.getThanaName()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getAllAddressTypes() {
        return addressTypeRepository.findAll()
                .stream()
                .map(addressType -> new LookupResponse(
                        addressType.getAddrTypeId(),
                        addressType.getAddrTypeNm()
                ))
                .toList();
    }
}