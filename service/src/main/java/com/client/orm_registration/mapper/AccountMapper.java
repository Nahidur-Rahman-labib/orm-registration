package com.client.orm_registration.mapper;

import com.client.orm_registration.command.AccountRequest;
import com.client.orm_registration.entity.Account;
import com.client.orm_registration.entity.AccountId;
import com.client.orm_registration.entity.Client;
import com.client.orm_registration.query.AccountResponse;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AccountMapper {

    public AccountId toId(AccountRequest request) {
        if (request == null) return null;

        AccountId id = new AccountId();
        id.setOfficeId(request.getOfficeId());
        id.setClAccSl(request.getClAccSl());
        return id;
    }

    public Account toEntity(AccountRequest request, Client client) {
        if (request == null) return null;

        Account account = new Account();

        account.setId(toId(request));
        account.setClient(client);
        account.setAccountTitle(request.getAccountTitle());
        account.setAccountOpenDt(request.getAccountOpenDt());
        account.setEffectiveDt(request.getEffectiveDt());
        account.setExpiryDt(request.getExpiryDt());
        account.setLimitAmt(request.getLimitAmt());

        account.setEntityId(
                request.getEntityId() != null ? request.getEntityId() : "C"
        );

        account.setApproveFlag(0);
        account.setRecordUserId("SYSTEM");
        account.setRecordDt(new Date());
        account.setAccountType(request.getAccountType());

        return account;
    }

    public void updateEntity(Account account, AccountRequest request) {
        if (account == null || request == null) return;

        account.setAccountTitle(request.getAccountTitle());

        if (request.getAccountOpenDt() != null)
            account.setAccountOpenDt(request.getAccountOpenDt());

        if (request.getEffectiveDt() != null)
            account.setEffectiveDt(request.getEffectiveDt());

        if (request.getExpiryDt() != null)
            account.setExpiryDt(request.getExpiryDt());

        account.setLimitAmt(request.getLimitAmt());

        account.setEntityId(
                request.getEntityId() != null
                        ? request.getEntityId()
                        : account.getEntityId()
        );

        account.setAccountType(request.getAccountType());
        account.setRecordDt(new Date());
    }


    public AccountResponse toResponse(Account account) {
        if (account == null) return null;

        AccountResponse response = new AccountResponse();

        response.setOfficeId(account.getId().getOfficeId());
        response.setClAccSl(account.getId().getClAccSl());
        response.setAccountNo(account.getAccountNo());

        response.setClientId(
                account.getClient() != null
                        ? account.getClient().getClientId()
                        : null
        );

        response.setAccountTitle(account.getAccountTitle());
        response.setAccountOpenDt(account.getAccountOpenDt());
        response.setEffectiveDt(account.getEffectiveDt());
        response.setExpiryDt(account.getExpiryDt());
        response.setLimitAmt(account.getLimitAmt());
        response.setEntityId(account.getEntityId());
        response.setAccountType(account.getAccountType());

        return response;
    }
}