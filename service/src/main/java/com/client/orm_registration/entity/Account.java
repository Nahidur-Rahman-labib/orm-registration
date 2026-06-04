package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "MREG_AC")
@Getter
@Setter
public class Account {

    @EmbeddedId
    private AccountId id;

    @Column(name = "ACCOUNT_NO", insertable = false, updatable = false)
    private String accountNo;

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID")
    private Client client;

    @Column(name = "ACCOUNT_TITLE")
    private String accountTitle;

    @Column(name = "ACCOUNT_OPEN_DT")
    private Date accountOpenDt;

    @Column(name = "EFFECTIVE_DT")
    private Date effectiveDt;

    @Column(name = "EXPIRY_DT")
    private Date expiryDt;

    @Column(name = "LIMIT_AMT")
    private BigDecimal limitAmt;

    @Column(name = "ENTITY_ID")
    private String entityId = "C";

    @Column(name = "APPROVE_FLAG")
    private Integer approveFlag = 0;

    @Column(name = "RECORD_USER_ID")
    private String recordUserId;

    @Column(name = "RECORD_DT")
    private Date recordDt;
}