package com.client.orm_registration.repository;

import com.client.orm_registration.entity.Account;
import com.client.orm_registration.entity.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountId> {
    List<Account> findByClientClientId(Long clientId);
}