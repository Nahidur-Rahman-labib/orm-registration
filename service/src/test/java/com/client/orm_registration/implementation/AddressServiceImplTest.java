package com.client.orm_registration.implementation;

import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.entity.*;
import com.client.orm_registration.query.AddressResponse;
import com.client.orm_registration.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock private AddressRepository addressRepository;
    @Mock private ClientRepository clientRepository;
    @Mock private AddressTypeRepository addressTypeRepository;
    @Mock private CountryRepository countryRepository;
    @Mock private DivisionRepository divisionRepository;
    @Mock private DistrictRepository districtRepository;
    @Mock private ThanaRepository thanaRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setClientId(1L);
    }

    @Test
    void addAddress_ShouldSaveSuccessfully() {

        AddressRequest request = new AddressRequest();
        request.setClientId(1L);
        request.setAddress("Dhaka");
        request.setCity("Dhaka");
        request.setMobileNo("01700000000");

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(client));

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(i -> {
                    Address a = i.getArgument(0);
                    a.setAddressId(10L);
                    return a;
                });

        AddressResponse response = addressService.addAddress(request);

        assertThat(response.getAddressId()).isEqualTo(10L);
        assertThat(response.getAddress()).isEqualTo("Dhaka");

        verify(addressRepository, times(1)).save(any(Address.class));
    }

    @Test
    void updateAddress_ShouldUpdateSuccessfully() {

        Address existing = new Address();
        existing.setAddressId(10L);
        existing.setClient(client);

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(client));

        when(addressRepository.findById(10L)).thenReturn(Optional.of(existing));

        when(addressRepository.save(any(Address.class)))
                .thenAnswer(i -> i.getArgument(0));

        AddressRequest request = new AddressRequest();
        request.setClientId(1L);
        request.setAddress("Updated Dhaka");

        AddressResponse response = addressService.updateAddress(10L, request);

        assertThat(response.getAddress()).isEqualTo("Updated Dhaka");
    }

    @Test
    void deleteAddress_ShouldDeleteSuccessfully() {

        when(addressRepository.existsById(10L)).thenReturn(true);

        addressService.deleteAddress(10L);

        verify(addressRepository, times(1)).deleteById(10L);
    }
}