package com.client.orm_registration.service;

import com.client.orm_registration.dto.AccountRequest;
import com.client.orm_registration.dto.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse addAccount(AccountRequest request);

    AccountResponse updateAccount(Integer officeId, Integer clAccSl, AccountRequest request);

    void deleteAccount(Integer officeId, Integer clAccSl);

    List<AccountResponse> getAccountsByClientId(Long clientId);
}