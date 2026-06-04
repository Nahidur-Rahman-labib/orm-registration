package com.client.orm_registration.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer officeId;
    private Integer clAccSl;
    private String accountNo;
    private Long clientId;
    private String accountTitle;
    private Date accountOpenDt;
    private Date effectiveDt;
    private Date expiryDt;
    private BigDecimal limitAmt;
    private String entityId;
}