package com.client.orm_registration.controller;

import com.client.orm_registration.command.AddressRequest;
import com.client.orm_registration.entity.Client;
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
@Sql(scripts = "/sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AddressControllerIntegrationTest {

    private static final String API = "/api/clients";

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .responseTimeout(Duration.ofSeconds(10))
                .build();
    }

    private Client saveClient() {
        Client c = new Client();
        c.setClientName("TEST Client");
        c.setApproveFlag(0);
        c.setRecordUserId("TEST");
        c.setRecordDt(new Date());
        return clientRepository.save(c);
    }

    @Test
    void addAddress_ShouldWork() {

        Client client = saveClient();

        AddressRequest req = new AddressRequest();
        req.setAddress("Dhaka");
        req.setCity("Dhaka");
        req.setMobileNo("01700000000");

        webTestClient.post()
                .uri(API + "/" + client.getClientId() + "/addresses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.address").isEqualTo("Dhaka");
    }

    @Test
    void getAddresses_ShouldReturnList() {

        Client client = saveClient();

        webTestClient.get()
                .uri(API + "/" + client.getClientId() + "/addresses")
                .exchange()
                .expectStatus().isOk();
    }
}