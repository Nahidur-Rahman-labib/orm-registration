package com.client.orm_registration.implementation;

import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.query.GetClientResponse;
import com.client.orm_registration.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private Client testClient;
    private CreateClientRequest testRequest;

    @BeforeEach
    void setUp() {
        testClient = new Client();
        testClient.setClientId(1L);
        testClient.setClientName("Test Client");
        testClient.setApproveFlag(0);
        testClient.setRecordUserId("SYSTEM");
        testClient.setRecordDt(new Date());

        testRequest = new CreateClientRequest();
        testRequest.setClientName("Test Client");
    }

    // ══ 1. createClient() ═════════════════════════════════════════════════════

    @Test
    void createClient_WithValidRequest_ShouldReturnSuccessResponse() {
        // ARRANGE
        when(clientRepository.save(any(Client.class))).thenReturn(testClient);

        // ACT
        GetClientResponse result = clientService.createClient(testRequest);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getClientName()).isEqualTo("Test Client");
        assertThat(result.getStatus()).isEqualTo("Client created successfully");
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void createClient_ShouldSetSystemAsRecordUserAndZeroApproveFlag() {
        // ARRANGE
        when(clientRepository.save(any(Client.class))).thenAnswer(invocation -> {
            Client saved = invocation.getArgument(0);
            saved.setClientId(1L);
            return saved;
        });

        // ACT
        clientService.createClient(testRequest);

        // ASSERT
        verify(clientRepository).save(argThat(client ->
                "SYSTEM".equals(client.getRecordUserId()) &&
                        client.getApproveFlag() == 0 &&
                        client.getRecordDt() != null
        ));
    }

    // ══ 2. getClientById() ════════════════════════════════════════════════════

    @Test
    void getClientById_WithValidId_ShouldReturnClient() {
        // ARRANGE
        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));

        // ACT
        GetClientResponse result = clientService.getClientById(1L);

        // ASSERT
        assertThat(result).isNotNull();
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getClientName()).isEqualTo("Test Client");
        assertThat(result.getStatus()).isEqualTo("SUCCESS");
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void getClientById_WithInvalidId_ShouldThrowRuntimeException() {
        // ARRANGE
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> clientService.getClientById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client not found with ID: 99");
        verify(clientRepository, times(1)).findById(99L);
    }

    // ══ 3. getAllClients() ════════════════════════════════════════════════════

    @Test
    void getAllClients_WhenClientsExist_ShouldReturnList() {
        // ARRANGE
        Client second = new Client();
        second.setClientId(2L);
        second.setClientName("Second Client");
        when(clientRepository.findAll()).thenReturn(List.of(testClient, second));

        // ACT
        List<GetClientResponse> result = clientService.getAllClients();

        // ASSERT
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getClientName()).isEqualTo("Test Client");
        assertThat(result.get(1).getClientName()).isEqualTo("Second Client");
        assertThat(result).allMatch(r -> "SUCCESS".equals(r.getStatus()));
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getAllClients_WhenNoClients_ShouldReturnEmptyList() {
        // ARRANGE
        when(clientRepository.findAll()).thenReturn(List.of());

        // ACT
        List<GetClientResponse> result = clientService.getAllClients();

        // ASSERT
        assertThat(result).isNotNull().isEmpty();
    }

    // ══ 4. updateClient() ════════════════════════════════════════════════════

    @Test
    void updateClient_WithValidId_ShouldReturnUpdatedResponse() {
        // ARRANGE
        CreateClientRequest updateRequest = new CreateClientRequest();
        updateRequest.setClientName("Updated Client");

        Client updatedClient = new Client();
        updatedClient.setClientId(1L);
        updatedClient.setClientName("Updated Client");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(testClient));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        // ACT
        GetClientResponse result = clientService.updateClient(1L, updateRequest);

        // ASSERT
        assertThat(result.getClientId()).isEqualTo(1L);
        assertThat(result.getClientName()).isEqualTo("Updated Client");
        assertThat(result.getStatus()).isEqualTo("Client updated successfully");
        verify(clientRepository, times(1)).findById(1L);
        verify(clientRepository, times(1)).save(any(Client.class));
    }

    @Test
    void updateClient_WithInvalidId_ShouldThrowRuntimeException() {
        // ARRANGE
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        // ACT & ASSERT
        assertThatThrownBy(() -> clientService.updateClient(99L, testRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client not found with ID: 99");
        verify(clientRepository, never()).save(any(Client.class));
    }

    // ══ 5. deleteClient() ════════════════════════════════════════════════════

    @Test
    void deleteClient_WithValidId_ShouldDeleteSuccessfully() {
        // ARRANGE
        when(clientRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clientRepository).deleteById(1L);

        // ACT
        assertThatCode(() -> clientService.deleteClient(1L))
                .doesNotThrowAnyException();

        // VERIFY
        verify(clientRepository, times(1)).existsById(1L);
        verify(clientRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteClient_WithInvalidId_ShouldThrowRuntimeException() {
        // ARRANGE
        when(clientRepository.existsById(99L)).thenReturn(false);

        // ACT & ASSERT
        assertThatThrownBy(() -> clientService.deleteClient(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client not found with ID: 99");
        verify(clientRepository, never()).deleteById(any());
    }
}