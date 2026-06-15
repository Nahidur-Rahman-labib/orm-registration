package com.client.orm_registration.controller;

import com.client.orm_registration.command.ClientDetailsRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.entity.ClientDetails;
import com.client.orm_registration.query.ClientDetailsResponse;
import com.client.orm_registration.repository.ClientDetailsRepository;
import com.client.orm_registration.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Sql(
        scripts = "/sql/cleanup.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class ClientDetailsControllerIntegrationTest {

    private static final String CLIENT_API = "/api/clients";

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofSeconds(10))
                .build();
    }

    // ══ 1. POST /api/clients/{id}/details ════════════════════════════════════

    @Test
    void saveDetails_WithValidRequest_ShouldSaveClientDetails() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Details Create Client"
        );

        ClientDetailsRequest request =
                createValidRequest();

        // ACT AND ASSERT
        webTestClient.post()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDetailsResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getClientId())
                            .isEqualTo(client.getClientId());
                    assertThat(response.getFatherName())
                            .isEqualTo("Test Father");
                    assertThat(response.getMotherName())
                            .isEqualTo("Test Mother");
                    assertThat(response.getGender())
                            .isEqualTo("Male");
                    assertThat(response.getNid())
                            .isEqualTo("1234567890");
                });

        assertThat(
                clientDetailsRepository.findById(
                        client.getClientId()
                )
        ).isPresent();
    }

    @Test
    void saveDetails_WithBlankFatherName_ShouldReturnBadRequest() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Invalid Father Client"
        );

        ClientDetailsRequest request =
                createValidRequest();

        request.setFatherName("");

        // ACT AND ASSERT
        webTestClient.post()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$[0]")
                .isEqualTo("Father Name is required");
    }

    @Test
    void saveDetails_WithInvalidNid_ShouldReturnBadRequest() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Invalid NID Client"
        );

        ClientDetailsRequest request =
                createValidRequest();

        request.setNid("ABC123");

        // ACT AND ASSERT
        webTestClient.post()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void saveDetails_WithFutureDateOfBirth_ShouldReturnBadRequest() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Future DOB Client"
        );

        ClientDetailsRequest request =
                createValidRequest();

        request.setDateOfBirth(
                new Date(System.currentTimeMillis() + 86_400_000L)
        );

        // ACT AND ASSERT
        webTestClient.post()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    // ══ 2. GET /api/clients/{id}/details ═════════════════════════════════════

    @Test
    void getDetails_WhenDetailsExist_ShouldReturnClientDetails() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Details Get Client"
        );

        saveTestDetails(client);

        // ACT AND ASSERT
        webTestClient.get()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDetailsResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getClientId())
                            .isEqualTo(client.getClientId());
                    assertThat(response.getFatherName())
                            .isEqualTo("Test Father");
                    assertThat(response.getMotherName())
                            .isEqualTo("Test Mother");
                    assertThat(response.getNid())
                            .isEqualTo("1234567890");
                });
    }

    @Test
    void getDetails_WhenDetailsDoNotExist_ShouldReturnEmptyResponse() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST No Details Client"
        );

        // ACT AND ASSERT
        webTestClient.get()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDetailsResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getClientId()).isNull();
                    assertThat(response.getFatherName()).isNull();
                    assertThat(response.getMotherName()).isNull();
                });
    }

    // ══ 3. PUT /api/clients/{id}/details ═════════════════════════════════════

    @Test
    void updateDetails_WithValidRequest_ShouldUpdateClientDetails() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Details Update Client"
        );

        saveTestDetails(client);

        ClientDetailsRequest updateRequest =
                createValidRequest();

        updateRequest.setFatherName("Updated Father");
        updateRequest.setMotherName("Updated Mother");
        updateRequest.setGender("Female");
        updateRequest.setMaritalStatus("Married");
        updateRequest.setSpouseName("Test Spouse");
        updateRequest.setNid("9876543210");

        // ACT AND ASSERT
        webTestClient.put()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(ClientDetailsResponse.class)
                .value(response -> {
                    assertThat(response.getClientId())
                            .isEqualTo(client.getClientId());
                    assertThat(response.getFatherName())
                            .isEqualTo("Updated Father");
                    assertThat(response.getMotherName())
                            .isEqualTo("Updated Mother");
                    assertThat(response.getGender())
                            .isEqualTo("Female");
                    assertThat(response.getMaritalStatus())
                            .isEqualTo("Married");
                    assertThat(response.getSpouseName())
                            .isEqualTo("Test Spouse");
                    assertThat(response.getNid())
                            .isEqualTo("9876543210");
                });

        ClientDetails savedDetails =
                clientDetailsRepository.findById(
                        client.getClientId()
                ).orElseThrow();

        assertThat(savedDetails.getFatherName())
                .isEqualTo("Updated Father");
        assertThat(savedDetails.getMotherName())
                .isEqualTo("Updated Mother");
    }

    @Test
    void updateDetails_WithBlankMotherName_ShouldReturnBadRequest() {
        // ARRANGE
        Client client = saveTestClient(
                "TEST Invalid Update Client"
        );

        saveTestDetails(client);

        ClientDetailsRequest request =
                createValidRequest();

        request.setMotherName(" ");

        // ACT AND ASSERT
        webTestClient.put()
                .uri(CLIENT_API + "/" +
                        client.getClientId() + "/details")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    private Client saveTestClient(String clientName) {
        Client client = new Client();
        client.setClientName(clientName);
        client.setApproveFlag(0);
        client.setRecordUserId("TEST");
        client.setRecordDt(new Date());

        return clientRepository.save(client);
    }

    private ClientDetails saveTestDetails(Client client) {
        ClientDetails details = new ClientDetails();

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

        return clientDetailsRepository.save(details);
    }

    private ClientDetailsRequest createValidRequest() {
        ClientDetailsRequest request =
                new ClientDetailsRequest();

        /*
         * clientId does not need to be set here.
         * The controller takes it from the URL.
         */
        request.setFatherName("Test Father");
        request.setMotherName("Test Mother");
        request.setGender("Male");
        request.setDateOfBirth(new Date(946684800000L));
        request.setMaritalStatus("Single");
        request.setSpouseName(null);
        request.setNid("1234567890");

        return request;
    }
}