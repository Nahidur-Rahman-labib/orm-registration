package com.client.orm_registration.repository;

import com.client.orm_registration.entity.Client;
import com.client.orm_registration.entity.ClientDetails;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ClientDetailsRepositoryIntegrationTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void saveAndFindByClientId_ShouldReturnClientDetails() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Repository Client"
        );

        ClientDetails details = createDetails(client);

        // ACT
        ClientDetails saved =
                clientDetailsRepository.saveAndFlush(details);

        entityManager.clear();

        Optional<ClientDetails> result =
                clientDetailsRepository.findByClient_ClientId(
                        client.getClientId()
                );

        // ASSERT
        assertThat(saved.getClientId())
                .isEqualTo(client.getClientId());

        assertThat(result).isPresent();
        assertThat(result.get().getClientId())
                .isEqualTo(client.getClientId());
        assertThat(result.get().getFatherName())
                .isEqualTo("Test Father");
        assertThat(result.get().getMotherName())
                .isEqualTo("Test Mother");
        assertThat(result.get().getNid())
                .isEqualTo("1234567890");
    }

    @Test
    void findByClientId_WhenDetailsDoNotExist_ShouldReturnEmptyOptional() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST No Details Client"
        );

        // ACT
        Optional<ClientDetails> result =
                clientDetailsRepository.findByClient_ClientId(
                        client.getClientId()
                );

        // ASSERT
        assertThat(result).isEmpty();
    }

    @Test
    void updateClientDetails_ShouldPersistUpdatedValues() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Update Details Client"
        );

        ClientDetails details =
                clientDetailsRepository.saveAndFlush(
                        createDetails(client)
                );

        entityManager.clear();

        ClientDetails existing =
                clientDetailsRepository.findById(
                        details.getClientId()
                ).orElseThrow();

        // ACT
        existing.setFatherName("Updated Father");
        existing.setMotherName("Updated Mother");
        existing.setMaritalStatus("Married");
        existing.setSpouseName("Test Spouse");
        existing.setNid("9876543210");
        existing.setRecordDt(new Date());

        clientDetailsRepository.saveAndFlush(existing);

        entityManager.clear();

        ClientDetails updated =
                clientDetailsRepository.findById(
                        client.getClientId()
                ).orElseThrow();

        // ASSERT
        assertThat(updated.getFatherName())
                .isEqualTo("Updated Father");
        assertThat(updated.getMotherName())
                .isEqualTo("Updated Mother");
        assertThat(updated.getMaritalStatus())
                .isEqualTo("Married");
        assertThat(updated.getSpouseName())
                .isEqualTo("Test Spouse");
        assertThat(updated.getNid())
                .isEqualTo("9876543210");
    }

    private Client saveTestClient(String clientName) {
        Client client = new Client();
        client.setClientName(clientName);
        client.setApproveFlag(0);
        client.setRecordUserId("TEST");
        client.setRecordDt(new Date());

        return clientRepository.saveAndFlush(client);
    }

    private ClientDetails createDetails(Client client) {
        ClientDetails details = new ClientDetails();

        /*
         * Do not manually set clientId.
         * @MapsId takes the ID from the parent Client.
         */
        details.setClient(client);
        details.setFatherName("Test Father");
        details.setMotherName("Test Mother");
        details.setGender("Male");
        details.setDateOfBirth(new Date(946684800000L));
        details.setMaritalStatus("Single");
        details.setSpouseName(null);
        details.setNid("1234567890");
        details.setApproveFlag(0);
        details.setRecordUserId("TEST");
        details.setRecordDt(new Date());

        return details;
    }
}