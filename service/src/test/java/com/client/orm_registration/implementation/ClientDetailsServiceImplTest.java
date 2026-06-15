package com.client.orm_registration.implementation;

import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.entity.ClientDetails;
import com.client.orm_registration.query.ClientDetailsResponse;
import com.client.orm_registration.repository.ClientDetailsRepository;
import com.client.orm_registration.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientDetailsServiceImplTest {

    @Mock
    private ClientDetailsRepository clientDetailsRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientDetailsServiceImpl clientDetailsService;

    private Client testClient;
    private ClientDetails testDetails;
    private ClientDetailsRequest testRequest;
    private Date testDateOfBirth;

    @BeforeEach
    void setUp() {
        testDateOfBirth = new Date(946684800000L); // 1 January 2000

        testClient = new Client();
        testClient.setClientId(1L);
        testClient.setClientName("Test Client");

        testRequest = new ClientDetailsRequest();
        testRequest.setClientId(1L);
        testRequest.setFatherName("Test Father");
        testRequest.setMotherName("Test Mother");
        testRequest.setGender("Male");
        testRequest.setDateOfBirth(testDateOfBirth);
        testRequest.setMaritalStatus("Single");
        testRequest.setSpouseName(null);
        testRequest.setNid("1234567890");

        testDetails = new ClientDetails();
        testDetails.setClientId(1L);
        testDetails.setClient(testClient);
        testDetails.setFatherName("Test Father");
        testDetails.setMotherName("Test Mother");
        testDetails.setGender("Male");
        testDetails.setDateOfBirth(testDateOfBirth);
        testDetails.setMaritalStatus("Single");
        testDetails.setSpouseName(null);
        testDetails.setNid("1234567890");
        testDetails.setApproveFlag(0);
        testDetails.setRecordUserId("SYSTEM");
        testDetails.setRecordDt(new Date());
    }

    // ══ 1. saveClientDetails() ═══════════════════════════════════════════════

    @Test
    void saveClientDetails_WithValidRequest_ShouldReturnSavedResponse() {
        // ARRANGE
        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(testClient));

        /*
         * In a real JPA operation, @MapsId copies the Client ID into
         * ClientDetails.clientId.
         *
         * Mockito does not execute Hibernate behavior, so the mock manually
         * sets the shared primary key.
         */
        when(clientDetailsRepository.save(any(ClientDetails.class)))
                .thenAnswer(invocation -> {
                    ClientDetails details = invocation.getArgument(0);
                    details.setClientId(1L);
                    return details;
                });

        // ACT
        ClientDetailsResponse result =
                clientDetailsService.saveClientDetails(testRequest);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getFatherName()).isEqualTo("Test Father");
        assertThat(result.getMotherName()).isEqualTo("Test Mother");
        assertThat(result.getGender()).isEqualTo("Male");
        assertThat(result.getDateOfBirth()).isEqualTo(testDateOfBirth);
        assertThat(result.getMaritalStatus()).isEqualTo("Single");
        assertThat(result.getSpouseName()).isNull();
        assertThat(result.getNid()).isEqualTo("1234567890");

        verify(clientRepository, times(1)).findById(1L);
        verify(clientDetailsRepository, times(1))
                .save(any(ClientDetails.class));
    }

    @Test
    void saveClientDetails_ShouldSetDefaultAuditValues() {
        // ARRANGE
        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(testClient));

        when(clientDetailsRepository.save(any(ClientDetails.class)))
                .thenAnswer(invocation -> {
                    ClientDetails details = invocation.getArgument(0);
                    details.setClientId(1L);
                    return details;
                });

        ArgumentCaptor<ClientDetails> captor =
                ArgumentCaptor.forClass(ClientDetails.class);

        // ACT
        clientDetailsService.saveClientDetails(testRequest);

        // ASSERT
        verify(clientDetailsRepository).save(captor.capture());

        ClientDetails savedDetails = captor.getValue();

        assertThat(savedDetails.getClient()).isEqualTo(testClient);
        assertThat(savedDetails.getApproveFlag()).isZero();
        assertThat(savedDetails.getRecordUserId()).isEqualTo("SYSTEM");
        assertThat(savedDetails.getRecordDt()).isNotNull();
    }

    @Test
    void saveClientDetails_WithInvalidClientId_ShouldThrowRuntimeException() {
        // ARRANGE
        when(clientRepository.findById(99L))
                .thenReturn(Optional.empty());

        testRequest.setClientId(99L);

        // ACT AND ASSERT
        assertThatThrownBy(() ->
                clientDetailsService.saveClientDetails(testRequest)
        )
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client not found with ID: 99");

        verify(clientRepository, times(1)).findById(99L);
        verify(clientDetailsRepository, never())
                .save(any(ClientDetails.class));
    }

    // ══ 2. getClientDetailsByClientId() ══════════════════════════════════════

    @Test
    void getClientDetailsByClientId_WhenDetailsExist_ShouldReturnResponse() {
        // ARRANGE
        when(clientDetailsRepository.findByClient_ClientId(1L))
                .thenReturn(Optional.of(testDetails));

        // ACT
        ClientDetailsResponse result =
                clientDetailsService.getClientDetailsByClientId(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getFatherName()).isEqualTo("Test Father");
        assertThat(result.getMotherName()).isEqualTo("Test Mother");
        assertThat(result.getNid()).isEqualTo("1234567890");

        verify(clientDetailsRepository, times(1))
                .findByClient_ClientId(1L);
    }

    @Test
    void getClientDetailsByClientId_WhenDetailsDoNotExist_ShouldReturnEmptyResponse() {
        // ARRANGE
        when(clientDetailsRepository.findByClient_ClientId(99L))
                .thenReturn(Optional.empty());

        // ACT
        ClientDetailsResponse result =
                clientDetailsService.getClientDetailsByClientId(99L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isNull();
        assertThat(result.getFatherName()).isNull();
        assertThat(result.getMotherName()).isNull();
        assertThat(result.getNid()).isNull();

        verify(clientDetailsRepository, times(1))
                .findByClient_ClientId(99L);
    }

    // ══ 3. updateClientDetails() ═════════════════════════════════════════════

    @Test
    void updateClientDetails_WithValidClientId_ShouldReturnUpdatedResponse() {
        // ARRANGE
        ClientDetailsRequest updateRequest = new ClientDetailsRequest();
        updateRequest.setFatherName("Updated Father");
        updateRequest.setMotherName("Updated Mother");
        updateRequest.setGender("Female");
        updateRequest.setDateOfBirth(testDateOfBirth);
        updateRequest.setMaritalStatus("Married");
        updateRequest.setSpouseName("Test Spouse");
        updateRequest.setNid("9876543210");

        when(clientDetailsRepository.findById(1L))
                .thenReturn(Optional.of(testDetails));

        when(clientDetailsRepository.save(any(ClientDetails.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        ClientDetailsResponse result =
                clientDetailsService.updateClientDetails(1L, updateRequest);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getFatherName()).isEqualTo("Updated Father");
        assertThat(result.getMotherName()).isEqualTo("Updated Mother");
        assertThat(result.getGender()).isEqualTo("Female");
        assertThat(result.getMaritalStatus()).isEqualTo("Married");
        assertThat(result.getSpouseName()).isEqualTo("Test Spouse");
        assertThat(result.getNid()).isEqualTo("9876543210");

        verify(clientDetailsRepository, times(1)).findById(1L);
        verify(clientDetailsRepository, times(1))
                .save(testDetails);
    }

    @Test
    void updateClientDetails_ShouldUpdateRecordDate() {
        // ARRANGE
        Date previousRecordDate = new Date(1000L);
        testDetails.setRecordDt(previousRecordDate);

        when(clientDetailsRepository.findById(1L))
                .thenReturn(Optional.of(testDetails));

        when(clientDetailsRepository.save(any(ClientDetails.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // ACT
        clientDetailsService.updateClientDetails(1L, testRequest);

        // ASSERT
        assertThat(testDetails.getRecordDt()).isAfter(previousRecordDate);
    }

    @Test
    void updateClientDetails_WithInvalidClientId_ShouldThrowRuntimeException() {
        // ARRANGE
        when(clientDetailsRepository.findById(99L))
                .thenReturn(Optional.empty());

        // ACT AND ASSERT
        assertThatThrownBy(() ->
                clientDetailsService.updateClientDetails(99L, testRequest)
        )
                .isInstanceOf(RuntimeException.class)
                .hasMessage(
                        "Client details not found with client ID: 99"
                );

        verify(clientDetailsRepository, times(1)).findById(99L);
        verify(clientDetailsRepository, never())
                .save(any(ClientDetails.class));
    }
}