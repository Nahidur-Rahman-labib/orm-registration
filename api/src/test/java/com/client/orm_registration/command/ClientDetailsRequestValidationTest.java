package com.client.orm_registration.command;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClientDetailsRequestValidationTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory =
                Validation.buildDefaultValidatorFactory();

        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void validRequest_ShouldHaveNoValidationErrors() {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations).isEmpty();
    }

    @Test
    void blankFatherName_ShouldHaveValidationError() {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();
        request.setFatherName("   ");

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("fatherName") &&
                                violation.getMessage()
                                        .equals("Father Name is required")
                );
    }

    @Test
    void blankMotherName_ShouldHaveValidationError() {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();
        request.setMotherName("");

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("motherName") &&
                                violation.getMessage()
                                        .equals("Mother Name is required")
                );
    }

    @Test
    void futureDateOfBirth_ShouldHaveValidationError() {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();

        request.setDateOfBirth(
                new Date(System.currentTimeMillis() + 86_400_000L)
        );

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("dateOfBirth") &&
                                violation.getMessage()
                                        .equals("Date of birth must be in the past")
                );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "ABC12",
            "1234",
            "123456789012345678901"
    })
    void invalidNid_ShouldHaveValidationError(String invalidNid) {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();
        request.setNid(invalidNid);

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations)
                .anyMatch(violation ->
                        violation.getPropertyPath().toString()
                                .equals("nid") &&
                                violation.getMessage().equals(
                                        "NID must be numeric with 5-20 digits"
                                )
                );
    }

    @Test
    void nullNid_ShouldBeValidWithCurrentValidationRules() {
        // ARRANGE
        ClientDetailsRequest request = createValidRequest();
        request.setNid(null);

        // ACT
        Set<ConstraintViolation<ClientDetailsRequest>> violations =
                validator.validate(request);

        // ASSERT
        assertThat(violations).isEmpty();
    }

    private static ClientDetailsRequest createValidRequest() {
        ClientDetailsRequest request = new ClientDetailsRequest();

        request.setClientId(1L);
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