package com.client.orm_registration.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetClientResponse {
    private Long clientId;
    private String clientName;
    private String status;
}