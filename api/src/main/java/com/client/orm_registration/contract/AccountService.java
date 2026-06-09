package com.client.orm_registration.contract;

import com.client.orm_registration.command.AccountRequest;
import com.client.orm_registration.query.AccountResponse;

import java.util.List;

public interface AccountService {

    AccountResponse addAccount(Long clientId, AccountRequest request);

    AccountResponse updateAccount(Integer officeId, Integer clAccSl, AccountRequest request);

    void deleteAccount(Integer officeId, Integer clAccSl);

    List<AccountResponse> getAccountsByClientId(Long clientId);
}