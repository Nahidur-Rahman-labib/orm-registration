package com.client.orm_registration.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateClientRequest {

    @NotBlank(message = "Client Name is required")
    @Size(min = 2, max = 100, message = "Client Name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Client Name can only contain letters and spaces")
    private String clientName;
}