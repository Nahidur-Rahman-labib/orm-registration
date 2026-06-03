package com.client.orm_registration.service.impl;

import com.client.orm_registration.dto.AddressRequest;
import com.client.orm_registration.dto.AddressResponse;
import com.client.orm_registration.entity.*;
import com.client.orm_registration.repository.*;
import com.client.orm_registration.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final AddressTypeRepository addressTypeRepository;
    private final CountryRepository countryRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final ThanaRepository thanaRepository;

    public AddressServiceImpl(
            AddressRepository addressRepository,
            ClientRepository clientRepository,
            AddressTypeRepository addressTypeRepository,
            CountryRepository countryRepository,
            DivisionRepository divisionRepository,
            DistrictRepository districtRepository,
            ThanaRepository thanaRepository
    ) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.countryRepository = countryRepository;
        this.divisionRepository = divisionRepository;
        this.districtRepository = districtRepository;
        this.thanaRepository = thanaRepository;
    }

    @Override
    @Transactional
    public AddressResponse addAddress(AddressRequest request) {
        Address address = new Address();

        mapRequestToAddress(address, request);

        address.setApproveFlag(0);
        address.setRecordUserId("SYSTEM");
        address.setRecordDt(new Date());

        Address savedAddress = addressRepository.save(address);

        return mapToResponse(savedAddress);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        mapRequestToAddress(address, request);
        address.setRecordDt(new Date());

        Address updatedAddress = addressRepository.save(address);

        return mapToResponse(updatedAddress);
    }

    @Override
    @Transactional
    public void deleteAddress(Long addressId) {
        if (!addressRepository.existsById(addressId)) {
            throw new RuntimeException("Address not found with ID: " + addressId);
        }

        addressRepository.deleteById(addressId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByClientId(Long clientId) {
        return addressRepository.findByClientClientId(clientId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private void mapRequestToAddress(Address address, AddressRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + request.getClientId()));

        address.setClient(client);
        address.setAddress(request.getAddress());
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setMobileNo(request.getMobileNo());
        address.setEmail(request.getEmail());

        if (request.getAddressTypeId() != null) {
            AddressType addressType = addressTypeRepository.findById(request.getAddressTypeId())
                    .orElseThrow(() -> new RuntimeException("Address type not found"));
            address.setAddressType(addressType);
        }

        if (request.getCountryId() != null) {
            Country country = countryRepository.findById(request.getCountryId())
                    .orElseThrow(() -> new RuntimeException("Country not found"));
            address.setCountry(country);
        }

        if (request.getDivisionId() != null) {
            Division division = divisionRepository.findById(request.getDivisionId())
                    .orElseThrow(() -> new RuntimeException("Division not found"));
            address.setDivision(division);
        }

        if (request.getDistrictId() != null) {
            District district = districtRepository.findById(request.getDistrictId())
                    .orElseThrow(() -> new RuntimeException("District not found"));
            address.setDistrict(district);
        }

        if (request.getThanaId() != null) {
            Thana thana = thanaRepository.findById(request.getThanaId())
                    .orElseThrow(() -> new RuntimeException("Thana not found"));
            address.setThana(thana);
        }
    }

    private AddressResponse mapToResponse(Address address) {
        AddressResponse response = new AddressResponse();

        response.setAddressId(address.getAddressId());
        response.setAddress(address.getAddress());

        if (address.getAddressType() != null) {
            response.setAddressTypeId(address.getAddressType().getAddrTypeId());
        }

        if (address.getCountry() != null) {
            response.setCountryId(address.getCountry().getCountryId());
        }

        if (address.getDivision() != null) {
            response.setDivisionId(address.getDivision().getDivisionId());
        }

        if (address.getDistrict() != null) {
            response.setDistrictId(address.getDistrict().getDistrictId());
        }

        if (address.getThana() != null) {
            response.setThanaId(address.getThana().getThanaId());
        }

        response.setCity(address.getCity());
        response.setZipCode(address.getZipCode());
        response.setMobileNo(address.getMobileNo());
        response.setEmail(address.getEmail());

        return response;
    }
}