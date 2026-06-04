package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.LookupService;
import com.client.orm_registration.query.LookupResponse;
import com.client.orm_registration.repository.*;
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

    public LookupServiceImpl(CountryRepository countryRepository,
                             DivisionRepository divisionRepository,
                             DistrictRepository districtRepository,
                             ThanaRepository thanaRepository,
                             AddressTypeRepository addressTypeRepository) {
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
                .map(c -> new LookupResponse(c.getCountryId(), c.getCountryName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getDivisionsByCountryId(Integer countryId) {
        return divisionRepository.findByCountryCountryId(countryId)
                .stream()
                .map(d -> new LookupResponse(d.getDivisionId(), d.getDivisionName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getDistrictsByDivisionId(Integer divisionId) {
        return districtRepository.findByDivisionDivisionId(divisionId)
                .stream()
                .map(d -> new LookupResponse(d.getDistrictId(), d.getDistrictName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getThanasByDistrictId(Integer districtId) {
        return thanaRepository.findByDistrictDistrictId(districtId)
                .stream()
                .map(t -> new LookupResponse(t.getThanaId(), t.getThanaName()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LookupResponse> getAllAddressTypes() {
        return addressTypeRepository.findAll()
                .stream()
                .map(a -> new LookupResponse(a.getAddrTypeId(), a.getAddrTypeNm()))
                .toList();
    }
}