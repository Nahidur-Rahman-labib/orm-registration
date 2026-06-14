package com.client.orm_registration.controller;

import com.client.orm_registration.command.CreateClientRequest;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.query.GetClientResponse;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(
        scripts = "/sql/cleanup.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
)
class ClientControllerIntegrationTest {

    private static final String CLIENT_API = "/api/clients";

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;   // NO @Autowired

    private String baseUrl;                // field declared here

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + CLIENT_API;

        webTestClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofSeconds(10))
                .build();
    }

    @Test
    void createClient_WithValidRequest_ShouldSaveClient() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("TEST Create Client");

        webTestClient.post()
                .uri(CLIENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(GetClientResponse.class)
                .value(response -> {
                    assertThat(response).isNotNull();
                    assertThat(response.getClientId()).isNotNull();
                    assertThat(response.getClientName()).isEqualTo("TEST Create Client");
                });
    }

    @Test
    void createClient_WithInvalidName_ShouldReturnBadRequest() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("Client123");

        webTestClient.post()
                .uri(CLIENT_API)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void getClientById_ShouldReturnClient() {
        Client client = new Client();
        client.setClientName("TEST Get Client");
        client.setApproveFlag(0);
        client.setRecordUserId("TEST");
        client.setRecordDt(new Date());

        Client saved = clientRepository.save(client);

        webTestClient.get()
                .uri(CLIENT_API + "/" + saved.getClientId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GetClientResponse.class)
                .value(response ->
                        assertThat(response.getClientId()).isEqualTo(saved.getClientId())
                );
    }

    @Test
    void getAllClients_ShouldReturnList() {
        Client c1 = new Client();
        c1.setClientName("TEST One");
        c1.setApproveFlag(0);
        c1.setRecordUserId("TEST");
        c1.setRecordDt(new Date());

        Client c2 = new Client();
        c2.setClientName("TEST Two");
        c2.setApproveFlag(0);
        c2.setRecordUserId("TEST");
        c2.setRecordDt(new Date());

        clientRepository.save(c1);
        clientRepository.save(c2);

        webTestClient.get()
                .uri(CLIENT_API)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GetClientResponse.class)
                .value(list -> assertThat(list).isNotEmpty());
    }

}