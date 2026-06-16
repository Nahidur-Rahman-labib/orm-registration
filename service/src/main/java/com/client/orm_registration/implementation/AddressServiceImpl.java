package com.client.orm_registration.implementation;

import com.client.orm_registration.contract.AddressService;
import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.mapper.AddressMapper;
import com.client.orm_registration.query.AddressResponse;
import com.client.orm_registration.entity.*;
import com.client.orm_registration.repository.*;
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
    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository,
                              ClientRepository clientRepository,
                              AddressTypeRepository addressTypeRepository,
                              CountryRepository countryRepository,
                              DivisionRepository divisionRepository,
                              DistrictRepository districtRepository,
                              ThanaRepository thanaRepository,
                              AddressMapper addressMapper) {

        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.addressTypeRepository = addressTypeRepository;
        this.countryRepository = countryRepository;
        this.divisionRepository = divisionRepository;
        this.districtRepository = districtRepository;
        this.thanaRepository = thanaRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    @Transactional
    public AddressResponse addAddress(AddressRequest request) {

        Address address = new Address();

        fillAndMap(address, request);

        address.setApproveFlag(0);
        address.setRecordUserId("SYSTEM");
        address.setRecordDt(new Date());

        return addressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + addressId));

        fillAndMap(address, request);

        return addressMapper.toResponse(addressRepository.save(address));
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
                .map(addressMapper::toResponse)
                .toList();
    }
    private void fillAndMap(Address address, AddressRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        AddressType type = request.getAddressTypeId() != null
                ? addressTypeRepository.findById(request.getAddressTypeId()).orElse(null)
                : null;

        Country country = request.getCountryId() != null
                ? countryRepository.findById(request.getCountryId()).orElse(null)
                : null;

        Division division = request.getDivisionId() != null
                ? divisionRepository.findById(request.getDivisionId()).orElse(null)
                : null;

        District district = request.getDistrictId() != null
                ? districtRepository.findById(request.getDistrictId()).orElse(null)
                : null;

        Thana thana = request.getThanaId() != null
                ? thanaRepository.findById(request.getThanaId()).orElse(null)
                : null;

        addressMapper.updateEntity(
                address,
                request,
                client,
                type,
                country,
                division,
                district,
                thana
        );
    }
}