package com.client.orm_registration.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequest {
    private Integer officeId;
    private Integer clAccSl;
    private Long clientId;

    private String accountTitle;
    private Date accountOpenDt;
    private Date effectiveDt;
    private Date expiryDt;
    private BigDecimal limitAmt;
    private String entityId;
}