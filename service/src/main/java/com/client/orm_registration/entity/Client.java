package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "MREG_CLIENT")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(name = "client_seq", sequenceName = "SEQ_CLIENT_ID", allocationSize = 1)
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Column(name = "CLIENT_NAME", nullable = false)
    private String clientName;

    @Column(name = "APPROVE_FLAG")
    private Integer approveFlag = 0;

    @Column(name = "RECORD_USER_ID")
    private String recordUserId;

    @Column(name = "RECORD_DT")
    private Date recordDt;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private ClientDetails clientDetails;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts;
}