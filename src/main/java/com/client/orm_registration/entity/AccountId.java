package com.client.orm_registration.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class AccountId implements Serializable {

    @Column(name = "OFFICE_ID")
    private Integer officeId;

    @Column(name = "CL_ACC_SL")
    private Integer clAccSl;

    @Override
    public int hashCode() { return officeId.hashCode() + clAccSl.hashCode(); }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        AccountId that = (AccountId) obj;
        return officeId.equals(that.officeId) && clAccSl.equals(that.clAccSl);
    }
}