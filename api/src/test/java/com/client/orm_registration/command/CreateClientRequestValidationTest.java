package com.client.orm_registration.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateClientRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void clientName_WhenBlank_ShouldFailValidation() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("");

        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Client Name is required"));
    }

    @Test
    void clientName_WhenTooShort_ShouldFailValidation() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("A");

        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Client Name must be between 2 and 100 characters"));
    }

    @Test
    void clientName_WhenContainsNumbers_ShouldFailValidation() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("Client123");

        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
        assertThat(violations)
                .anyMatch(v -> v.getMessage().equals("Client Name can only contain letters and spaces"));
    }

    @Test
    void clientName_WhenValid_ShouldPassValidation() {
        CreateClientRequest request = new CreateClientRequest();
        request.setClientName("Test Client");

        Set<ConstraintViolation<CreateClientRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}