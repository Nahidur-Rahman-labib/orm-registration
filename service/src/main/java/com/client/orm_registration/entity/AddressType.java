package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "MD_ADDRESS_TYPE")
@Getter
@Setter
public class AddressType {

    @Id
    @Column(name = "ADDR_TYPE_ID")
    private Integer addrTypeId;

    @Column(name = "ADDR_TYPE_NM")
    private String addrTypeNm;
}